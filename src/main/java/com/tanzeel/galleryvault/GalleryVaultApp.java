package com.tanzeel.galleryvault;

import com.tanzeel.galleryvault.config.Config;
import com.tanzeel.galleryvault.downloader.GalleryDownloader;
import com.tanzeel.galleryvault.exception.DownloadFailedException;
import com.tanzeel.galleryvault.history.DownloadRecord;
import com.tanzeel.galleryvault.history.DownloadStatus;
import com.tanzeel.galleryvault.history.HistoryManager;
import com.tanzeel.galleryvault.platform.Platform;
import com.tanzeel.galleryvault.platform.PlatformDetector;
import com.tanzeel.galleryvault.setup.SetupManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GalleryVaultApp {
    private final Scanner scanner;
    private final Config config;
    private final GalleryDownloader downloader;
    private final HistoryManager historyManager;

    public GalleryVaultApp(Config config) {
        this.config = config;
        this.downloader = new GalleryDownloader(config);
        this.scanner = new Scanner(System.in);
        this.historyManager = new HistoryManager();
    }

    public void start() {
        mainMenu();
    }

    private void mainMenu() {
        while(true) {
            showMenuOptions();

            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" :
                    startDownload();
                    break;

                case "2" :
                    historyMenu();
                    break;
                case "3" :
                    configurationMenu();
                    break;

                case "4" :
                    return;

                default:
                    System.out.println("Invalid Option.");
            }
        }
    }                   // Main Menu logic~

    private void showMenuOptions() {
        System.out.println("================GalleryVault================");
        System.out.println();

        System.out.println("1. Download");
        System.out.println("2. History");
        System.out.println("3. Configuration");
        System.out.println("4. Exit");
        System.out.println();

    }           // Show the "Options" available in menu

    private void startDownload() {
        while(true) {

            System.out.println("-------------------------------------------");
            System.out.println();
            System.out.println("Type 'menu' to go back to menu");
            System.out.println();

            System.out.print("Enter URL: ");
            String url = scanner.nextLine().trim();
            System.out.println();

            if(url.equalsIgnoreCase("menu")) return;                     // Checks for user command

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

            LocalDateTime timestamp = LocalDateTime.now();
            Platform platform = new PlatformDetector().detect(url);

            try {
                downloader.download(url);                                           // actual start

                recordHistory(
                        platform,
                        timestamp,
                        DownloadStatus.SUCCESS,
                        url,
                        null
                );


                System.out.println("✓ Download Completed Successfully.");

            } catch (DownloadFailedException e) {

                recordHistory(
                        platform,
                        timestamp,
                        DownloadStatus.FAILED,
                        url,
                        e.getMessage()
                );

                showError(e);

            }
        }

    }             // Download Menu Logic

    private void showHistoryOptions() {
        System.out.println("================History================");
        System.out.println();

        System.out.println("1. View All");
        System.out.println("2. Show Successful Downloads");
        System.out.println("3. Show Failed Downloads");
        System.out.println("4. Search by Platform");
        System.out.println("5. Clear History");
        System.out.println("6. Menu");
        System.out.println();
    }       // Show the "Options" available in History

    private void historyMenu() {
        while (true) {
            showHistoryOptions();
            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            List<DownloadRecord> history = historyManager.readHistory();

            switch (choice) {
                case "1" :  // View ALl
                    printHistory(history);

                    break;

                case "2" :  // View Successful

                    List<DownloadRecord> success = new ArrayList<>();

                    for(DownloadRecord record : history) {
                        if(record.getStatus() == DownloadStatus.SUCCESS) {
                            success.add(record);
                        }
                    }

                    printHistory(success);

                    break;

                case "3" :  // View Failed

                    List<DownloadRecord> failed = new ArrayList<>();

                    for(DownloadRecord record : history) {
                        if(record.getStatus() == DownloadStatus.FAILED) {
                            failed.add(record);
                        }
                    }
                    printHistory(failed);

                    break;

                case "4" :  // Search by Platform

                    System.out.println("Available : ");
                    System.out.println("INSTAGRAM");
                    System.out.println("REDDIT");
                    System.out.println("YOUTUBE");
                    System.out.println("X");
                    System.out.println();
                    System.out.print("Enter Platform: ");
                    String platformName = scanner.nextLine();

                    List<DownloadRecord> platform = new ArrayList<>();

                    for(DownloadRecord record : history) {
                        if(record.getPlatform().toString().equalsIgnoreCase(platformName)) {
                            platform.add(record);
                        }
                    }
                    printHistory(platform);

                    break;
                case "5" :  // Clear History
                    System.out.print("Are you sure you want to clear all history? (y/n):");
                    String choose = scanner.nextLine();

                    if(!choose.equalsIgnoreCase("y")) {
                        break;
                    }

                    if(historyManager.clearHistory()) {
                        System.out.println("✓ History cleared successfully.");

                    }else System.out.println("History is already empty.");

                    break;
                case "6" :  // Menu
                    return;

                default:
                    System.out.println("Invalid Options");
            }
        }
    }           // History Menu Logic

    private void printHistory(List<DownloadRecord> history) {

        if(history.isEmpty()) {
            System.out.println("No download history found.");
            return;
        }

        int i = 1;
        for(DownloadRecord record : history) {
            System.out.println("-------------------------------------------");
            System.out.println();

            System.out.println("Record - " + i++ + ":");
            System.out.println();

            System.out.println("Platform    :" + record.getPlatform());
            System.out.println("Timestamp   :" + record.getTimeStamp());
            System.out.println("Status      :" + record.getStatus());
            System.out.println("URL         :" + record.getUrl());
            if(record.getReason() != null) {
                System.out.println("Reason      :" + record.getReason());
            }
            System.out.println();

        }
    }   // Prints the required History

    private void recordHistory(Platform platform,
                               LocalDateTime timestamp,
                               DownloadStatus status,
                               String url,
                               String reason) {

        DownloadRecord record = new DownloadRecord(
                platform,
                timestamp,
                status,
                url,
                reason
        );

        historyManager.save(record);
    }

    private void showConfigurationOptions() {
        System.out.println("================Configuration================");
        System.out.println();

        System.out.println("1. Show Configuration.");
        System.out.println("2. Update Configuration.");
        System.out.println("3. Menu");
        System.out.println();

    }       // Show the "Options" available in configuration

    private void configurationMenu() {
        while(true) {
            showConfigurationOptions();
            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" :
                    printSystemConfiguration();
                    break;
                case "2" :
                    SetupManager manager = new SetupManager();

                    manager.updateConfiguration();
                    break;
                case "3" :
                    return;

                default:
                    System.out.println("Invalid Option.");
            }
        }
    }               // Configuration Menu Logic

    private void printSystemConfiguration() {
        System.out.println("-------------------------------------------");
        System.out.println();

        System.out.println("Gallery-dl  : " + config.getGalleryDlCommand());
        System.out.println("Download    : " + config.getDownloadPath());
        System.out.println("Cookies     : " + config.getCookiesPath());
        System.out.println();

    }       // Prints the configuration

    private void showError(Exception e) {
        System.out.println("✗ Download failed.");
        System.out.print("Reason: " + e.getMessage());
    }
}
