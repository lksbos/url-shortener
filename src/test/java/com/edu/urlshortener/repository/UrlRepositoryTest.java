package com.edu.urlshortener.repository;

import com.edu.urlshortener.model.entity.Url;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UrlRepositoryTest {
    private UrlRepository repository;

    @BeforeEach
    void setup (){
        repository = new UrlRepository();
        repository.createUrl("http://existent.existent");
    }

    @Test
    void shouldCreateUrlCorrectly() {
        Url expected = Url.builder().id("2").url("http://nonexistent.nonexistent").build();
        Url result = repository.createUrl("http://nonexistent.nonexistent");
        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnUrlWhenFindByUrlIsCalledWithExistentUrl() {
        Url expected = Url.builder().id("1").url("http://existent.existent").build();
        Url result = repository.findByUrl("http://existent.existent");
        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnNullWhenFindByUrlIsCalledWithNonExistentUrl() {
        Assertions.assertNull(repository.findByUrl("http://nonexistent.nonexistent"));
    }

    @Test
    void shouldReturnUrlWhenFindByIdIsCalledWithExistentUrl() {
        Url expected = Url.builder().id("1").url("http://existent.existent").build();
        Url result = repository.findById("1");
        Assertions.assertEquals(expected, result);
    }

    @Test
    void shouldReturnNullWhenFindByIdIsCalledWithNonExistentUrl() {
        Assertions.assertNull(repository.findById("nonExistent"));
    }


}