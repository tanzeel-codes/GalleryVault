package com.tanzeel.galleryvault.service;

import com.tanzeel.galleryvault.dto.HistoryResponse;
import com.tanzeel.galleryvault.mapper.HistoryMapper;
import com.tanzeel.galleryvault.model.DownloadHistory;
import com.tanzeel.galleryvault.model.DownloadStatus;
import com.tanzeel.galleryvault.model.Platform;
import com.tanzeel.galleryvault.repository.HistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private Sort createSort(String sort) {

        if(sort.equals("newest")) {
            return Sort.by("downloadedAt").descending();
        }
        else if(sort.equals("oldest")) {
            return Sort.by("downloadedAt").ascending();
        }
        else throw new IllegalArgumentException("Invalid sort option");

    }

    public Page<DownloadHistory> getAllHistory(int page, int size, String sort) {

        Sort sorting = createSort(sort);

        Pageable pageable = PageRequest.of(page, size, sorting);

        return historyRepository.findAll(pageable);

    }

    public Page<DownloadHistory> getDownloadsByStatus(DownloadStatus status, int page, int size, String sort) {

        Sort sorting = createSort(sort);

        Pageable pageable = PageRequest.of(page, size, sorting);

        return historyRepository.findByStatus(status, pageable);

    }

    public Page<DownloadHistory> getDownloadsByPlatform(Platform platform, int page, int size, String sort) {

        Sort sorting = createSort(sort);

        Pageable pageable = PageRequest.of(page, size, sorting);

        return historyRepository.findByPlatform(platform, pageable);

    }

    public Page<DownloadHistory> getDownloadsByKeyword(String keyword, int page, int size, String sort) {

        Sort sorting = createSort(sort);

        Pageable pageable = PageRequest.of(page, size, sorting);

        return historyRepository.findByUrlContaining(keyword, pageable);

    }
}
