package com.tanzeel.galleryvault.statistics;

import com.tanzeel.galleryvault.platform.Platform;

import java.util.EnumMap;

public class StatisticsRecord {
    private final int totalDownloads;
    private final int successfulDownloads;
    private final int failedDownloads;

    private final EnumMap<Platform, Integer> platformDownloads;



    public StatisticsRecord(int totalDownloads,
                            int successfulDownloads,
                            int failedDownloads,
                            EnumMap<Platform, Integer> platformDownloads
    ) {

        this.totalDownloads = totalDownloads;
        this.successfulDownloads = successfulDownloads;
        this.failedDownloads = failedDownloads;
        this.platformDownloads = platformDownloads;

    }

    public int getTotalDownloads() {
        return totalDownloads;
    }

    public int getSuccessfulDownloads() {
        return successfulDownloads;
    }

    public int getFailedDownloads() {
        return failedDownloads;
    }

    public EnumMap<Platform, Integer> getPlatformDownloads() {
        return platformDownloads;
    }
}
