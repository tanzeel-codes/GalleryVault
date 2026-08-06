package com.tanzeel.galleryvault.specification;

import com.tanzeel.galleryvault.model.DownloadHistory;
import com.tanzeel.galleryvault.model.DownloadStatus;
import com.tanzeel.galleryvault.model.Platform;
import org.springframework.data.jpa.domain.Specification;

public final class HistorySpecification {

    public static Specification<DownloadHistory> hasStatus(DownloadStatus status) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status
                );
    }

    public static Specification<DownloadHistory> hasPlatform(Platform platform) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("platform"), platform);

    }

    public static Specification<DownloadHistory> containsKeyword(String keyword) {

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("url"), "%" + keyword + "%"
                );

    }
}
