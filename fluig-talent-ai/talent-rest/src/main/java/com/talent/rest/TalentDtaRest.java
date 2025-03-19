package com.talent.rest;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fluig.sdk.api.FluigAPI;
import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.service.UserService;
import com.talent.service.TalentAiService;
import com.totvs.technology.foundation.common.EncodedMediaType;
import com.totvs.technology.foundation.common.ServiceLocator;

@Path("/dta")
public class TalentDtaRest {
	private final Logger log = LoggerFactory.getLogger(TalentDtaRest.class);

	@GET
	@Path("/{id}")
	@Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
	public Response get(@PathParam("id") Long id) throws Exception {
		log.info("---- App Request | GET getById");
		String s = getTalentService().get(id);
		if(s == null)
			return Response.status(Status.NOT_FOUND).entity("No App found for ID: " + id).build();
		return Response.ok(s).build();
	}

	private UserService getUserServiceSDK() throws SDKException {
		return new FluigAPI().getUserService();
	}

	private TalentAiService getTalentService() throws NamingException {
		return (TalentAiService) ServiceLocator.getInstance().getService(TalentAiService.JNDI_NAME);
	}

}
