package com.edu.urlshortener.repository;

import com.edu.urlshortener.model.entity.Url;
import com.edu.urlshortener.repository.impl.UrlRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UrlRepositoryTest {
    private static final String HTTP_EXISTENT_URL = "http://existent.com";
    private static final String HTTP_NONEXISTENT_URL = "http://nonexistent.com";
    private static final String EXISTENT_ID = "1";

    private UrlRepository repository;

    @BeforeEach
    void setup (){
        repository = new UrlRepositoryImpl();
        repository.createUrl(HTTP_EXISTENT_URL);
    }

    @Test
    void shouldCreateUrlCorrectly() {
        Url expected = Url.builder().id("2").url(HTTP_NONEXISTENT_URL).build();
        Url result = repository.createUrl(HTTP_NONEXISTENT_URL);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnUrlWhenFindByUrlIsCalledWithExistentUrl() {
        Url expected = Url.builder().id(EXISTENT_ID).url(HTTP_EXISTENT_URL).build();
        Url result = repository.findByUrl(HTTP_EXISTENT_URL);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnNullWhenFindByUrlIsCalledWithNonExistentUrl() {
        Assertions.assertNull(repository.findByUrl(HTTP_NONEXISTENT_URL));
    }

    @Test
    void shouldReturnUrlWhenFindByIdIsCalledWithExistentUrl() {
        Url expected = Url.builder().id(EXISTENT_ID).url(HTTP_EXISTENT_URL).build();
        Url result = repository.findById(EXISTENT_ID);
        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnNullWhenFindByIdIsCalledWithNonExistentUrl() {
        Assertions.assertNull(repository.findById("nonExistent"));
    }


}