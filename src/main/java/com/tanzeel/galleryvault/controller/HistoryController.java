package com.tanzeel.galleryvault.controller;

import com.tanzeel.galleryvault.dto.HistoryResponse;
import com.tanzeel.galleryvault.mapper.HistoryMapper;
import com.tanzeel.galleryvault.model.DownloadHistory;
import com.tanzeel.galleryvault.model.DownloadStatus;
import com.tanzeel.galleryvault.model.Platform;
import com.tanzeel.galleryvault.repository.HistoryRepository;
import com.tanzeel.galleryvault.service.HistoryService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("history")
public class HistoryController {

    private final HistoryService historyService;

    private final HistoryMapper historyMapper;

    public HistoryController(HistoryService historyService, HistoryMapper historyMapper) {
        this.historyService = historyService;
        this.historyMapper = historyMapper;
    }

    @GetMapping
    public Page<HistoryResponse> getHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "newest") String sort,

            @RequestParam(required = false) DownloadStatus status,
            @RequestParam(required = false) Platform platform,
            @RequestParam(required = false) String keyword

    ) {

        Page<DownloadHistory> historyPage = historyService.getHistory(
                status,
                platform,
                keyword,
                page,
                size,
                sort
        );

        return historyPage.map(historyMapper::toResponse);
    }
}
