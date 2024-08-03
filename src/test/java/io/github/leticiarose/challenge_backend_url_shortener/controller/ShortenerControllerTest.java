package io.github.leticiarose.challenge_backend_url_shortener.controller;

import io.github.leticiarose.challenge_backend_url_shortener.dto.UrlRequestDTO;
import io.github.leticiarose.challenge_backend_url_shortener.dto.UrlResponseDTO;
import io.github.leticiarose.challenge_backend_url_shortener.dto.UrlShortDTO;
import io.github.leticiarose.challenge_backend_url_shortener.exception.UrlNotFoundException;
import io.github.leticiarose.challenge_backend_url_shortener.model.Url;
import io.github.leticiarose.challenge_backend_url_shortener.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jfr.Description;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShortenerControllerTest {

    @InjectMocks
    ShortenerController shortenerController;
    @Mock
    UrlService urlService;
    @Mock
    HttpServletRequest httpRequest;

    String urlShort = "123b";
    String urlComplete = "https://shortenUrl.com";
    Url url;

    @BeforeEach
    void setUp() {
        url = new Url(urlComplete, urlShort, 1265L, LocalDate.now(), 1.1443);
    }

    @Test
    @Description(value = "Shorten the original URL")
    void shortenUrlTest() {
        when(urlService.shortenUrl(anyString())).thenReturn(urlShort);

        when(httpRequest.getScheme()).thenReturn("http");
        when(httpRequest.getServerName()).thenReturn("localhost");
        when(httpRequest.getServerPort()).thenReturn(8080);

        UrlRequestDTO requestDTO = new UrlRequestDTO();
        requestDTO.setUrlComplete(urlComplete);

        ResponseEntity<UrlShortDTO> result = shortenerController.shortenUrl(requestDTO, httpRequest);

        assertNotNull(result);
        assertEquals(HttpStatusCode.valueOf(200), result.getStatusCode());
    }

    @Test
    @Description(value = "Returns the access statistics when the shortened URL is found")
    void accessStatisticsWhenUrlShortFoundTest() {
        when(urlService.findByUrlShort(anyString())).thenReturn(Optional.of(url));

        ResponseEntity<UrlResponseDTO> result = shortenerController.accessStatistics(urlShort);

        assertNotNull(result);
        assertEquals(HttpStatusCode.valueOf(200), result.getStatusCode());
    }

    @Test
    @Description(value = "Returns a 400 code when the shortened URL is not found.")
    void accessStatisticsWhenUrlShortNotFoundTest() {
        when(urlService.findByUrlShort(anyString())).thenReturn(Optional.empty());

        ResponseEntity<UrlResponseDTO> result = shortenerController.accessStatistics(urlShort);

        assertNotNull(result);
        assertEquals(HttpStatusCode.valueOf(400), result.getStatusCode());
    }

    @Test
    @Description(value = "Redirects to the original URL")
    void redirectOriginalURLWhenShortenedFoundTest() {
        when(urlService.findByUrlShort(anyString())).thenReturn(Optional.of(url));

        RedirectView redirectView = shortenerController.redirectToOriginalUrl(urlShort);

        assertNotNull(redirectView);
        assertEquals(urlComplete, redirectView.getUrl());
        verify(urlService, times(1)).accessStatistics(urlShort);
    }

    @Test
    @Description(value = "Throws a ExceptionBusca when the URL is not found.\n")
    void throwsUrlNotFoundExceptionWhenShortenedURLNotFoundTest() {
        when(urlService.findByUrlShort(anyString())).thenReturn(Optional.empty());

        var exception = assertThrows(UrlNotFoundException.class, () -> {
            shortenerController.redirectToOriginalUrl(urlShort);
        });

        assertEquals(400, exception.getErrorCode());
    }

}