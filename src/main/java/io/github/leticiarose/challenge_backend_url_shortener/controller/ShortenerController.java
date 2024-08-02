package io.github.leticiarose.challenge_backend_url_shortener.controller;

import io.github.leticiarose.challenge_backend_url_shortener.dto.UrlRequestDTO;
import io.github.leticiarose.challenge_backend_url_shortener.dto.UrlResponseDTO;
import io.github.leticiarose.challenge_backend_url_shortener.dto.UrlShortDTO;
import io.github.leticiarose.challenge_backend_url_shortener.exception.UrlNotFoundException;
import io.github.leticiarose.challenge_backend_url_shortener.model.Url;
import io.github.leticiarose.challenge_backend_url_shortener.service.UrlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
@RequestMapping(value = "/", produces = {"application/json"})
@Tag(name = "URL Shortener API")
public class ShortenerController {

    private final UrlService urlService;

    @Autowired
    public ShortenerController(UrlService urlService) {
        this.urlService = urlService;
    }

    @Operation(summary = "Shorten", description = "Shorten the original URL", method = "POST",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Input Json",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UrlRequestDTO.class)
                    )))
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Returns the shortened URL",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UrlShortDTO.class)
            ))})
    @PostMapping(value = "/shorten", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UrlShortDTO> shortenUrl(@RequestBody UrlRequestDTO requestDTO, HttpServletRequest httpRequest) {

        String urlShort = urlService.shortenUrl(requestDTO.getUrlComplete());

        String hostUrl = String.format("%s://%s:%d/", httpRequest.getScheme(), httpRequest.getServerName(), httpRequest.getServerPort());

        return ResponseEntity.ok(new UrlShortDTO(hostUrl + urlShort));
    }

    @Operation(summary = "Statistics", description = "Display access statistics for the shortened URL", method = "GET")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Displays access statistics for the shortened URL - complete URL, total overall, average daily accesses and date of first access.",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UrlResponseDTO.class)
            )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request - no JSON in the response",
                    content = @Content
            )
    })
    @GetMapping("shorten/access/{urlShort}")
    public ResponseEntity<UrlResponseDTO> accessStatistics(@PathVariable String urlShort) {
        Optional<Url> entityURL = urlService.findByUrlShort(urlShort);

        return entityURL.map(url -> ResponseEntity.ok(new UrlResponseDTO(
                url.getUrlComplete(),
                url.getAccessCount(),
                url.getDailyAccessAverage(),
                url.getDateFirstAccess()))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Statistics", description = "Redirects to the original URL", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful operation, you will be redirected to the URL",
                    content = @Content(
                            mediaType = "text/html",
                            schema = @Schema(
                                    example = "<html><body><h1>HTML</h1></body></html>"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "The provided URL was not found.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "string", example = "The provided URL was not found.")
                    )
            )})
    @GetMapping("/{urlShort}")
    public RedirectView redirectToOriginalUrl(@PathVariable String urlShort) {
        Url infoEntity = urlService.findByUrlShort(urlShort)
                .orElseThrow(() -> new UrlNotFoundException("The provided URL was not found.", 400));
        urlService.accessStatistics(urlShort);
        return new RedirectView(infoEntity.getUrlComplete());
    }

}
