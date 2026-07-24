package com.tanzeel.galleryvault.statistics;

import com.tanzeel.galleryvault.history.DownloadRecord;
import com.tanzeel.galleryvault.history.DownloadStatus;
import com.tanzeel.galleryvault.history.HistoryManager;
import com.tanzeel.galleryvault.platform.Platform;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

public class StatisticsManager {
    HistoryManager historyManager;

    public StatisticsManager(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    public StatisticsRecord calculateStatistics() {

        List<DownloadRecord> records = historyManager.readHistory();

        int totalDownloads = 0;
        int successfulDownloads = 0;
        int failedDownloads = 0;

        EnumMap<Platform, Integer> platformDownloads = new EnumMap<>(Platform.class);

        for(Platform platform : Platform.values()) {        // Initializing every values so that when printing it can show others as 0 instead of null
            platformDownloads.put(platform, 0);

        }

        for(DownloadRecord record : records) {
             totalDownloads++;

             if(record.getStatus() == DownloadStatus.SUCCESS) {
                 successfulDownloads++;
             }else failedDownloads++;

             platformDownloads.put(
                     record.getPlatform(),  // <- KEY
                     platformDownloads.get(record.getPlatform()) + 1    // <- VALUE
             );
        }

        return new StatisticsRecord(
                totalDownloads,
                successfulDownloads,
                failedDownloads,
                platformDownloads
        );
    }


}
