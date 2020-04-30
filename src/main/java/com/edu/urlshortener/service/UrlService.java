package com.edu.urlshortener.service;

import com.edu.urlshortener.model.entity.Url;

public interface UrlService {
    Url createUrl(String url);
    Url getUrl(String id);
}
