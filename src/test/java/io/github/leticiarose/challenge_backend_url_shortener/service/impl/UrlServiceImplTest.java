package io.github.leticiarose.challenge_backend_url_shortener.service.impl;

import io.github.leticiarose.challenge_backend_url_shortener.model.Url;
import io.github.leticiarose.challenge_backend_url_shortener.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceImplTest {

    @InjectMocks
    UrlServiceImpl service;

    @Mock
    UrlRepository urlRepository;

    Url url;
    String urlShort = "123b";
    String urlComplete = "https://shortenUrl.com";

    @BeforeEach
    void setUp() {
        url = new Url(urlComplete, urlShort, 1265L, LocalDate.now(), 1.1443);
    }

    @Test
    void shouldCreateAndReturnNewShortenedURLTest() {
        when(urlRepository.findByUrlComplete(anyString())).thenReturn(Optional.empty());

        String result = service.shortenUrl(urlComplete);

        assertNotNull(result);
        assertTrue(result.length() <= 4);
    }

    @Test
    void shouldReturnExistingShortenedURLTest() {
        when(urlRepository.findByUrlComplete(anyString())).thenReturn(Optional.of(url));

        String result = service.shortenUrl(urlComplete);

        assertNotNull(result);
        assertEquals("123b", result);
    }


    @Test
    void accessStatisticsWhenExistsFirstAccessTest() {
        when(urlRepository.findByUrlShort(anyString())).thenReturn(Optional.of(url));

        service.accessStatistics(urlShort);

        assertEquals(1266, url.getAccessCount());
        verify(urlRepository, times(1)).save(url);
    }

    @Test
    void accessStatisticsForFirstAccessTest() {
        url.setAccessCount(0L);
        when(urlRepository.findByUrlShort(anyString())).thenReturn(Optional.of(url));

        service.accessStatistics(urlShort);

        assertEquals(1, url.getAccessCount());
        verify(urlRepository, times(1)).save(url);
    }

    @Test
    void findByUrlShortTest() {
        when(urlRepository.findByUrlShort(anyString())).thenReturn(Optional.of(url));
        assertNotNull(service.findByUrlShort(urlShort));
    }

}