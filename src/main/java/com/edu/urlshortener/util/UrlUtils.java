package com.edu.urlshortener.util;

public class UrlUtils {

    private static final String DEFAULT_PROTOCOL = "http://";
    private static final String PROTOCOL_REGEX = "^.*://.*";

    public static String buildUrl(String uri) {
        if(!uri.matches(PROTOCOL_REGEX)){
            uri = DEFAULT_PROTOCOL +uri;
        }
        return uri;
    }
}
