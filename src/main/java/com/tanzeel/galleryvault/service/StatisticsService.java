package com.tanzeel.galleryvault.service;

import com.tanzeel.galleryvault.dto.StatisticsResponse;
import com.tanzeel.galleryvault.model.DownloadStatus;
import com.tanzeel.galleryvault.model.Platform;
import com.tanzeel.galleryvault.repository.HistoryRepository;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class StatisticsService {

    private final HistoryRepository historyRepository;

    public StatisticsService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public StatisticsResponse getStatistics() {
        StatisticsResponse response = new StatisticsResponse();

        response.setTotalDownloads(historyRepository.count());

        response.setSuccessfulDownloads(historyRepository.countByStatus(DownloadStatus.SUCCESS));

        response.setFailedDownloads(historyRepository.countByStatus(DownloadStatus.FAILED));

        Map<Platform, Long> platformStatistics = new EnumMap<>(Platform.class);

        for(Platform platform : Platform.values()) {
            platformStatistics.put(platform, historyRepository.countByPlatform(platform));
        }

        response.setPlatformStatistics(platformStatistics);

        return response;
    }

}
