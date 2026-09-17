package com.samplecomponent.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fluig.customappkey.Keyring;
import com.fluig.sdk.api.customappkey.KeyVO;
import com.samplecomponent.i18n.MessageUtils;
import com.samplecomponent.util.ErrorStatus;
import com.samplecomponent.util.RestConstant;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

@Path("/activate")
public class SampleActivateRest {

    private static final Logger log = LoggerFactory.getLogger(SampleActivateRest.class);

    @Context
    private HttpHeaders headers;

    @GET
    @Path("/search/{tenantId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getSearch(@PathParam("tenantId") Long tenantId) throws Exception {
        KeyVO key = Keyring.getKeys(tenantId, RestConstant.APP_KEY);

        try {
            OAuthConsumer consumer = getOAuthConsumer(key);
            String payload = buildSearchPayload();
            String url = key.getDomainUrl() + "/api/public/search/advanced";
            String result = executePostRequest(url, payload, consumer);
            return Response.ok(result).build();
        } catch (Exception exception) {
            log.error("@<SampleComponent_TOTVS> Failed to execute search", exception);
            return internalServerError();
        }
    }

    @GET
    @Path("/userInfo/{tenantId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUserInfo(@PathParam("tenantId") Long tenantId) {
        try {
            KeyVO key = Keyring.getKeys(tenantId, RestConstant.APP_KEY);
            OAuthConsumer consumer = getOAuthConsumer(key);
            String url = key.getDomainUrl() + "/api/public/2.0/users/getCurrent";
            String result = executeGetRequest(url, consumer);
            return Response.ok(result).build();
        } catch (Exception exception) {
            log.error("@<SampleComponent_TOTVS> Failed to get user info", exception);
            return internalServerError();
        }
    }

    private Response internalServerError() {
        String localizedMessage = MessageUtils.getMessage(
                MessageUtils.resolveLocale(headers), "error.unexpected");
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorStatus(localizedMessage))
                .build();
    }

    private OAuthConsumer getOAuthConsumer(KeyVO key) {
        OAuthConsumer consumer = new DefaultOAuthConsumer(key.getConsumerKey(), key.getConsumerSecret());
        consumer.setTokenWithSecret(key.getToken(), key.getTokenSecret());
        return consumer;
    }

    private String buildSearchPayload() {
        return "{\"searchType\" : \"GLOBAL\","
                + "\"pattern\":\"\","
                + "\"ordering\":\"RELEVANT\","
                + "\"limit\":\"15\","
                + "\"offset\":\"0\","
                + "\"contentSearch\":\"false\","
                + "\"documentTypes\":[\"FILEDOCUMENT\"],"
                + "\"folderToSearch\":\"0\"}";
    }

    private String executePostRequest(String url, String body, OAuthConsumer consumer) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        try {
            conn.setRequestMethod(RestConstant.REQUEST_METHOD_POST);
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            consumer.sign(conn);

            try (OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream())) {
                wr.write(body);
                wr.flush();
            }

            conn.connect();

            String response = readResponse(conn);
            log.info("@<SampleComponent_TOTVS> POST {} | status={}", url, conn.getResponseCode());
            return response;
        } finally {
            conn.disconnect();
        }
    }

    private String executeGetRequest(String url, OAuthConsumer consumer) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
        try {
            conn.setRequestMethod(RestConstant.REQUEST_METHOD_GET);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            consumer.sign(conn);
            conn.connect();

            String response = readResponse(conn);
            log.info("@<SampleComponent_TOTVS> GET {} | status={}", url, conn.getResponseCode());
            return response;
        } finally {
            conn.disconnect();
        }
    }

    private String readResponse(HttpURLConnection conn) throws Exception {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), RestConstant.UTF_8_ENCODE))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }
}
