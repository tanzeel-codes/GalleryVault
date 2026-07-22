package com.tanzeel.galleryvault.history;

import com.tanzeel.galleryvault.platform.Platform;

import java.time.LocalDateTime;

public class DownloadRecord {
    private final String url;
    private final LocalDateTime timestamp;
    private final DownloadStatus status;
    private final String reason;
    private final Platform platform;

    public DownloadRecord(Platform platform, LocalDateTime timestamp, DownloadStatus status, String url, String reason) {
        this.platform = platform;
        this.timestamp = timestamp;
        this.status = status;
        this.url = url;
        this.reason = reason;
    }
    public String getUrl() {
        return url;
    }
    public LocalDateTime getTimeStamp() {
        return timestamp;
    }
    public DownloadStatus getStatus() {
        return status;
    }
    public String getReason() {
        return reason;
    }
    public Platform getPlatform() {
        return platform;
    }
}
