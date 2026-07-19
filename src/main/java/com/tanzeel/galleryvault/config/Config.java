package com.tanzeel.galleryvault.config;

public class Config {
    private String galleryDlCommand;
    private Path downloadFolder;
    private Path cookiesPath;
    private String archiveFolder;

    public Config() {

    }

    public Config(String galleryDlPath, String downloadFolder, String cookiesPath) {
        this.galleryDlPath = galleryDlPath;
        this.downloadFolder = downloadFolder;
        this.cookiesPath = cookiesPath;
    }

    public String getGalleryDlPath() {
        return galleryDlPath;
    }

    public void setGalleryDlPath(String galleryDlPath) {
        this.galleryDlPath = galleryDlPath;
    }

    public String getDownloadFolder() {
        return downloadFolder;
    }

    public void setDownloadFolder(String downloadFolder) {
        this.downloadFolder = downloadFolder;
    }

    public String getCookiesPath() {
        return cookiesPath;
    }

    public void setCookiesPath(String cookiesPath) {
        this.cookiesPath = cookiesPath;
    }

    public String getArchiveFolder() {
        return archiveFolder;
    }

    public void setArchiveFolder(String archiveFolder) {
        this.archiveFolder = archiveFolder;
    }
}
