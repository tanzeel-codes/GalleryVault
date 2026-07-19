package com.tanzeel.galleryvault;

import com.tanzeel.galleryvault.config.Config;
import com.tanzeel.galleryvault.downloader.GalleryDownloader;
import com.tanzeel.galleryvault.exception.DownloadFailedException;
import com.tanzeel.galleryvault.setup.SetupManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Config config;

    public static String gettingURL() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter URL: ");

        return sc.nextLine();
    }

    public static void showSystemConfiguration() {
        System.out.println("Application Started");

        System.out.println("Gallery-dl: " + config.getGalleryDlPath());
        System.out.println("Download: " + config.getDownloadFolder());
        System.out.println("Cookies: " + config.getCookiesPath());
    }

    public static void main(String[] args) {
        String url = gettingURL();

        // Checks whether url is provided or not
        if(url.isBlank()) {
            System.out.println("URL cannot be empty.");
            return;
        }

        SetupManager setupManager = new SetupManager();

        if(setupManager.isFirstRun()) {
            setupManager.runSetup();
        }
        config = setupManager.loadConfig();

        showSystemConfiguration();

        GalleryDownloader downloader = new GalleryDownloader();

        try {
            downloader.download(url, config);
        } catch (IOException | InterruptedException | DownloadFailedException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String gettingURL() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter URL: ");

        return sc.nextLine();
    }

    public static void showSystemConfiguration() {
        System.out.println("Application Started");

        System.out.println("Gallery-dl: " + config.getGalleryDlCommand());
        System.out.println("Download: " + config.getDownloadFolder());
        System.out.println("Cookies: " + config.getCookiesPath());
    }
}