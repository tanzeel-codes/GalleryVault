package com.tanzeel.galleryvault.dto;

import com.tanzeel.galleryvault.model.Browser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ConfigurationRequest {

    @NotBlank
    private String downloadDirectory;

    @NotNull
    private Browser browser;

    private String cookiesPath;

    private String archivePath;

    private boolean archiveEnabled;

    private boolean overwriteExisting;

    public String getDownloadDirectory() {
        return downloadDirectory;
    }

    public Browser getBrowser() {
        return browser;
    }

    public String getCookiesPath() {
        return cookiesPath;
    }

    public String getArchivePath() {
        return archivePath;
    }

    public boolean isArchiveEnabled() {
        return archiveEnabled;
    }

    public boolean isOverwriteExisting() {
        return overwriteExisting;
    }
}
