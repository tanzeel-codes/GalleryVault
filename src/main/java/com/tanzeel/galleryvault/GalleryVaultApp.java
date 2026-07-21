package com.tanzeel.galleryvault;

import com.tanzeel.galleryvault.config.Config;
import com.tanzeel.galleryvault.downloader.GalleryDownloader;
import com.tanzeel.galleryvault.exception.AuthenticationRequiredException;
import com.tanzeel.galleryvault.exception.DownloadFailedException;

import java.util.Scanner;

public class GalleryVaultApp {
    private final Scanner scanner;
    private final Config config;
    private final GalleryDownloader downloader;

    public GalleryVaultApp(Config config) {
        this.config = config;
        this.downloader = new GalleryDownloader(config);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        showSystemConfiguration();
        runDownload();
    }

    private void runDownload() {
        while(true) {
            System.out.print("Enter URL: ");
            String url = scanner.nextLine().trim();
            System.out.println();

            if(url.equalsIgnoreCase("exit")) break;                     // Checks for user command

            if(url.isBlank()) {                                                     // Checks if url provided
                System.out.println("URL cannot be empty... try again");
                continue;
            }

            if(!url.startsWith("https://")) {                                       // Verify the url provided
                System.out.println("Please enter correct url... try again");
                continue;
            }

            System.out.println("Downloading...");
            System.out.println();

            try {
                downloader.download(url);                                           // actual start
                System.out.println("✓ Download Completed Successfully.");

            } catch (DownloadFailedException e) {

                showError(e);

            } finally {
                System.out.println("-------------------------------------------");
            }
        }
    }

    private void showError(Exception e) {
        System.out.println("✗ Download failed.");
        System.out.print("Reason: " + e.getMessage());
    }

    private void showSystemConfiguration() {
        System.out.println("==========GalleryVault==========");
        System.out.println();

        System.out.println("Gallery-dl  : " + config.getGalleryDlCommand());
        System.out.println("Download    : " + config.getDownloadPath());
        System.out.println("Cookies     : " + config.getCookiesPath());
        System.out.println();

        System.out.println("Type 'exit' anytime to quit");
        System.out.println();

        System.out.println("-------------------------------------------");
    }
}
