package com.samplecomponent.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import com.fluig.sdk.user.UserVO;
import com.samplecomponent.vo.SampleComponentVO;
import com.totvs.technology.foundation.common.EncodedMediaType;

/**
 * Classe de exemplo para expor uma API Rest no fluig
 * 
 * Existem 4 endpoint's:
 * 
 * GET:    /samplecomponent/v1/myrest | Solicita uma informação que está no fluig
 * POST:   /samplecomponent/v1/myrest | Persiste uma informação no fluig
 * PUT:    /samplecomponent/v1/myrest | Atualiza uma informação no fluig
 * DELETE: /samplecomponent/v1/myrest | Remove uma informação no fluig
 * 
 *  onde:
 *  /samplecomponent é o contexto que foi registrado através do arquivo jboss-web.xml no projeto sample-component-config
 *  /v1 é o ApplicationPath, que está na classe ApplicationConfig
 *  
 */
@Path("/myrest")
public class SampleComponentRest {
	
	private Logger log = LoggerFactory.getLogger(SampleComponentRest.class);

    @GET
    @Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
    public Response list() throws Exception {
    	
    	log.info("---- API Request | GET: /myrest");
    	log.info("---- Logged User: " + getUserServiceSDK().getCurrent().getLogin());

        List<UserVO> list = getUserServiceSDK().list(0, 10);
        return Response.ok(list).build();
    }
    
    @POST
    @Consumes(EncodedMediaType.APPLICATION_JSON_UTF8)
    @Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
    public Response create(SampleComponentVO vo) throws Exception {
    	
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
    public Response update(SampleComponentVO vo) throws Exception {
    	
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
