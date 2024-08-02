package io.github.leticiarose.challenge_backend_url_shortener.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Output object for the accessStatistics endpoint")
public class UrlResponseDTO {
    @Schema(description = "URL Complete", example = "https://github.com/leticiarose/desafio-backend-encurtador-de-url")
    String urlComplete;
    @Schema(description = "Access Count", example = "150")
    Long accessCount;
    @Schema(description = "Daily Access Average", example = "0.1234")
    Double dailyAccessAverage;
    @Schema(description = "Date Of First Access", example = "2024-05-02")
    LocalDate dateOfFirstAccess;
}
