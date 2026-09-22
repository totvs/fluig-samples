package com.samplecomponent.util;

import com.fluig.sdk.api.FluigAPI;
import com.fluig.sdk.api.common.SDKException;
import com.fluig.sdk.service.UserService;

/**
 * Utilitários compartilhados entre endpoints REST.
 */
public final class RestHelper {

    private RestHelper() {}

    /**
     * Limita valor de paginação ao máximo permitido.
     */
    public static int clampLimit(int limit, int max) {
        return limit > max ? max : limit;
    }

    /**
     * Obtém UserService via SDK público.
     */
    public static UserService getUserService() throws SDKException {
        return new FluigAPI().getUserService();
    }
}
