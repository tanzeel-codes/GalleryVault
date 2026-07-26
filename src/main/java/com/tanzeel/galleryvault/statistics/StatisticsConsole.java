package com.tanzeel.galleryvault.statistics;

import com.tanzeel.galleryvault.platform.Platform;

import java.security.spec.RSAOtherPrimeInfo;
import java.util.Scanner;

public class StatisticsConsole {
    private final Scanner scanner;
    private final StatisticsManager statisticsManager;

    public StatisticsConsole(
            StatisticsManager statisticsManager,
            Scanner scanner
    ) {
        this.statisticsManager = statisticsManager;
        this.scanner = scanner;
    }

    public void start() {
        printStatistics();
    }
    private void printStatistics() {
        StatisticsRecord statisticsRecord = statisticsManager.calculateStatistics();

        System.out.println("================Statistics================");
        System.out.println();

        System.out.printf( "%-22s: %s%n", "Total Downloads", statisticsRecord.getTotalDownloads());
        System.out.printf( "%-22s: %s%n", "Successful Downloads", statisticsRecord.getSuccessfulDownloads());
        System.out.printf( "%-22s: %s%n", "Failed Downloads", statisticsRecord.getFailedDownloads());
        System.out.println();

        System.out.println("Downloads by Platform");
        System.out.println("-------------------------------------------");

        for(Platform platform : Platform.values()) {
            System.out.format(
                    "%-11s: %d%n", platform, statisticsRecord.getPlatformDownloads().get(platform));
        }
        System.out.println("-------------------------------------------");
        System.out.println();
    }
}
