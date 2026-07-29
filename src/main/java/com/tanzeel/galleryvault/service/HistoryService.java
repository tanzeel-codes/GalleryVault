package com.tanzeel.galleryvault.service;

import com.tanzeel.galleryvault.dto.HistoryResponse;
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

    private HistoryResponse mapToResponse(DownloadHistory history) {
        HistoryResponse response = new HistoryResponse();

        response.setId(history.getId());
        response.setUrl(history.getUrl());
        response.setPlatform(history.getPlatform());
        response.setStatus(history.getStatus());
        response.setDownloadedAt(history.getDownloadedAt());

        return response;
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

    public Page<HistoryResponse> getAllHistory(int page, int size, String sort) {

        Sort sorting = createSort(sort);

        Pageable pageable = PageRequest.of(page, size, sorting);

        Page<DownloadHistory> historyPage = historyRepository.findAll(pageable);

        return historyPage.map(this::mapToResponse);
    }

    public Page<HistoryResponse> getDownloadsByStatus(DownloadStatus status, int page, int size, String sort) {

        Sort sorting = createSort(sort);

        Pageable pageable = PageRequest.of(page, size, sorting);

        Page<DownloadHistory> historyPage = historyRepository.findByStatus(status, pageable);

        return historyPage.map(this::mapToResponse);
    }

    public Page<HistoryResponse> getDownloadsByPlatform(Platform platform, int page, int size, String sort) {

        Sort sorting = createSort(sort);

        Pageable pageable = PageRequest.of(page, size, sorting);

        Page<DownloadHistory> historyPage = historyRepository.findByPlatform(platform, pageable);

        return historyPage.map(this::mapToResponse);
    }

    public Page<HistoryResponse> getDownloadsByKeyword(String keyword, int page, int size, String sort) {

        Sort sorting = createSort(sort);

        Pageable pageable = PageRequest.of(page, size, sorting);

        Page<DownloadHistory> historyPage = historyRepository.findByUrlContaining(keyword, pageable);

        return historyPage.map(this::mapToResponse);
    }
}
