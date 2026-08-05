package com.tanzeel.galleryvault.exception;

import com.tanzeel.galleryvault.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DownloadFailedException.class)
    public ResponseEntity<ErrorResponse> handleDownloadFailedException(DownloadFailedException ex) {

        return buildResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationRequiredException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationRequiredException(AuthenticationRequiredException ex) {

        return buildResponse(
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {

        String message = "Validation failed";

        if(ex.getBindingResult().getFieldError() != null) {
            message = ex.getBindingResult()
                    .getFieldError()
                    .getDefaultMessage();
        }

        return buildResponse(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyConfiguredException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyConfiguredException(AlreadyConfiguredException ex) {

        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DownloadJobNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDownloadJobNotFound(DownloadJobNotFoundException ex) {

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException() {
        return buildResponse(
                "Unexpected server error",
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private ResponseEntity<ErrorResponse> buildResponse(String message, HttpStatus status) {

        ErrorResponse errorResponse = new ErrorResponse(
                message,
                status.value()
        );

        return ResponseEntity.status(status).body(errorResponse);
    }
}
