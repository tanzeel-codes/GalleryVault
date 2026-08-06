package com.tanzeel.galleryvault.controller;

import com.tanzeel.galleryvault.dto.DeleteHistoryResponse;
import com.tanzeel.galleryvault.dto.HistoryResponse;
import com.tanzeel.galleryvault.mapper.HistoryMapper;
import com.tanzeel.galleryvault.model.DownloadHistory;
import com.tanzeel.galleryvault.model.DownloadStatus;
import com.tanzeel.galleryvault.model.Platform;
import com.tanzeel.galleryvault.model.SortOrder;
import com.tanzeel.galleryvault.service.HistoryService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<Page<HistoryResponse>> getHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "NEWEST") SortOrder sort,

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

        return ResponseEntity.ok(historyPage.map(historyMapper::toResponse));
    }

    @DeleteMapping
    public ResponseEntity<DeleteHistoryResponse> deleteHistory(
            @RequestParam(required = false) DownloadStatus status,
            @RequestParam(required = false) Platform platform
    ) {

        return ResponseEntity.ok(historyService.deleteHistory(status, platform));

    }
}
