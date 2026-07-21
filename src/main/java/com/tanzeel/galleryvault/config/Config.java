package com.tanzeel.galleryvault.config;

import java.nio.file.Path;

public class Config {
    private String galleryDlCommand;
    private Path downloadPath;
    private Path cookiesPath;
    private String archiveFolder;

    public Config() {

    }

    public Config(String galleryDlCommand, Path downloadPath, Path cookiesPath) {
        this.galleryDlCommand = galleryDlCommand;
        this.downloadPath = downloadPath;
        this.cookiesPath = cookiesPath;
    }

    public String getGalleryDlCommand() {
        return galleryDlCommand;
    }

    public Path getDownloadPath() {
        return downloadPath;
    }

    public Path getCookiesPath() {
        return cookiesPath;
    }

    public void setCookiesPath(Path cookiesPath) {
        this.cookiesPath = cookiesPath;
    }

    public String getArchiveFolder() {
        return archiveFolder;
    }

    public void setArchiveFolder(String archiveFolder) {
        this.archiveFolder = archiveFolder;
    }
}
