package com.edu.urlshortener.util;

import java.util.HashMap;
import java.util.Map;

public class IdUtils {
    private static final char [] CHAR_ARRAY;
    private static final Map<Character, Integer> CHAR_INDEX_MAP;
    private static final int BASE;

    /* Using Base60 including dash (-) and underscore (_) in the url
     * It does not have O (capital o), 0 (zero), I (capital I), and l (lower case L) (from Base64 to Base60).
     * This decision was made considering a naked eye friendly url.
     * Depending on the font configuration those characters can be confused
     */
    static {
        CHAR_ARRAY = "abcdefghijklmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ123456789-_".toCharArray();
        CHAR_INDEX_MAP = new HashMap<>(CHAR_ARRAY.length);
        for(int i = 0; i < CHAR_ARRAY.length; i++) {
            CHAR_INDEX_MAP.put(CHAR_ARRAY[i], i);
        }
        BASE = CHAR_ARRAY.length;
    }
    private IdUtils(){
        //intentionally empty
    }

    public static String idToUrlHash(long id) {
        StringBuilder urlHash = new StringBuilder();

        if(id == 0) {
            return "a";
        }

        while (id > 0) {
            urlHash.append(CHAR_ARRAY[(int)(id % BASE)]);
            id = id / BASE;
        }

        return urlHash.reverse().toString();
    }

    public static long urlHashToId(String urlHash) {
        long id = 0;
        for (int i = 0; i < urlHash.length(); i++) {
            char c = urlHash.charAt(i);
            id = id * BASE + CHAR_INDEX_MAP.get(c);
        }
        return id;
    }
}
