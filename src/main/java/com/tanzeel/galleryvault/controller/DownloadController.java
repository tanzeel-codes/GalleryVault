package com.tanzeel.galleryvault.controller;

import com.tanzeel.galleryvault.dto.DownloadRequest;
import com.tanzeel.galleryvault.exception.DownloadFailedException;
import com.tanzeel.galleryvault.service.DownloadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/downloads")
public class DownloadController {

    private final DownloadService downloadService;

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @PostMapping
    public String download(@RequestBody DownloadRequest request) throws DownloadFailedException {
        downloadService.download(request.getUrl());

        return "Download Started";
    }

}
