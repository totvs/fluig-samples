package com.samplecomponent.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fluig.customappkey.Keyring;
import com.fluig.sdk.api.customappkey.KeyVO;
import com.samplecomponent.util.ErrorStatus;
import com.samplecomponent.util.RestConstant;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

@Path("/activate")
public class SampleActivateRest {

    @GET
    @Path("/search/{tenantId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getSearch(@PathParam("tenantId") Long tenantId) throws Exception {

        KeyVO key = Keyring.getKeys(tenantId, RestConstant.APP_KEY);
        try {
            OAuthConsumer config = config(key);
            // Exemplo de requisição POST realizando uma consulta no fluig
            URL url = new URL(key.getDomainUrl() + "/api/public/search/advanced");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(RestConstant.REQUEST_METHOD_POST);
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            config.sign(conn);

            String json = "{\"searchType\" : \"GLOBAL\","
                    + "\"pattern\":\"\","
                    + "\"ordering\":\"RELEVANT\","
                    + "\"limit\":\"15\","
                    + "\"offset\":\"0\","
                    + "\"contentSearch\":\"false\","
                    + "\"documentTypes\":[\"FILEDOCUMENT\"],"
                    + "\"folderToSearch\":\"0\"}";

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json);
            wr.flush();
            wr.close();

            conn.connect();

            Reader inputCreateUser = new BufferedReader(new InputStreamReader(conn.getInputStream(), RestConstant.UTF_8_ENCODE));
            String retCreateUser = "";
            for (int c = inputCreateUser.read(); c != -1; c = inputCreateUser.read()) {
                retCreateUser += (char) c;
            }
            int code = conn.getResponseCode();
            System.out.println(String.format("RESPONSE: %d - %s: data: %s", code, conn.getResponseMessage(), retCreateUser));

            conn.disconnect();
            return Response.status(code).entity(retCreateUser).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorStatus(e)).build();
        }
    }

    @GET
    @Path("/userInfo/{tenantId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUserInfo(@PathParam("tenantId") Long tenantId) {
        try {
            KeyVO key = Keyring.getKeys(tenantId, RestConstant.APP_KEY);
            OAuthConsumer consumer = config(key);
            // Exemplo de requisição GET para buscar os dados do usuário
            URL urlProvisioningTenant = new URL(key.getDomainUrl() + "/api/public/2.0/users/getCurrent");
            HttpURLConnection connUserInfo = (HttpURLConnection) urlProvisioningTenant.openConnection();
            connUserInfo.setRequestMethod(RestConstant.REQUEST_METHOD_GET);
            connUserInfo.setDoInput(true);
            connUserInfo.setDoOutput(true);
            // Autentica a conexão
            consumer.sign(connUserInfo);
            connUserInfo.connect();

            Reader inputCreateUser = new BufferedReader(new InputStreamReader(connUserInfo.getInputStream(), RestConstant.UTF_8_ENCODE));
            String retCreateUser = "";
            for (int c = inputCreateUser.read(); c != -1; c = inputCreateUser.read()) {
                retCreateUser += (char) c;
            }
            int code = connUserInfo.getResponseCode();
            System.out.println(String.format("RESPONSE: %d - %s: data: %s", code, connUserInfo.getResponseMessage(), retCreateUser));

            connUserInfo.disconnect();
            return Response.status(code).entity(retCreateUser).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorStatus(e)).build();
        }
    }

    private OAuthConsumer config(KeyVO key) {
        OAuthConsumer consumer = new DefaultOAuthConsumer(key.getConsumerKey(), key.getConsumerSecret());
        consumer.setTokenWithSecret(key.getToken(), key.getTokenSecret());
        return consumer;
    }
}