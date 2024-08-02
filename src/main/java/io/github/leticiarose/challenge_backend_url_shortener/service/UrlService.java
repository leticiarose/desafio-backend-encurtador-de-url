package io.github.leticiarose.challenge_backend_url_shortener.service;

import io.github.leticiarose.challenge_backend_url_shortener.model.Url;

import java.util.Optional;

public interface UrlService {
    String shortenUrl(String urlComplete);

    void accessStatistics(String urlShort);

    Optional<Url> findByUrlShort(String urlShort);

}