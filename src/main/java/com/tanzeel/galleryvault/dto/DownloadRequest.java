package com.tanzeel.galleryvault.dto;

import com.tanzeel.galleryvault.model.DownloadJob;
import jakarta.validation.constraints.NotBlank;

public class DownloadRequest {

    @NotBlank(message = "URL cannot be empty")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
