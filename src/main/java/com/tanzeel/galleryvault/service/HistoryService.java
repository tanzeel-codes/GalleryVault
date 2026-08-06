package com.tanzeel.galleryvault.service;

import com.tanzeel.galleryvault.dto.DeleteHistoryResponse;
import com.tanzeel.galleryvault.model.DownloadHistory;
import com.tanzeel.galleryvault.model.DownloadStatus;
import com.tanzeel.galleryvault.model.Platform;
import com.tanzeel.galleryvault.model.SortOrder;
import com.tanzeel.galleryvault.repository.HistoryRepository;
import com.tanzeel.galleryvault.specification.HistorySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    public void save(DownloadHistory history) {
        historyRepository.save(history);
    }

    private Sort createSort(SortOrder sort) {

        if(sort.equals(SortOrder.NEWEST)) {
            return Sort.by("downloadedAt").descending();
        }
        else if(sort.equals(SortOrder.OLDEST)) {
            return Sort.by("downloadedAt").ascending();
        }

        else throw new IllegalArgumentException("Invalid sort option");

    }

    public Page<DownloadHistory> getHistory(
            DownloadStatus status,
            Platform platform,
            String keyword,
            int page,
            int size,
            SortOrder sort
    ) {
        Sort sorting  = createSort(sort);

        Pageable pageable = PageRequest.of(page, size, sorting);

        Specification<DownloadHistory> specification = HistorySpecification.buildSpecification(status,platform,keyword);

        return historyRepository.findAll(specification, pageable);

    }

    public DeleteHistoryResponse deleteHistory(
            DownloadStatus status,
            Platform platform
    ) {

        Specification<DownloadHistory> specification = HistorySpecification.buildSpecification(status, platform);

        List<DownloadHistory> matchingHistory = historyRepository.findAll(specification);

        long deleteCount = matchingHistory.size();

        String message;
        if(deleteCount == 0) {
            message = "No matching history record found.";

        } else message = "Deleted " + deleteCount + " history record(s)." ;

        historyRepository.deleteAll(matchingHistory);

        return new DeleteHistoryResponse(message, deleteCount);
    }
}
