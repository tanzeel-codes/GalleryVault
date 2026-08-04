package com.tanzeel.galleryvault.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class DownloadJob {

    private UUID id;

    private String url;

    private DownloadJobStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime completedAt;

    public DownloadJob(String url) {
        this.id = UUID.randomUUID();
        this.url = url;
        this.status = DownloadJobStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public void setStatus(DownloadJobStatus status) {
        this.status = status;
    }

    public DownloadJobStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}
