package com.edu.urlshortener.service;

import com.edu.urlshortener.model.entity.Url;
import com.edu.urlshortener.repository.impl.UrlRepositoryImpl;
import com.edu.urlshortener.service.impl.UrlServiceImpl;
import org.apache.commons.validator.routines.UrlValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {
    private static final String HTTP_EXISTENT_URL = "http://existenturl.com";
    private static final String EXISTENT_URL = "existenturl.com";
    private static final long EXISTENT_ID = 1L;
    private static final String EXISTENT_HASH_ID = "b";

    @InjectMocks
    private UrlServiceImpl service;
    @Mock
    private UrlRepositoryImpl repository;
    @Mock
    private UrlValidator urlValidator;

    @Test
    void shouldCreateUrlCorrectlyWhenItDoesNotExist() {
        when(urlValidator.isValid(HTTP_EXISTENT_URL)).thenReturn(true);
        when(repository.findByUrl(HTTP_EXISTENT_URL)).thenReturn(null);
        when(repository.createUrl(HTTP_EXISTENT_URL)).thenReturn(Url.builder().build());
        assertNotNull(service.createUrl(EXISTENT_URL));
    }

    @Test
    void shouldReturnExistentUrlWhenTryingToCreateUrlThatAlreadyExists() {
        when(urlValidator.isValid(HTTP_EXISTENT_URL)).thenReturn(true);
        when(repository.findByUrl(HTTP_EXISTENT_URL)).thenReturn(Url.builder().build());
        assertNotNull(service.createUrl(EXISTENT_URL));
        verify(repository, never()).createUrl(anyString());
    }

    @Test
    void shouldThrowExceptionWhenTryingToCreateUrlWithInvalidUrl() {
        final String url = "udp://invalid.com";
        when(urlValidator.isValid(url)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> service.createUrl(url));
    }

    @Test
    void shouldGetOriginalUrlCorrectly() {
        when(repository.findById(EXISTENT_ID)).thenReturn(Url.builder().id(EXISTENT_ID).build());
        assertNotNull(service.getUrl(EXISTENT_HASH_ID));
    }

    @Test
    void shouldThrowExceptionWhenGetOriginalUrlWithInvalidUrl() {
        when(repository.findById(EXISTENT_ID)).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> service.getUrl(EXISTENT_HASH_ID));
    }
}