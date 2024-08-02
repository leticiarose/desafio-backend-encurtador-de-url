package io.github.leticiarose.challenge_backend_url_shortener.controller;

import io.github.leticiarose.challenge_backend_url_shortener.dto.UrlRequestDTO;
import io.github.leticiarose.challenge_backend_url_shortener.dto.UrlResponseDTO;
import io.github.leticiarose.challenge_backend_url_shortener.dto.UrlShortDTO;
import io.github.leticiarose.challenge_backend_url_shortener.exception.UrlNotFoundException;
import io.github.leticiarose.challenge_backend_url_shortener.model.Url;
import io.github.leticiarose.challenge_backend_url_shortener.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
public class ShortenerController {

    private final UrlService urlService;

    @Autowired
    public ShortenerController(UrlService urlService) {
        this.urlService = urlService;
    }


    @PostMapping("/shorten")
    public ResponseEntity<UrlShortDTO> shortenUrl(@RequestBody UrlRequestDTO requestDTO, HttpServletRequest httpRequest) {

        String urlShort = urlService.shortenUrl(requestDTO.getUrlComplete());

        String hostUrl = String.format("%s://%s:%d/", httpRequest.getScheme(), httpRequest.getServerName(), httpRequest.getServerPort());

        return ResponseEntity.ok(new UrlShortDTO(hostUrl + urlShort));
    }

    @GetMapping("shorten/access/{urlShort}")
    public ResponseEntity<UrlResponseDTO> accessStatistics(@PathVariable String urlShort) {
        Optional<Url> entityURL = urlService.findByUrlShort(urlShort);

        return entityURL.map(url -> ResponseEntity.ok(new UrlResponseDTO(
                url.getUrlComplete(),
                url.getAccessCount(),
                url.getDailyAccessAverage(),
                url.getDateFirstAccess()))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{urlShort}")
    public RedirectView redirectToOriginalUrl(@PathVariable String urlShort) {
        Optional<Url> urlOptional = urlService.findByUrlShort(urlShort);
        urlService.accessStatistics(urlShort);
        if (urlOptional.isPresent()) {
            return new RedirectView(urlOptional.get().getUrlComplete());
        } else {
            throw new UrlNotFoundException("The provided URL was not found. ", 400);
        }
    }

}
