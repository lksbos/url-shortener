package com.edu.urlshortener.controller;

import com.edu.urlshortener.model.dto.UrlDTO;
import com.edu.urlshortener.model.entity.Url;
import com.edu.urlshortener.service.impl.UrlServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UrlController.class)
class UrlControllerTest {

    private static final String HTTP_URL_COM = "http://url.com";
    private static final String REDIRECT_VIEW_NAME = "redirect:" + HTTP_URL_COM;
    private static final String URL_DTO_ATTR = "urlDTO";
    private static final String ERROR_MESSAGE = "error message!";
    private static final String URL_COM = "url.com";
    private static final String INDEX_VIEW_NAME = "index";
    private static final String INDEX_PATH = "/";
    private static final String URL_PARAM = "url";
    private static final String REDIRECT_URL = "/1";
    public static final String SHORT_URL = "http://localhost/b";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @MockBean
    private UrlServiceImpl service;

    @Test
    void shouldReturnIndexForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(INDEX_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW_NAME));
    }

    @Test
    void shouldShortenUrlCorrectly() throws Exception {
        Url created = Url.builder()
                .url(HTTP_URL_COM)
                .id(1L).build();
        UrlDTO expected =  UrlDTO.builder()
                .url(HTTP_URL_COM)
                .shortUrl(SHORT_URL).build();
        when(service.createUrl(eq(HTTP_URL_COM))).thenReturn(created);

        mockMvc.perform(MockMvcRequestBuilders.post(INDEX_PATH)
                .param(URL_PARAM, HTTP_URL_COM)
                .sessionAttr(URL_DTO_ATTR, new UrlDTO()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW_NAME))
                .andExpect(MockMvcResultMatchers.model().attribute(URL_DTO_ATTR, expected));
    }

    @Test
    void shouldReturnErrorMessageWhenShortenUrlWithInvalidUrl() throws Exception {
        UrlDTO expected =  UrlDTO.builder()
                .url(URL_COM)
                .errorMessage(ERROR_MESSAGE)
                .build();
        when(service.createUrl(ArgumentMatchers.any())).thenThrow(new IllegalArgumentException(ERROR_MESSAGE));

        mockMvc.perform(MockMvcRequestBuilders.post(INDEX_PATH)
                .param(URL_PARAM, URL_COM)
                .sessionAttr(URL_DTO_ATTR, new UrlDTO()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW_NAME))
                .andExpect(MockMvcResultMatchers.model().attribute(URL_DTO_ATTR, expected));
    }

    @Test
    void shouldRedirectToUrlCorrectly() throws Exception {
        UrlDTO found = UrlDTO.builder()
                .url(HTTP_URL_COM)
                .shortUrl(SHORT_URL).build();

        when(service.getUrl(ArgumentMatchers.any())).thenReturn(found);

        mockMvc.perform(MockMvcRequestBuilders.get(REDIRECT_URL)
                .sessionAttr(URL_DTO_ATTR, new UrlDTO()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name(REDIRECT_VIEW_NAME));
    }

    @Test
    void shouldReturnErrorMessageWhenShortUrlDoesNotExist() throws Exception {
        UrlDTO expected =  UrlDTO.builder()
                .errorMessage(ERROR_MESSAGE)
                .build();
        when(service.getUrl(ArgumentMatchers.any())).thenThrow(new IllegalArgumentException(ERROR_MESSAGE));

        mockMvc.perform(MockMvcRequestBuilders.get(REDIRECT_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name(INDEX_VIEW_NAME))
                .andExpect(MockMvcResultMatchers.model().attribute(URL_DTO_ATTR, expected));
    }
}