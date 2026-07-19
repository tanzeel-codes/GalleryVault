package com.tanzeel.galleryvault.config;

import java.nio.file.Path;

public class Config {
    private String galleryDlCommand;
    private Path downloadFolder;
    private Path cookiesPath;
    private String archiveFolder;

    public Config() {

    }

    public Config(String galleryDlCommand, Path downloadFolder, Path cookiesPath) {
        this.galleryDlCommand = galleryDlCommand;
        this.downloadFolder = downloadFolder;
        this.cookiesPath = cookiesPath;
    }

    public String getGalleryDlCommand() {
        return galleryDlCommand;
    }

    public void setGalleryDlCommand(String galleryDlCommand) {
        this.galleryDlCommand = galleryDlCommand;
    }

    public Path getDownloadFolder() {
        return downloadFolder;
    }

    public void setDownloadFolder(Path downloadFolder) {
        this.downloadFolder = downloadFolder;
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
