package com.tanzeel.galleryvault.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "download_history")
public class DownloadHistory {

    @Id // This marks it as Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // This will generate id automatically
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Platform platform;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DownloadStatus status;

    @Column(nullable = false)
    private LocalDateTime downloadedAt;

    public DownloadHistory() {

    }

    public DownloadHistory(
            String url,
            Platform platform,
            DownloadStatus status,
            LocalDateTime downloadedAt
    ) {
        this.url = url;
        this.platform = platform;
        this.status = status;
        this.downloadedAt = downloadedAt;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Platform getPlatform() {
        return platform;
    }

    public DownloadStatus getStatus() {
        return status;
    }

    public LocalDateTime getDownloadedAt() {
        return downloadedAt;
    }
}
