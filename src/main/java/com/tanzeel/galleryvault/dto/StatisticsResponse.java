package com.tanzeel.galleryvault.dto;

import com.tanzeel.galleryvault.model.Platform;

import java.util.Map;

public class StatisticsResponse {

    private long totalDownloads;
    private long successfulDownloads;
    private long failedDownloads;
    private Map<Platform, Long> platformStatistics;

    public StatisticsResponse() {

    }

    public StatisticsResponse(long totalDownloads, long successfulDownloads, long failedDownloads, Map<Platform, Long> platformStatistics) {
        this.totalDownloads = totalDownloads;
        this.successfulDownloads = successfulDownloads;
        this.failedDownloads = failedDownloads;
        this.platformStatistics = platformStatistics;
    }

    public long getTotalDownloads() {
        return totalDownloads;
    }

    public void setTotalDownloads(long totalDownloads) {
        this.totalDownloads = totalDownloads;
    }

    public long getSuccessfulDownloads() {
        return successfulDownloads;
    }

    public void setSuccessfulDownloads(long successfulDownloads) {
        this.successfulDownloads = successfulDownloads;
    }

    public long getFailedDownloads() {
        return failedDownloads;
    }

    public void setFailedDownloads(long failedDownloads) {
        this.failedDownloads = failedDownloads;
    }

    public Map<Platform, Long> getPlatformStatistics() {
        return platformStatistics;
    }

    public void setPlatformStatistics(Map<Platform, Long> platformStatistics) {
        this.platformStatistics = platformStatistics;
    }
}
