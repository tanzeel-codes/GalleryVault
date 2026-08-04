package com.tanzeel.galleryvault.mapper;

import com.tanzeel.galleryvault.dto.DownloadJobResponse;
import com.tanzeel.galleryvault.model.DownloadJob;
import com.tanzeel.galleryvault.model.DownloadJobStatus;
import org.springframework.stereotype.Component;

@Component
public class DownloadJobMapper {

    public DownloadJobResponse toResponse(DownloadJob job) {

        return new DownloadJobResponse(
                job.getId(),
                job.getStatus(),
                getStatusMessage(job.getStatus()),
                job.getCreatedAt(),
                job.getCompletedAt()
        );
    }

    private String getStatusMessage(DownloadJobStatus status) {

        return switch (status) {
            case PENDING -> "Download is waiting to start.";
            case DOWNLOADING -> "Download is in progress";
            case COMPLETED -> "Download completed successfully";
            case FAILED -> "Download failed";
            case CANCELLED -> "Download was cancelled";
        };
    }


}
