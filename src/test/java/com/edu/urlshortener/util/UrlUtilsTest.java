package com.edu.urlshortener.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class UrlUtilsTest {
    @ParameterizedTest
    @CsvSource({"test,http://test","anything://test,anything://test"})
    void shouldFormatUrlCorrectly(String uri, String url) {
        String result = UrlUtils.formatUrl(uri);
        Assertions.assertEquals(url, result);
    }
}