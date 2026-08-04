package com.tanzeel.galleryvault.mapper;

import com.tanzeel.galleryvault.dto.HistoryResponse;
import com.tanzeel.galleryvault.model.DownloadHistory;
import org.springframework.stereotype.Component;

@Component
public class HistoryMapper {

    public HistoryResponse toResponse(DownloadHistory history) {
        HistoryResponse response = new HistoryResponse();

        response.setId(history.getId());
        response.setUrl(history.getUrl());
        response.setPlatform(history.getPlatform());
        response.setStatus(history.getStatus());
        response.setDownloadedAt(history.getDownloadedAt());

        return response;
    }
}
