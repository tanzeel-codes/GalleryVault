package com.tanzeel.galleryvault.dto;

import com.tanzeel.galleryvault.model.Browser;

public class ConfigurationResponse {

    private String downloadDirectory;

    private Browser browser;

    private String cookiesPath;

    private boolean archiveEnabled;

    private boolean overwriteExisting;


    public ConfigurationResponse() {

    }

    public ConfigurationResponse(String downloadDirectory, Browser browser, String cookiesPath, boolean archiveEnabled, boolean overwriteExisting) {
        this.downloadDirectory = downloadDirectory;
        this.browser = browser;
        this.cookiesPath = cookiesPath;
        this.archiveEnabled = archiveEnabled;
        this.overwriteExisting = overwriteExisting;
    }

    public String getDownloadDirectory() {
        return downloadDirectory;
    }

    public void setDownloadDirectory(String downloadDirectory) {
        this.downloadDirectory = downloadDirectory;
    }

    public Browser getBrowser() {
        return browser;
    }

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }

    public String getCookiesPath() {
        return cookiesPath;
    }

    public void setCookiesPath(String cookiesPath) {
        this.cookiesPath = cookiesPath;
    }

    public boolean isArchiveEnabled() {
        return archiveEnabled;
    }

    public void setArchiveEnabled(boolean archiveEnabled) {
        this.archiveEnabled = archiveEnabled;
    }

    public boolean isOverwriteExisting() {
        return overwriteExisting;
    }

    public void setOverwriteExisting(boolean overwriteExisting) {
        this.overwriteExisting = overwriteExisting;
    }
}
