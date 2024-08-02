package io.github.leticiarose.challenge_backend_url_shortener.exception;

import lombok.Getter;

@Getter
public class UrlNotFoundException extends RuntimeException {
    private final int errorCode;

    public UrlNotFoundException() {
        super();
        this.errorCode = 0;
    }

    public UrlNotFoundException(String message) {
        super(message);
        this.errorCode = 0;
    }

    public UrlNotFoundException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public UrlNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = 0;
    }

    public UrlNotFoundException(Throwable cause) {
        super(cause);
        this.errorCode = 0;
    }

    public UrlNotFoundException(Throwable cause, int errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public UrlNotFoundException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

}