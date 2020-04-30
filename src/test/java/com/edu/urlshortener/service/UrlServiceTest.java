package com.edu.urlshortener.service;

import com.edu.urlshortener.model.entity.Url;
import com.edu.urlshortener.repository.UrlRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {
    @InjectMocks
    private UrlService service;
    @Mock
    private UrlRepository repository;

    @Test
    void shouldCreateUrlCorrectlyWhenItDoesNotExist() {
        when(repository.findByUrl(eq("http://existenturl.com"))).thenReturn(null);
        when(repository.createUrl(eq("http://existenturl.com"))).thenReturn(Url.builder().build());
        Assertions.assertNotNull(service.createUrl("existenturl.com"));
    }

    @Test
    void shouldReturnExistentUrlWhenTryingToCreateUrlThatAlreadyExists() {
        when(repository.findByUrl(eq("http://existenturl.com"))).thenReturn(Url.builder().build());
        Assertions.assertNotNull(service.createUrl("existenturl.com"));
        verify(repository, never()).createUrl(anyString());
    }

    @Test
    void shouldGetOriginalUrlCorrectly() {
        when(repository.findById(eq("1"))).thenReturn(Url.builder().build());
        Assertions.assertNotNull(service.getUrl("1"));
    }

    @Test
    void shouldThrowExceptionWhenGetOriginalUrlWithInvalidUrl() {
        Mockito.when(repository.findById(eq("1"))).thenReturn(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.getUrl("1"));
    }
}