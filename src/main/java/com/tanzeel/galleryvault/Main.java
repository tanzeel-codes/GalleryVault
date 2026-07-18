package com.tanzeel.galleryvault;

import com.tanzeel.galleryvault.config.Config;
import com.tanzeel.galleryvault.downloader.GalleryDownloader;
import com.tanzeel.galleryvault.exception.DownloadFailedException;
import com.tanzeel.galleryvault.setup.SetupManager;

import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static String gettingURL() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter URL: ");

        return sc.nextLine();
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
        Config config = setupManager.loadConfig();

        GalleryDownloader downloader = new GalleryDownloader();

        try {
            downloader.download(url, config);
        } catch (IOException | InterruptedException | DownloadFailedException e) {
            System.out.println(e.getMessage());
        }
    }
}