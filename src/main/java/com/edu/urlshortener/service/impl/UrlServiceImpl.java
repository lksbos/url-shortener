package com.edu.urlshortener.service.impl;

import com.edu.urlshortener.model.dto.UrlDTO;
import com.edu.urlshortener.model.entity.Url;
import com.edu.urlshortener.repository.UrlRepository;
import com.edu.urlshortener.service.UrlService;
import com.edu.urlshortener.util.IdUtils;
import com.edu.urlshortener.util.UrlUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements UrlService {
    private final UrlRepository repository;
    private final UrlValidator urlValidator;
    private static final String BASE_URL = "http://localhost:8080/";// this should be configured by environment

    public UrlServiceImpl(UrlRepository repository, UrlValidator urlValidator) {
        this.repository = repository;
        this.urlValidator = urlValidator;
    }

    @Override
    public synchronized Url createUrl(final String url){
        String formattedUrl = UrlUtils.formatUrl(url);
        if(!urlValidator.isValid(formattedUrl)) {
            throw new IllegalArgumentException("Url is invalid!");
        }

        Url result = repository.findByUrl(formattedUrl);
        if (result == null) {
            result = repository.createUrl(formattedUrl);
        }
        return result;
    }

    @Override
    public UrlDTO getUrl(String urlHash){
        Url url = repository.findById(IdUtils.urlHashToId(urlHash));
        if(url == null) {
            throw new IllegalArgumentException("Short url does not exist!");
        }

        return UrlDTO.builder()
                .url(url.getUrl())
                .shortUrl(BASE_URL + IdUtils.idToUrlHash(url.getId()))
                .build();
    }
}
