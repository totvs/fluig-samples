package com.samplecomponent.example1;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.fluig.api.tools.response.ErrorResponse;
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
    @ApiOperation(value = "List items")
    @ApiResponses({@ApiResponse(code = 200, message = "Success", response = SampleComponent.class),
        @ApiResponse(code = 400, message = "Failed to return items",
        response = ErrorResponse.class),
        @ApiResponse(code = 401, message = "Unauthorized to list items", response = ErrorResponse.class),
        @ApiResponse(code = 500, message = "Internal error, see response for more details",
        response = ErrorResponse.class)})
    public Response list() throws Exception {

        List<SampleComponent> list = new ArrayList<>();

        SampleComponent s1 = new SampleComponent(1L, "fluig");
        SampleComponent s2 = new SampleComponent(2L, "totvs");

        list.add(s1);
        list.add(s2);

        return Response.ok(list).build();
    }

}
