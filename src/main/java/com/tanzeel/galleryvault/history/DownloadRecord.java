package com.tanzeel.galleryvault.history;

import java.time.LocalDateTime;

public class DownloadRecord {
    private String url;
    private LocalDateTime timeStamp;
    private DownloadStatus status;
    private String reason;
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
