package com.tanzeel.galleryvault.app;

import com.tanzeel.galleryvault.config.Config;
import com.tanzeel.galleryvault.config.ConfigurationConsole;
import com.tanzeel.galleryvault.download.DownloadConsole;
import com.tanzeel.galleryvault.download.GalleryDownloader;
import com.tanzeel.galleryvault.history.HistoryConsole;
import com.tanzeel.galleryvault.history.HistoryManager;
import com.tanzeel.galleryvault.platform.PlatformDetector;
import com.tanzeel.galleryvault.setup.SetupManager;
import com.tanzeel.galleryvault.statistics.StatisticsConsole;
import com.tanzeel.galleryvault.statistics.StatisticsManager;

import java.util.Scanner;

public class GalleryVaultApp {
    private final Scanner scanner;
    private final HistoryConsole historyConsole;
    private final ConfigurationConsole configurationConsole;
    private final DownloadConsole downloadConsole;
    private final StatisticsConsole statisticsConsole;
    private final HistoryManager historyManager;
    private final GalleryDownloader downloader;
    private final PlatformDetector platformDetector;
    private final SetupManager setupManager;
    private final StatisticsManager statisticsManager;
    private final Config config;



    public GalleryVaultApp() {
        this.scanner = new Scanner(System.in);
        this.setupManager = new SetupManager(scanner);

        setupManager.isFirstRun();                  // IF THE SETUP HASN'T DONE YET, WE WILL RUN AND SAVE CONFIG

        // Main Entry point
        this.config = setupManager.loadConfig();    // READS THE DATA FROM SAVED CONFIG.PROPERTIES

        // Object Initialization
        this.downloader = new GalleryDownloader(config);
        this.historyManager = new HistoryManager();
        this.statisticsManager = new StatisticsManager(historyManager);
        this.platformDetector = new PlatformDetector();

        this.historyConsole = new HistoryConsole(
                historyManager,
                scanner
        );

        this.configurationConsole = new ConfigurationConsole(
                config,
                setupManager,
                scanner
        );

        this.downloadConsole = new DownloadConsole(
                downloader,
                historyManager,
                platformDetector,
                scanner
        );

        this.statisticsConsole = new StatisticsConsole(
                statisticsManager,
                scanner
        );
    }

    public void start() {
        runMainMenu();
    }

    private void runMainMenu() {
        while(true) {
            showMainMenu();

            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" :
                    downloadConsole.start();
                    break;

                case "2" :
                    historyConsole.start();
                    break;
                case "3" :
                    configurationConsole.start();
                    break;

                case "4" :
                    statisticsConsole.start();
                    break;

                case "5" :
                    return;

                default:
                    System.out.println("Invalid Option.");
            }
        }
    }             // Main Menu logic

    private void showMainMenu() {
        System.out.println("══════════════════════════════════════════════");
        System.out.println("               GalleryVault");
        System.out.println("══════════════════════════════════════════════");
        System.out.println();

        System.out.println("1. Download");
        System.out.println("2. History");
        System.out.println("3. Configuration");
        System.out.println("4. Statistics");
        System.out.println("5. Exit");
        System.out.println();

    }           // Show "Options" available in menu
}
