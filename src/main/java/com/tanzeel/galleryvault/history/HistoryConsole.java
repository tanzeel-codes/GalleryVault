package com.tanzeel.galleryvault.history;

import com.tanzeel.galleryvault.platform.Platform;

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

            switch (choice) {

                case "1" :  // VIEW ALL
                    System.out.println("================All Downloads================");

                    printHistory(historyManager.getHistory(sortOrder()));

                    break;

                case "2" :  // VIEW SUCCESSFUL
                    System.out.println("================Successful Downloads================");

                    printHistory(historyManager.getSuccessfulHistory(sortOrder()));

                    break;

                case "3" :  // VIEW FAILED
                    System.out.println("=============Failed Downloads=============");

                    printHistory(historyManager.getFailedHistory(sortOrder()));

                    break;

                case "4" :  // FILTER BY PLATFORM
                    System.out.println("=============Platform=============");

                    System.out.println("Available : ");

                    for(Platform platform : Platform.values()) {
                        System.out.format("%-11s %n", platform);
                    }
                    System.out.println();

                    System.out.print("Enter Platform: ");

                    Platform platformName = Platform.valueOf(scanner.nextLine().toUpperCase());

                    printHistory(historyManager.getHistoryByPlatform(platformName, sortOrder()));

                    break;

                case "5" :  // SEARCH
                    System.out.println("=============Search=============");

                    String keyword;
                    while (true) {
                        System.out.print("Enter keyword: ");
                        keyword = scanner.nextLine().trim();

                        if(!keyword.isEmpty()) {
                            break;
                        }
                        System.out.println("Keyword cannot be empty.");

                    }


                    printHistory(historyManager.searchHistory(keyword, sortOrder()));
                    break;

                case "6" :  // CLEAR HISTORY
                    System.out.print("Are you sure you want to clear all history? (y/n):");
                    String choose = scanner.nextLine();

                    if(!choose.equalsIgnoreCase("y")) {
                        break;
                    }

                    if(historyManager.clearHistory()) {
                        System.out.println("✓ History cleared successfully.");

                    }else System.out.println("History is already empty.");

                    break;

                case "7" :  // MENU
                    return;

                default:
                    System.out.println("Invalid Option");
            }
        }
    }                               // History Menu Logic

    private void showHistoryMenu() {
        System.out.println("==================History==================");
        System.out.println();

        System.out.println("1. View All");
        System.out.println("2. Successful Downloads");
        System.out.println("3. Failed Downloads");
        System.out.println("4. Filter By Platform");
        System.out.println("5. Search");
        System.out.println("6. Clear History");
        System.out.println("7. Menu");
        System.out.println();
    }                           // Show the "Options" available in History

    private SortOrder sortOrder() {
        while(true) {

            System.out.println("1. Newest First");
            System.out.println("2. Oldest First");
            System.out.println();

            System.out.print("Choice:");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" :
                    System.out.println("================Newest First================");

                    return SortOrder.NEWEST_FIRST;

                case "2" :
                    System.out.println("================Oldest First================");

                    return SortOrder.OLDEST_FIRST;

                default :
                    System.out.println("Invalid Option");
            }
        }
    }               // sorting Menu Logic

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
