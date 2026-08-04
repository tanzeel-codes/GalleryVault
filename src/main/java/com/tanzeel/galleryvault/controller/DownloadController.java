package com.tanzeel.galleryvault.controller;

import com.tanzeel.galleryvault.dto.DownloadJobResponse;
import com.tanzeel.galleryvault.dto.DownloadRequest;
import com.tanzeel.galleryvault.exception.DownloadFailedException;
import com.tanzeel.galleryvault.mapper.DownloadJobMapper;
import com.tanzeel.galleryvault.model.DownloadJob;
import com.tanzeel.galleryvault.model.DownloadJobStatus;
import com.tanzeel.galleryvault.service.DownloadManager;
import com.tanzeel.galleryvault.service.DownloadService;
import jakarta.validation.Valid;
import org.apache.catalina.connector.Response;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/downloads")
public class DownloadController {

    private final DownloadManager downloadManager;
    private final DownloadJobMapper downloadJobMapper;

    public DownloadController(DownloadManager downloadManager, DownloadJobMapper downloadJobMapper) {
        this.downloadManager = downloadManager;
        this.downloadJobMapper = downloadJobMapper;
    }

    @PostMapping
    public ResponseEntity<DownloadJobResponse> download(@Valid @RequestBody DownloadRequest request) {

        DownloadJob job = downloadManager.startDownload(request.getUrl());

        return ResponseEntity
                .accepted()
                .body(downloadJobMapper.toResponse(job));
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<DownloadJobResponse> getDownloadJob(@PathVariable UUID jobId) {

        return ResponseEntity.ok(downloadJobMapper.toResponse(downloadManager.getJob(jobId)));
    }

}
