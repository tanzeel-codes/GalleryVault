package com.tanzeel.galleryvault.controller;

import com.tanzeel.galleryvault.dto.HistoryResponse;
import com.tanzeel.galleryvault.model.DownloadStatus;
import com.tanzeel.galleryvault.model.Platform;
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

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/all")
    public Page<HistoryResponse> getAllHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "newest") String sort
    ) {

        return historyService.getAllHistory(page, size, sort);
    }

    @GetMapping("/status")
    public Page<HistoryResponse> getByStatus(
            @RequestParam DownloadStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "newest") String sort
    ) {

        return historyService.getDownloadsByStatus(status, page, size, sort);
    }

    @GetMapping("/platform")
    public Page<HistoryResponse> getByPlatform(
            @RequestParam Platform platform,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "newest") String sort
    ) {

        return historyService.getDownloadsByPlatform(platform, page, size, sort);
    }

    @GetMapping("/search")
    public Page<HistoryResponse> getByKeyword(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "newest") String sort
    ) {

        return historyService.getDownloadsByKeyword(keyword, page, size, sort);
    }
}
