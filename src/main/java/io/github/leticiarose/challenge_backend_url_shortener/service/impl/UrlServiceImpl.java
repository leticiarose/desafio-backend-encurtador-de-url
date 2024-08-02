package io.github.leticiarose.challenge_backend_url_shortener.service.impl;

import io.github.leticiarose.challenge_backend_url_shortener.model.Url;
import io.github.leticiarose.challenge_backend_url_shortener.repository.UrlRepository;
import io.github.leticiarose.challenge_backend_url_shortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    @Override
    public void accessStatistics(String urlShort) {
        Optional<Url> urlEntity = urlRepository.findByUrlShort(urlShort);

        urlEntity.ifPresent(url -> {
            if (url.getAccessCount() == null || url.getAccessCount() == 0) {
                url.setAccessCount(1L);
                url.setDateFirstAccess(LocalDate.now());
            } else {
                url.setAccessCount(url.getAccessCount() + 1L);
            }
            Double average = calculatesAverageDailyAccess(url.getAccessCount(), url.getDateFirstAccess(), LocalDate.now());
            url.setDailyAccessAverage(average);
            urlRepository.save(url);
        });

    }

    private Double calculatesAverageDailyAccess(Long accessCount, LocalDate dateFirstAccess, LocalDate currentDate) {
        long daysSinceFirstAccess = ChronoUnit.DAYS.between(dateFirstAccess, currentDate);

        double averageAccessesDay;
        if (daysSinceFirstAccess > 0) {
            averageAccessesDay = (double) accessCount / daysSinceFirstAccess;
        } else {
            averageAccessesDay = 0;
        }
        return averageAccessesDay;
    }

    private String createShortUrl() {
        return UUID.randomUUID().toString().substring(0, 4);
    }

}
