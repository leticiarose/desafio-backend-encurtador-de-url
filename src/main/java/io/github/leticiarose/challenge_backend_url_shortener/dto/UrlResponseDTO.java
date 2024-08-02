package io.github.leticiarose.challenge_backend_url_shortener.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlResponseDTO {
    String urlComplete;
    Long accessCount;
    Double dailyAccessAverage;
    LocalDate dateOfFirstAccess;
}
