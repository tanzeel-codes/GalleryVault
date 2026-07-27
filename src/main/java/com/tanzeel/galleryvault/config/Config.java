package com.tanzeel.galleryvault.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

@ConfigurationProperties(prefix = "gallery")
public class Config {

    private final String galleryDlCommand;
    private final Path downloadPath;
    private final Path cookiesPath;

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
}
