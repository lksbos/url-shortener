package com.edu.urlshortener.service;

import com.edu.urlshortener.model.dto.UrlDTO;
import com.edu.urlshortener.model.entity.Url;

public interface UrlService {
    Url createUrl(String url);
    UrlDTO getUrl(String urlHash);
}
