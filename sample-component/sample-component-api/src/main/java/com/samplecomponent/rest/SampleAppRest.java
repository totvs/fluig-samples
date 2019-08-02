package com.samplecomponent.rest;

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
import com.samplecomponent.service.SampleAppService;
import com.samplecomponent.vo.SampleAppVO;
import com.totvs.technology.foundation.common.EncodedMediaType;
import com.totvs.technology.foundation.common.ServiceLocator;

/**
 * Classe de exemplo para expor uma API Rest no fluig
 *
 * Existem 5 endpoint's:
 *
 * GET:    /samplecomponent/v1/app      | Solicita uma informação(lista) que está no fluig
 * GET:    /samplecomponent/v1/app/{id} | Solicita uma informação(item único) que está no fluig
 * POST:   /samplecomponent/v1/app      | Persiste uma informação no fluig
 * PUT:    /samplecomponent/v1/app      | Atualiza uma informação no fluig
 * DELETE: /samplecomponent/v1/app      | Remove uma informação no fluig
 *
 *  onde:
 *  /samplecomponent é o contexto que foi registrado através do arquivo jboss-web.xml no projeto sample-component-config
 *  /v1 é o ApplicationPath, que está na classe ApplicationConfig
 *  /app, que é o path registrado para essa classe em específico
 *
 */
@Path("/app")
public class SampleAppRest {
	
	SampleAppService sampleAppService;
	private Logger log = LoggerFactory.getLogger(SampleAppRest.class);

	@GET
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response list(
			@DefaultValue("") @QueryParam("text") String text,
			@DefaultValue("10") @QueryParam("limit") int limit,
			@DefaultValue("0") @QueryParam("offset") int offset) throws Exception {

		log.info("---- API Request | GET find: /myrest ");
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());
		
		sampleAppService = (SampleAppService) ServiceLocator.getInstance().getService(SampleAppService.JNDI_NAME);
		return Response.ok(sampleAppService.find(text, limit, offset)).build();
	}

	@GET
	@Path("/{id}")
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response getById(@PathParam("id") Long id) throws Exception {

		log.info("---- API Request | GET getById: /myrest");
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());
		
		return Response.ok(new SampleAppVO()).build();
	}

	@POST
	@Consumes(EncodedMediaType.APPLICATION_JSON_UTF8)
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response create(SampleAppVO vo) throws Exception {

		log.info("---- API Request | POST: /myrest");
		log.info("---- Object created: " + vo.toString());
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());

		/**
		 * check permission
		 *
		 * DO SOMETHING
		 */
		return Response.ok(Status.OK).build();
	}

	@PUT
	@Consumes(EncodedMediaType.APPLICATION_JSON_UTF8)
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response update(SampleAppVO vo) throws Exception {

		log.info("---- API Request | PUT: /myrest");
		log.info("---- Object Sent: " + vo.toString());
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());

		/**
		 * check permission
		 *
		 * DO SOMETHING
		 */
		return Response.ok(Status.OK).build();
	}

	@DELETE
	@Path("/{id}")
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response delete(@PathParam("id") Long id) throws Exception {

		log.info("---- API Request | DELETE: /myrest");
		log.info("---- Object ID Deleted: " + id);
		log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());

		/**
		 * check permission
		 *
		 * DO SOMETHING
		 */
		return Response.ok(Status.OK).build();
	}

	private UserService getUserServiceSDK() throws SDKException {
		return new FluigAPI().getUserService();
	}

}
