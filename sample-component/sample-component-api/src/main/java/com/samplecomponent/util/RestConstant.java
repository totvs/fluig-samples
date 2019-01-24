package com.samplecomponent.util;

import java.nio.charset.StandardCharsets;

public class RestConstant {
    /**
     * Troque esta chave, atenção ao formato aaaa-bbbb-cccc-dddd.
     */
    public static final String APP_KEY = "1234-5678-9876-5432";
    public static final String UTF_8_ENCODE = StandardCharsets.UTF_8.name();


    /**
     * HTTP Headers.
     */
    public static final String REQUEST_METHOD_GET = "GET";
    public static final String REQUEST_METHOD_POST = "POST";
}
