package com.tanzeel.galleryvault.exception;

public class AuthenticationRequiredException extends DownloadFailedException{
    public AuthenticationRequiredException(String message) {
        super(message);
    }
}
