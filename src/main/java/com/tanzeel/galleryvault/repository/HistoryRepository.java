package com.tanzeel.galleryvault.repository;

import com.tanzeel.galleryvault.model.DownloadHistory;
import com.tanzeel.galleryvault.model.DownloadStatus;
import com.tanzeel.galleryvault.model.Platform;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HistoryRepository extends JpaRepository<DownloadHistory, Long> {

    Page<DownloadHistory> findByStatus(DownloadStatus status, Pageable pageable);

    Page<DownloadHistory> findByPlatform(Platform platform, Pageable pageable);

    Page<DownloadHistory> findByUrlContaining(String keyword, Pageable pageable);

    long countByStatus(DownloadStatus status);

    long countByPlatform(Platform platform);
}
