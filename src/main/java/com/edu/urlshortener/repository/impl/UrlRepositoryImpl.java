package com.edu.urlshortener.repository.impl;

import com.edu.urlshortener.model.entity.Url;
import com.edu.urlshortener.repository.UrlRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UrlRepositoryImpl implements UrlRepository {
    private final AtomicLong sequence;
    private final Map<Long, Url> database;
    private final Map<String, Url> indexedByUrl;

    public UrlRepositoryImpl() {
        this.sequence = new AtomicLong(1);
        this.database = Collections.synchronizedMap(new HashMap<>());
        this.indexedByUrl = Collections.synchronizedMap(new HashMap<>());
    }

    @Override
    public Url createUrl(String url) {
        Url result = new Url(sequence.getAndIncrement(), url);
        database.put(result.getId(), result);
        indexedByUrl.put(result.getUrl(), result);
        return result;
    }

    @Override
    public Url findByUrl(String url){
        return indexedByUrl.get(url);
    }

    @Override
    public Url findById(Long id) {
        return database.get(id);
    }
}
