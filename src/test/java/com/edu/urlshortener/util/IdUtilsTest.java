package com.edu.urlshortener.util;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IdUtilsTest {
    @ParameterizedTest
    @CsvSource({"0,a","120,b-", "238469907, rnMKV", "1450001242,bTUmGk", "0x7fffffff,cHgey9", "0x7fffffffffffffff, m7TVbFif6Uh"})
    void shouldConvertFromIdToUrlHashCorrectly(long id, String urlHash) {
        String result = IdUtils.idToUrlHash(id);
        assertEquals(urlHash, result);
    }

    @ParameterizedTest
    @CsvSource({"0,a","120,b-", "238469907, rnMKV", "1450001242,bTUmGk", "0x7fffffff,cHgey9", "0x7fffffffffffffff, m7TVbFif6Uh"})
    void shouldConvertFromUrlHashToUrlIdCorrectly(long id, String urlHash) {
        long result = IdUtils.urlHashToId(urlHash);
        assertEquals(id, result);
    }
}