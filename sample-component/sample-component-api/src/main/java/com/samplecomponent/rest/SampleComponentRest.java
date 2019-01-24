package com.samplecomponent.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fluig.sdk.api.FluigAPI;
import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.service.UserService;
import com.fluig.sdk.user.UserVO;
import com.totvs.technology.foundation.common.EncodedMediaType;

@Path("/myrest")
public class SampleComponentRest {

    @GET
    @Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
    public Response list() throws Exception {

        List<UserVO> list = getUserServiceSDK().list(0, 10);
        return Response.ok(list).build();
    }

    private UserService getUserServiceSDK() throws SDKException {
        return new FluigAPI().getUserService();
    }

}
