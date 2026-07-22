package com.tanzeel.galleryvault.history;

import java.time.LocalDateTime;

public class DownloadRecord {
    private final String url;
    private final LocalDateTime timestamp;
    private final DownloadStatus status;
    private final String reason;
    private String platform;

    public DownloadRecord(String url, LocalDateTime timestamp, DownloadStatus status, String reason) {
        this.url = url;
        this.timestamp = timestamp;
        this.status = status;
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
}
