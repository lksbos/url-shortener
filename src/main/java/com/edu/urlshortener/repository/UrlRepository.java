package com.edu.urlshortener.repository;

import com.edu.urlshortener.model.entity.Url;

public interface UrlRepository {
    Url createUrl(String url);
    Url findByUrl(String url);
    Url findById(String id);
}
