package com.tanzeel.galleryvault.history;

import com.tanzeel.galleryvault.platform.Platform;

import java.time.Duration;
import java.time.LocalDateTime;

public class DownloadRecord {
    private final Platform platform;
    private final LocalDateTime timestamp;
    private final String url;
    private final DownloadStatus status;
    private final Duration duration;
    private final String reason;

    public DownloadRecord(Platform platform,
                          LocalDateTime timestamp,
                          DownloadStatus status,
                          String url,
                          Duration duration,
                          String reason
    ) {

        this.platform = platform;
        this.timestamp = timestamp;
        this.status = status;
        this.url = url;
        this.duration = duration;
        this.reason = reason;

    }

    public String getUrl() {
        return url;
    }

    public LocalDateTime getTimestamp() {
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

    public Duration getDuration() {
        return duration;
    }
}
