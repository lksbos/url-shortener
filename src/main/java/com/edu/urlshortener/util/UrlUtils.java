package com.edu.urlshortener.util;

public class UrlUtils {
    private UrlUtils () {
        //intentionally empty
    }
    private static final String DEFAULT_PROTOCOL = "http://";
    private static final String PROTOCOL_REGEX = "^.*://.*";

    public static String formatUrl(String uri) {
        if(!uri.matches(PROTOCOL_REGEX)){
            uri = DEFAULT_PROTOCOL + uri;
        }
        return uri;
    }
}
