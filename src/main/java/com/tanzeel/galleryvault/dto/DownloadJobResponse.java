package com.tanzeel.galleryvault.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tanzeel.galleryvault.model.DownloadJobStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DownloadJobResponse {

    private UUID id;

    private DownloadJobStatus status;

    private int progress;

    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completedAt;

    public DownloadJobResponse() {

    }

    public DownloadJobResponse(
            UUID id,
            DownloadJobStatus status,
            int progress,
            String message,
            LocalDateTime createdAt,
            LocalDateTime completedAt

    ) {

        this.id = id;
        this.status = status;
        this.progress = progress;
        this.message = message;
        this.createdAt = createdAt;
        this.completedAt = completedAt;

    }

    public UUID getId() {
        return id;
    }

    public DownloadJobStatus getStatus() {
        return status;
    }

    public void setStatus(DownloadJobStatus status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

}
