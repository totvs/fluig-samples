package com.samplerunner.rest;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.api.group.GroupVO;
import com.samplerunner.service.GroupService;
import com.samplerunner.util.ErrorStatus;

@Path("/groups")
public class GroupRunnerRest {

    @EJB
    private GroupService groupService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() throws Exception {
        /*
        Lista os grupos
         */
        try {
            List<GroupVO> groupVOS = groupService.listGroups();
            return Response.status(Response.Status.OK).entity(groupVOS).build();
        } catch (SDKException e) {
            return Response.status(BAD_REQUEST).entity(new ErrorStatus(e)).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(GroupVO groupVO) throws Exception {
        /*
        Para criar um grupo, apenas um usuário com papel de admin consegue executar a ação
         */
        try {
            GroupVO group = groupService.createGroup(groupVO);
            return Response.status(Response.Status.OK).entity(group).build();
        } catch (SDKException e) {
            return Response.status(BAD_REQUEST).entity(new ErrorStatus(e)).build();
        }
    }

}