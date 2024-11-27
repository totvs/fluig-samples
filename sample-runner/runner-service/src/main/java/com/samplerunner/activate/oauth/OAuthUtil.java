package com.samplerunner.activate.oauth;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.ws.rs.core.MediaType;

import com.fluig.sdk.api.customappkey.KeyVO;
import com.totvs.technology.foundation.common.FluigRestClient;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

public class OAuthUtil {

    /**
     * API para retornar os tokens para um determinado login
     *
     * @param jwt
     * @param key
     * @param login
     * @return
     * @throws Exception
     */
    public static OAuthConsumer generateKeysToUser(String jwt, KeyVO key, String login) throws Exception {
        String url = key.getDomainUrl() + "/api/public/wcm/oauth/generateKeysToUser";
        Map<String, Object> body = new HashMap<>();
        body.put("appKey", key.getConsumerKey());
        body.put("login", login);

        Map oauth = (Map) new FluigRestClient().post(new Cookie[]{},
                "Bearer " + jwt,
                url, null, body, MediaType.APPLICATION_JSON,
                Map.class).get("content");

        OAuthConsumer consumer = new DefaultOAuthConsumer(key.getConsumerKey(), key.getConsumerSecret());
        consumer.setTokenWithSecret(oauth.get("tokenAccess").toString(), oauth.get("tokenSecret").toString());
        return consumer;
    }
}
