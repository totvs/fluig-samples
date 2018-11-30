package com.samplecomponent.example1.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fluig.api.tools.response.ErrorResponse;
import com.fluig.sdk.api.FluigAPI;
import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.service.UserService;
import com.fluig.sdk.user.UserVO;
import com.totvs.technology.foundation.common.EncodedMediaType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/samplerest")
@Api(tags = "samplerest")
public class SampleComponentRest {

    @GET
    @Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
    @ApiOperation(value = "Esta é uma simples API Rest, retornando uma lista de objetos")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = SampleComponentVO.class),
        @ApiResponse(code = 400, message = "Failed to return items",
        response = ErrorResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized to list items", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal error, see response for more details",
        response = ErrorResponse.class)})
    public Response list() throws Exception {
    	
    	List<UserVO> list = getUserServiceSDK().list(0, 10);
        return Response.ok(list).build();
    }
    
    private UserService getUserServiceSDK() throws SDKException {
        return new FluigAPI().getUserService();
    }

}
