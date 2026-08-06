package com.tanzeel.galleryvault.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tanzeel.galleryvault.model.DownloadStatus;
import com.tanzeel.galleryvault.model.Platform;

import java.time.LocalDateTime;

public class HistoryResponse {

    private Long id;

    private String url;

    private Platform platform;

    private DownloadStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime downloadedAt;

    public HistoryResponse() {

    }

    public HistoryResponse(Long id, String url, Platform platform, DownloadStatus status, LocalDateTime downloadedAt) {
        this.id = id;
        this.url = url;
        this.platform = platform;
        this.status = status;
        this.downloadedAt = downloadedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public DownloadStatus getStatus() {
        return status;
    }

    public void setStatus(DownloadStatus status) {
        this.status = status;
    }

    public LocalDateTime getDownloadedAt() {
        return downloadedAt;
    }

    public void setDownloadedAt(LocalDateTime downloadedAt) {
        this.downloadedAt = downloadedAt;
    }
}
