package com.tanzeel.galleryvault;

import com.tanzeel.galleryvault.config.Config;
import com.tanzeel.galleryvault.downloader.GalleryDownloader;
import com.tanzeel.galleryvault.exception.DownloadFailedException;

import java.io.IOException;
import java.util.Scanner;

public class GalleryVaultApp {
    private final Scanner scanner = new Scanner(System.in);
    private final Config config;
    private final GalleryDownloader downloader;

    public GalleryVaultApp(Config config) {
        this.config = config;
        this.downloader = new GalleryDownloader(config);
    }

    public void start() {
        showSystemConfiguration();
        download();
    }

    // DOWNLOAD THE FILES
    private void download() {
        while(true) {
            System.out.print("Enter URL: ");
            String url = scanner.nextLine().trim();

            // Checks whether url is provided or not
            if(url.isBlank()) {
                System.out.println("URL cannot be empty... try again");
                continue;
            }

            if(url.equalsIgnoreCase("exit")) break;

            if(!url.startsWith("https")) {
                System.out.println("Please enter correct url... try again");
                continue;
            }

            try {
                downloader.download(url);
            } catch (DownloadFailedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // PRINTS ALL THE CONFIGURATION
    private void showSystemConfiguration() {
        System.out.println("======GalleryVault======");

        System.out.println("Gallery-dl  : " + config.getGalleryDlCommand());
        System.out.println("Download    : " + config.getVaultPath());
        System.out.println("Cookies     : " + config.getCookiesPath());
    }
}
