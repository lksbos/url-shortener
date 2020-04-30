package com.edu.urlshortener.repository;

import com.edu.urlshortener.model.entity.Url;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UrlRepository {
    private AtomicInteger sequence;
    private Map<String, Url> database;
    private Map<String, Url> indexedByUrl;

    public UrlRepository() {
        this.sequence = new AtomicInteger(1);
        this.database = new HashMap<>();
        this.indexedByUrl = new HashMap<>();
    }

    public Url createUrl(String url) {
        Url result = new Url(String.valueOf(sequence.getAndIncrement()), url);
        database.put(result.getId(), result);
        indexedByUrl.put(result.getUrl(), result);
        return result;
    }

    public Url findByUrl(String url){
        return indexedByUrl.get(url);
    }

    public Url findById(String id) {
        return database.get(id);
    }
}
