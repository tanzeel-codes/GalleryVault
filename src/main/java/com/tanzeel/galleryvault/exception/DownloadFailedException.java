package com.tanzeel.galleryvault.exception;

public class DownloadFailedException extends Exception {
    public DownloadFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public DownloadFailedException(String message) {
        super(message);
    }
}
