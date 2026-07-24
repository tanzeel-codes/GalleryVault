package com.tanzeel.galleryvault.history;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HistoryConsole {
    private final Scanner scanner;
    private final HistoryManager historyManager;

    public HistoryConsole(
            HistoryManager historyManager,
            Scanner scanner
    ) {

        this.historyManager =  historyManager;
        this.scanner = scanner;

    }

    public void start() {
        runHistoryMenu();
    }

    private void runHistoryMenu() {
        while (true) {
            showHistoryMenu();
            System.out.print("Choice: ");

            String choice = scanner.nextLine();

            List<DownloadRecord> history = historyManager.readHistory();

            switch (choice) {
                case "1" :  // View ALl
                    System.out.println("================All Downloads================");

                    printHistory(history);

                    break;

                case "2" :  // View Successful
                    System.out.println("================Successful Downloads================");

                    List<DownloadRecord> success = new ArrayList<>();

                    for(DownloadRecord record : history) {
                        if(record.getStatus() == DownloadStatus.SUCCESS) {
                            success.add(record);
                        }
                    }

                    printHistory(success);

                    break;

                case "3" :  // View Failed
                    System.out.println("=============Failed Downloads=============");

                    List<DownloadRecord> failed = new ArrayList<>();

                    for(DownloadRecord record : history) {
                        if(record.getStatus() == DownloadStatus.FAILED) {
                            failed.add(record);
                        }
                    }
                    printHistory(failed);

                    break;

                case "4" :  // Sort History
                    sortHistory();
                    break;

                case "5" :  // Search by Platform

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

                case "6" :  // Clear History
                    System.out.print("Are you sure you want to clear all history? (y/n):");
                    String choose = scanner.nextLine();

                    if(!choose.equalsIgnoreCase("y")) {
                        break;
                    }

                    if(historyManager.clearHistory()) {
                        System.out.println("✓ History cleared successfully.");

                    }else System.out.println("History is already empty.");

                    break;

                case "7" :  // Menu
                    return;

                default:
                    System.out.println("Invalid Options");
            }
        }
    }                               // History Menu Logic

    private void sortHistory() {
        while(true) {

            System.out.println("1. Newest First");
            System.out.println("2. Oldest First");
            System.out.println("3. Back");
            System.out.println();

            System.out.print("Choice:");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" :
                    System.out.println("================Newest First================");

                    printHistory(historyManager.getHistory(SortOrder.NEWEST_FIRST));
                    break;

                case "2" :
                    System.out.println("================Oldest First================");

                    printHistory(historyManager.getHistory(SortOrder.OLDEST_FIRST));
                    break;

                case "3" :
                    return;

                default :
                    System.out.println("Invalid Option");
            }
        }
    }                                   // sorting Menu Logic

    private void showHistoryMenu() {
        System.out.println("==================History==================");
        System.out.println();

        System.out.println("1. View All");
        System.out.println("2. Show Successful Downloads");
        System.out.println("3. Show Failed Downloads");
        System.out.println("4. Sort Downloads (Newest/Oldest)");
        System.out.println("5. Search by Platform");
        System.out.println("6. Clear History");
        System.out.println("7. Menu");
        System.out.println();
    }                           // Show the "Options" available in History

    private void printHistory(List<DownloadRecord> history) {

        if(history.isEmpty()) {
            System.out.println("No download history found.");
            return;
        }

        int i = 1;
        for(DownloadRecord record : history) {
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
            System.out.println("-------------------------------------------");

        }
    }     // Prints the required History
}
