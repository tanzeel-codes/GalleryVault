package com.tanzeel.galleryvault.repository;

import com.tanzeel.galleryvault.model.DownloadHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<DownloadHistory, Long> {

}
