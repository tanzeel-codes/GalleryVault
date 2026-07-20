package com.tanzeel.galleryvault;

import com.tanzeel.galleryvault.config.Config;
import com.tanzeel.galleryvault.downloader.GalleryDownloader;
import com.tanzeel.galleryvault.exception.DownloadFailedException;
import com.tanzeel.galleryvault.setup.SetupManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Config config;
    static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);

        SetupManager setupManager = new SetupManager();

        // IF THE SETUP HASN'T DONE YET WE WILL RUN THIS
        if(setupManager.isFirstRun()) {
            setupManager.runSetup();
        }

        config = setupManager.loadConfig();

        // PRINTS ALL THE CONFIGURATION
        showSystemConfiguration();

        // DOWNLOAD THE FILES
        download();

        System.out.println("Thank you!");
    }

    public static String gettingURL() {
        System.out.print("Enter URL: ");

        return sc.nextLine();
    }

    public static void download() {
        String ans = "N";
        do {
            String url = gettingURL();

            // Checks whether url is provided or not
            if(url.isBlank()) {
                System.out.println("URL cannot be empty... try again");
                continue;
            }

            GalleryDownloader downloader = new GalleryDownloader(config);

            try {
                downloader.download(url);
            } catch (IOException | InterruptedException | DownloadFailedException e) {
                System.out.println(e.getMessage());
            }

            System.out.println("DO you want to continue downloading? (Y/N)");
            ans = sc.nextLine();

        } while (ans.toLowerCase().startsWith("y"));
    }

    public static void showSystemConfiguration() {
        System.out.println("Application Started");

        System.out.println("Gallery-dl: " + config.getGalleryDlCommand());
        System.out.println("Download: " + config.getVaultPath());
        System.out.println("Cookies: " + config.getCookiesPath());
    }
}