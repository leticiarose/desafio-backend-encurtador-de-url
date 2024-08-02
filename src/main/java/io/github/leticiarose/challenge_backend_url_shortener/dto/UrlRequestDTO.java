package io.github.leticiarose.challenge_backend_url_shortener.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Input object for the shortenUrl endpoint")
public class UrlRequestDTO {

    @Schema(description = "URL Complete", example = "https://github.com/leticiarose/desafio-backend-encurtador-de-url")
    private String urlComplete;

}