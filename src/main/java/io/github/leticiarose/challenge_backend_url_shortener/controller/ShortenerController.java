package io.github.leticiarose.challenge_backend_url_shortener.controller;

import io.github.leticiarose.challenge_backend_url_shortener.dto.UrlRequestDTO;
import io.github.leticiarose.challenge_backend_url_shortener.dto.UrlShortDTO;
import io.github.leticiarose.challenge_backend_url_shortener.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortenerController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlShortDTO> shortenUrl(@RequestBody UrlRequestDTO requestDTO, HttpServletRequest httpRequest) {

        String urlShort = urlService.shortenUrl(requestDTO.getUrlComplete());

        String hostUrl = String.format("%s://%s:%d/", httpRequest.getScheme(), httpRequest.getServerName(), httpRequest.getServerPort());

        return ResponseEntity.ok(new UrlShortDTO(hostUrl + urlShort));
    }

}
