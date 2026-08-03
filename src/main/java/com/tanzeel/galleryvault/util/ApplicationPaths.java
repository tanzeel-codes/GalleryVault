package com.tanzeel.galleryvault.util;

import java.io.IOException;
import java.nio.file.*;

public final class ApplicationPaths {

    private static final Path GALLERY_VAULT_HOME =
            Paths.get(System.getProperty("user.home"), "GalleryVault");

    private ApplicationPaths() {

    }

    public static Path home() {
        createDirectory(GALLERY_VAULT_HOME);

        return GALLERY_VAULT_HOME;
    }
    public static Path downloads() {
        Path downloads = home().resolve("Downloads");

        createDirectory(downloads);

        return downloads;
    }

    public static Path archive() {

        return home().resolve("archive.txt");
    }

    private static void createDirectory(Path path) {
        try {
            Files.createDirectories(path);

        } catch (IOException e) {

            throw new IllegalArgumentException("Unable to create directory : " + path, e);
        }
    }

}
