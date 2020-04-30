package com.edu.urlshortener.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlDTO {
    private String url;
    private String shortUrl;
    private String errorMessage;
}
