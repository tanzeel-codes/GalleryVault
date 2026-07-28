package com.tanzeel.galleryvault.service;

import com.tanzeel.galleryvault.model.DownloadHistory;
import com.tanzeel.galleryvault.repository.HistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public void save(DownloadHistory history) {
        historyRepository.save(history);
    }
}
