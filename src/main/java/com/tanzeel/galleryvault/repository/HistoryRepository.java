package com.tanzeel.galleryvault.repository;

import com.tanzeel.galleryvault.model.DownloadHistory;
import com.tanzeel.galleryvault.model.DownloadStatus;
import com.tanzeel.galleryvault.model.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface HistoryRepository extends JpaRepository<DownloadHistory, Long>, JpaSpecificationExecutor<DownloadHistory> {

    long countByStatus(DownloadStatus status);

    long countByPlatform(Platform platform);

}
