package org.solar.system.central.common.all.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpConstUtils {

    public static final String PATH_VERSION                         = "/v";
    public static final String HEADER_ACCESS_TOKEN                  = "Access-token";
    public static final String HEADER_ACCEPT_LANGUAGE               = "Accept-Language";
    public static final String HEADER_API_VERSION                   = "Api-version";
    public static final String HEADER_CONTENT_TYPE                  = "Content-Type";
    public static final String MEDIA_APPLICATION_JSON               = "application/json";

    public static final String RESPONSE_CODE_200 					= "200";
    public static final String RESPONSE_CODE_201 					= "201";
    public static final String RESPONSE_CODE_204 					= "204";
    public static final String RESPONSE_CODE_403 					= "403";
    public static final String RESPONSE_CODE_403_NAME 				= "Forbidden";
    public static final String RESPONSE_CODE_401 					= "401";
    public static final String RESPONSE_CODE_401_NAME 				= "Unauthorized";
    public static final String HTTP_GET 							= "GET";
    public static final String HTTP_POST 							= "POST";
    public static final String HTTP_PATCH 							= "PATCH";
    public static final String HTTP_PUT 							= "PUT";
    public static final String HTTP_DELETE 							= "DELETE";
}
