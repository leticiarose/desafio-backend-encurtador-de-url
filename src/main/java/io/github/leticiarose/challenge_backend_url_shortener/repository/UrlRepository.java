package io.github.leticiarose.challenge_backend_url_shortener.repository;

import io.github.leticiarose.challenge_backend_url_shortener.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, String> {
    Optional<Url> findByUrlComplete(String urlComplete);

    Optional<Url> findByUrlShort(String urlShort);
}