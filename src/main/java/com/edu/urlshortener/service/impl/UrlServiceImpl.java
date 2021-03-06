package com.edu.urlshortener.service.impl;

import com.edu.urlshortener.model.entity.Url;
import com.edu.urlshortener.repository.UrlRepository;
import com.edu.urlshortener.service.UrlService;
import com.edu.urlshortener.util.UrlUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements UrlService {
    private UrlRepository repository;
    private UrlValidator urlValidator;

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
    public Url getUrl(String id){
        Url url = repository.findById(id);
        if(url == null) {
            throw new IllegalArgumentException("Short url does not exist!");
        }
        return url;
    }
}
