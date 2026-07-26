package com.tanzeel.galleryvault.config;

import com.tanzeel.galleryvault.setup.SetupManager;

import java.util.Scanner;

public class ConfigurationConsole {
    private final Config config;
    private final Scanner scanner;
    private final SetupManager setupManager;

    public ConfigurationConsole(
            Config config,
            SetupManager setupManager,
            Scanner scanner
    ) {

        this.config = config;
        this.setupManager = setupManager;
        this.scanner = scanner;

    }

    public void start() {
        runConfigurationMenu();
    }

    private void runConfigurationMenu() {
        while(true) {
            showConfigurationOptions();
            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" :
                    printConfiguration();
                    break;
                case "2" :
                    setupManager.updateConfiguration();
                    break;
                case "3" :
                    return;

                default:
                    System.out.println("Invalid Option.");
            }
        }
    }               // Configuration Menu Logic

    private void showConfigurationOptions() {
        System.out.println("================Configuration================");
        System.out.println();

        System.out.println("1. Show Configuration.");
        System.out.println("2. Update Configuration.");
        System.out.println("3. Menu");
        System.out.println();

    }       // Show the "Options" available in configuration

    private void printConfiguration() {
        System.out.println("================Configuration================");
        System.out.println();

        System.out.println("-------------------------------------------");
        System.out.printf("%-12s: %s%n", "Gallery-dl", config.getGalleryDlCommand());
        System.out.printf("%-12s: %s%n", "Download", config.getDownloadPath());
        System.out.printf("%-12s: %s%n", "Cookies", config.getCookiesPath());
        System.out.println("-------------------------------------------");
        System.out.println();

    }       // Prints the configuration
}
