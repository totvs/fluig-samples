package com.samplecomponent.rest;

import com.samplecomponent.entity.SampleCategory;
import com.samplecomponent.service.SampleCategoryService;
import com.samplecomponent.util.RestHelper;
import com.totvs.technology.foundation.common.EncodedMediaType;
import com.totvs.technology.foundation.common.ServiceLocator;
import com.totvs.technology.foundation.common.exception.FDNCreateException;
import com.totvs.technology.foundation.common.exception.FDNRemoveException;
import com.totvs.technology.foundation.common.exception.FDNUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.NamingException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.Map;

/**
 * GET:    /samplecomponent/v1/category      | Solicita uma informação(lista) que está na plataforma
 * GET:    /samplecomponent/v1/category/{id} | Solicita uma informação(item único) que está na plataforma
 * POST:   /samplecomponent/v1/category      | Persiste uma informação na plataforma
 * PUT:    /samplecomponent/v1/category      | Atualiza uma informação na plataforma
 * DELETE: /samplecomponent/v1/category      | Remove uma informação na plataforma
 *
 *  onde:
 *  /samplecomponent é o contexto que foi registrado através do arquivo jboss-web.xml no projeto sample-component-config
 *  /v1 é o ApplicationPath, que está na classe ApplicationConfig
 *  /category, que é o path registrado para essa classe em específico
 *
 */
@Path("/category")
public class SampleCategoryRest {
	private Logger log = LoggerFactory.getLogger(SampleCategoryRest.class);

	@GET
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response find(
			@DefaultValue("") @QueryParam("text") String text,
			@DefaultValue("10") @QueryParam("limit") int limit,
			@DefaultValue("0") @QueryParam("offset") int offset) throws Exception {
		log.info("@<SampleComponent_TOTVS> Category Request | GET find ");
		log.info("@<SampleComponent_TOTVS> Logged User: " + RestHelper.getUserService().getCurrent().getLogin());
		return Response.ok(categoryService().find(text, RestHelper.clampLimit(limit, 50), offset)).build();
	}

	@GET
	@Path("/{id}")
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response get(@PathParam("id") Long id) throws Exception {
		log.info("@<SampleComponent_TOTVS> Category Request | GET getById");
		log.info("@<SampleComponent_TOTVS> Logged User: " + RestHelper.getUserService().getCurrent().getLogin());		
		SampleCategory category = categoryService().get(id);
		if(category == null)
			return Response.status(Status.NOT_FOUND).entity("Nenhuma categoria encontrada para o ID: " + id).build();
		return Response.ok(category).build();
	}

	@POST
	@Consumes(EncodedMediaType.APPLICATION_JSON_UTF8)
	@Produces(MediaType.TEXT_PLAIN)
	public Response create(SampleCategory vo) throws Exception {
		log.info("@<SampleComponent_TOTVS> Category Request | POST");
		log.info("@<SampleComponent_TOTVS> Object to create: " + String.valueOf(vo));
		log.info("@<SampleComponent_TOTVS> Logged User: " + RestHelper.getUserService().getCurrent().getLogin());
		try {
			return Response.status(Response.Status.CREATED).entity(categoryService().create(vo)).build();
		} catch (IllegalArgumentException e) {
			return buildFriendlyErrorResponse(e, e.getMessage(), Status.BAD_REQUEST);
		} catch (FDNCreateException e) {
		    return Response.status(e.getStatus()).entity(e.getMessage()).build();
		} catch (Exception e) {
			return buildFriendlyErrorResponse(e, "Já existe uma categoria com esse nome.", Status.CONFLICT);
		}
	}

	@PUT
	@Consumes(EncodedMediaType.APPLICATION_JSON_UTF8)
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response update(SampleCategory vo) throws Exception {
		log.info("@<SampleComponent_TOTVS> Category Request | PUT");
		log.info("@<SampleComponent_TOTVS> Object to update: " + String.valueOf(vo));
		log.info("@<SampleComponent_TOTVS> Logged User: " + RestHelper.getUserService().getCurrent().getLogin());		
		try {
			categoryService().update(vo);
			return Response.noContent().build();
		} catch (IllegalArgumentException e) {
			return buildFriendlyErrorResponse(e, e.getMessage(), Status.BAD_REQUEST);
		} catch (FDNUpdateException e) {
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("error", e.getMessage());
			return Response.status(e.getStatus()).entity(errors).type(EncodedMediaType.APPLICATION_JSON_UTF8).build();
		} catch (Exception e) {
			return buildFriendlyErrorResponse(e, "Já existe uma categoria com esse nome.", Status.CONFLICT);
		}		
	}

	@DELETE
	@Path("/{id}")
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response delete(@PathParam("id") Long id) throws Exception {
		log.info("@<SampleComponent_TOTVS> Category Request | DELETE");
		log.info("@<SampleComponent_TOTVS> Object to delete: " + id);
		log.info("@<SampleComponent_TOTVS> Logged User: " + RestHelper.getUserService().getCurrent().getLogin());		
		try {
			categoryService().delete(id);
			return Response.noContent().build();
		} catch (FDNRemoveException e) {
			Map<String, String> errors = new HashMap<String, String>();
			errors.put("error", e.getMessage());
			return Response.status(e.getStatus()).entity(errors).type(EncodedMediaType.APPLICATION_JSON_UTF8).build();
		}
	}

	private SampleCategoryService categoryService() throws NamingException {
		return (SampleCategoryService) ServiceLocator.getInstance().getService(SampleCategoryService.JNDI_NAME);
	}

	private Response buildFriendlyErrorResponse(Exception e, String fallbackMessage, Status fallbackStatus) {
		String message = e.getMessage() == null ? "" : e.getMessage();
		if (isDuplicateCategoryError(message)) {
			message = "Já existe uma categoria com esse nome.";
			fallbackStatus = Status.CONFLICT;
		}

		Map<String, String> errors = new HashMap<String, String>();
		errors.put("error", message.isEmpty() ? fallbackMessage : message);
		return Response.status(fallbackStatus).entity(errors).type(EncodedMediaType.APPLICATION_JSON_UTF8).build();
	}

	private boolean isDuplicateCategoryError(String message) {
		return message != null && (message.contains("Duplicate entry") || message.contains("duplicate key") || message.contains("sc_category.scp_category_pk"));
	}
}
