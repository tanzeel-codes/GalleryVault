package com.tanzeel.galleryvault.exception;

public class AuthenticationRequiredException extends DownloadFailedException {
    public AuthenticationRequiredException(String message,Throwable cause) {
        super(message, cause);
    }

    public AuthenticationRequiredException(String message) {
        super(message);
    }
}
