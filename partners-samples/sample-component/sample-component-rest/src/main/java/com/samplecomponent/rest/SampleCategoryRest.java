package com.samplecomponent.rest;

import com.samplecomponent.entity.SampleCategory;
import com.samplecomponent.i18n.MessageUtils;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Path("/category")
public class SampleCategoryRest {

    private static final Logger log = LoggerFactory.getLogger(SampleCategoryRest.class);
    private static final int CATEGORY_NAME_MAX_LENGTH = 50;

    @Context
    private HttpHeaders headers;

    @GET
    @Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
    public Response find(
            @DefaultValue("") @QueryParam("text") String text,
            @DefaultValue("10") @QueryParam("limit") int limit,
            @DefaultValue("0") @QueryParam("offset") int offset) throws Exception {
        log.info("@<SampleComponent_TOTVS> Category Request | GET find ");
        log.info("@<SampleComponent_TOTVS> Logged User: " + RestHelper.getUserService().getCurrent().getLogin());
        return Response.ok(categoryService().find(text, RestHelper.clampLimit(limit, CATEGORY_NAME_MAX_LENGTH), offset)).build();
    }

    @GET
    @Path("/{id}")
    @Produces(EncodedMediaType.APPLICATION_JSON_UTF8)
    public Response get(@PathParam("id") Long id) throws Exception {
        log.info("@<SampleComponent_TOTVS> Category Request | GET getById");
        log.info("@<SampleComponent_TOTVS> Logged User: " + RestHelper.getUserService().getCurrent().getLogin());
        SampleCategory category = categoryService().get(id);
        if (category == null) {
            return Response.status(Status.NOT_FOUND).entity(message("error.category.notfound", id)).build();
        }
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
        } catch (IllegalArgumentException exception) {
            return buildErrorResponse(exception, "error.category.create", Status.BAD_REQUEST);
        } catch (FDNCreateException exception) {
            return buildErrorResponse(exception, "error.category.create", exception.getStatus());
        } catch (Exception exception) {
            return buildErrorResponse(exception, "error.category.create", Status.CONFLICT);
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
        } catch (IllegalArgumentException exception) {
            return buildErrorResponse(exception, "error.category.update", Status.BAD_REQUEST);
        } catch (FDNUpdateException exception) {
            return buildErrorResponse(exception, "error.category.update", exception.getStatus());
        } catch (Exception exception) {
            return buildErrorResponse(exception, "error.category.update", Status.CONFLICT);
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
        } catch (FDNRemoveException exception) {
            return buildErrorResponse(exception, "error.category.delete", exception.getStatus());
        } catch (Exception exception) {
            return buildErrorResponse(exception, "error.category.delete", Status.INTERNAL_SERVER_ERROR);
        }
    }

    private SampleCategoryService categoryService() throws NamingException {
        return (SampleCategoryService) ServiceLocator.getInstance().getService(SampleCategoryService.JNDI_NAME);
    }

    private Response buildErrorResponse(Exception exception, String fallbackKey, Status fallbackStatus) {
        String key = getCategoryErrorKey(exception.getMessage(), fallbackKey);
        Status status = "error.category.duplicate".equals(key) ? Status.CONFLICT : fallbackStatus;
        Map<String, String> errors = new HashMap<String, String>();
        errors.put("error", "error.category.name.maxlength".equals(key)
                ? message(key, CATEGORY_NAME_MAX_LENGTH)
                : message(key));
        return Response.status(status).entity(errors).type(EncodedMediaType.APPLICATION_JSON_UTF8).build();
    }

    private String getCategoryErrorKey(String exceptionMessage, String fallbackKey) {
        if (exceptionMessage == null) {
            return fallbackKey;
        }
        if (isDuplicateCategoryError(exceptionMessage)) {
            return "error.category.duplicate";
        }
        if (exceptionMessage.startsWith("error.category.")) {
            return exceptionMessage;
        }
        return fallbackKey;
    }

    private boolean isDuplicateCategoryError(String exceptionMessage) {
        return exceptionMessage.contains("Duplicate entry")
                || exceptionMessage.contains("duplicate key")
                || exceptionMessage.contains("sc_category.scp_category_pk");
    }

    private String message(String key, Object... arguments) {
        Locale locale = MessageUtils.resolveLocale(headers);
        return MessageUtils.getMessage(locale, key, arguments);
    }
}
