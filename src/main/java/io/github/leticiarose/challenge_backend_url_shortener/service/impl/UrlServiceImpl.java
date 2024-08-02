package io.github.leticiarose.challenge_backend_url_shortener.service.impl;

import io.github.leticiarose.challenge_backend_url_shortener.model.Url;
import io.github.leticiarose.challenge_backend_url_shortener.repository.UrlRepository;
import io.github.leticiarose.challenge_backend_url_shortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UrlServiceImpl implements UrlService {

    @Autowired
    UrlRepository urlRepository;

    @Override
    public String shortenUrl(String urlComplete) {
        Optional<Url> urlEntity = urlRepository.findByUrlComplete(urlComplete);

        if (urlEntity.isPresent()) {
            return urlEntity.get().getUrlShort();
        }

        String urlShort = createShortUrl();
        urlRepository.save(new Url(urlComplete, urlShort));
        return urlShort;
    }

    private String createShortUrl() {
        return UUID.randomUUID().toString().substring(0, 4);
    }

}
