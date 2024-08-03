package io.github.leticiarose.challenge_backend_url_shortener.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "urls")
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String urlComplete;
    private String urlShort;
    private Long accessCount = 0L;
    private LocalDate dateFirstAccess;
    private Double dailyAccessAverage = 0.0;

    public Url(String urlComplete, String urlShort) {
        this.urlComplete = urlComplete;
        this.urlShort = urlShort;
    }

    public Url(String urlComplete, String urlShort, Long accessCount, LocalDate dateFirstAccess, Double dailyAccessAverage) {
        this.urlComplete = urlComplete;
        this.urlShort = urlShort;
        this.accessCount = accessCount;
        this.dateFirstAccess = dateFirstAccess;
        this.dailyAccessAverage = dailyAccessAverage;
    }

}