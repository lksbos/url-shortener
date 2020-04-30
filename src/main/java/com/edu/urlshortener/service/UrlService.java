package com.edu.urlshortener.service;

import com.edu.urlshortener.model.entity.Url;
import org.springframework.stereotype.Service;

@Service
public class UrlService {
    public Url createUrl(String url){
        return Url.builder().id("1").url(url).build();
    }

    public Url getUrl(String id){
        return Url.builder().id("1").url("http://google.com").build();
    }
}
