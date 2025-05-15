package com.talent.rest;

import com.talent.rest.dto.jobs.JobsCreateDTO;
import com.talent.rest.dto.jobs.JobsResponseDTO;
import com.talent.rest.dto.jobs.JobsUpdateDTO;
import com.talent.service.JobsService;
import com.talent.service.impl.vo.jobs.JobsCreateVO;
import com.talent.service.impl.vo.jobs.JobsVO;
import com.talent.util.SimpleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/jobs")
public class JobsRest {
    private final Logger log = LoggerFactory.getLogger(JobsRest.class);

    @EJB
    JobsService jobsService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(JobsCreateDTO dto) {
        try {
            JobsCreateVO newJob = SimpleMapper.convertToVO(dto, JobsCreateVO.class);
            JobsVO jobsVO = jobsService.create(newJob);
            JobsResponseDTO responseDTO = SimpleMapper.convert(jobsVO, JobsResponseDTO.class);
            return Response.ok(responseDTO).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new com.talent.util.ErrorStatus(e)).build();
        }
    }

    @PATCH
    @Path("/{widgetInstanceId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("widgetInstanceId") Long id, JobsUpdateDTO dto) {
        try {

            return Response.ok().build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new com.talent.util.ErrorStatus(e)).build();        }
    }
}
