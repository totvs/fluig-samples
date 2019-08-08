package com.samplecomponent.rest;

import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fluig.sdk.api.FluigAPI;
import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.service.UserService;
import com.samplecomponent.entity.SampleCategory;
import com.samplecomponent.service.SampleCategoryService;
import com.totvs.technology.foundation.common.EncodedMediaType;
import com.totvs.technology.foundation.common.ServiceLocator;

/**
 * GET:    /samplecomponent/v1/category      | Solicita uma informação(lista) que está no fluig 
 * GET:    /samplecomponent/v1/category/{id} | Solicita uma informação(item único) que está no fluig
 * POST:   /samplecomponent/v1/category      | Persiste uma informação no fluig
 * PUT:    /samplecomponent/v1/category      | Atualiza uma informação no fluig
 * DELETE: /samplecomponent/v1/category      | Remove uma informação no fluig
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
		log.info("---- Category Request | GET find ");
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());
		return Response.ok(categoryService().find(text, limit>50?50:limit, offset)).build();
	}

	@GET
	@Path("/{id}")
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response get(@PathParam("id") Long id) throws Exception {
		log.info("---- Category Request | GET getById");
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());		
		SampleCategory category = categoryService().get(id);
		if(category == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(category).build();
	}

	@POST
	@Consumes(EncodedMediaType.APPLICATION_JSON_UTF8)
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response create(SampleCategory vo) throws Exception {
		log.info("---- Category Request | POST");
		log.info("---- Object to create: " + vo.toString());
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());
		return Response.ok(categoryService().create(vo)).status(Response.Status.CREATED) .build();
	}

	@PUT
	@Consumes(EncodedMediaType.APPLICATION_JSON_UTF8)
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response update(SampleCategory vo) throws Exception {
		log.info("---- Category Request | PUT");
		log.info("---- Object to update: " + vo.toString());
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());
		categoryService().update(vo);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response delete(@PathParam("id") Long id) throws Exception {
		log.info("---- Category Request | DELETE");
		log.info("---- Object to delete: " + id);
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());
		categoryService().delete(id);
		return Response.status(Response.Status.NO_CONTENT).build();
	}

	private UserService getUserServiceSDK() throws SDKException {
		return new FluigAPI().getUserService();
	}

	private SampleCategoryService categoryService() throws NamingException {
		return (SampleCategoryService) ServiceLocator.getInstance().getService(SampleCategoryService.JNDI_NAME);
	}
}
