package com.edu.urlshortener.service;

import com.edu.urlshortener.model.entity.Url;
import com.edu.urlshortener.repository.UrlRepository;
import com.edu.urlshortener.util.UrlUtils;
import org.springframework.stereotype.Service;

@Service
public class UrlService {
    private UrlRepository repository;

    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    public Url createUrl(final String url){
        String formattedUrl = UrlUtils.buildUrl(url);
        Url result = repository.findByUrl(formattedUrl);
        if (result == null) {
            result = repository.createUrl(formattedUrl);
        }
        return result;
    }

    public Url getUrl(String id){
        Url url = repository.findById(id);
        if(url == null) {
            throw new IllegalArgumentException("Short url does not exist!");
        }
        return url;
    }
}
