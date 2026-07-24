package com.tanzeel.galleryvault.download;

import com.tanzeel.galleryvault.exception.DownloadFailedException;
import com.tanzeel.galleryvault.history.DownloadRecord;
import com.tanzeel.galleryvault.history.DownloadStatus;
import com.tanzeel.galleryvault.history.HistoryManager;
import com.tanzeel.galleryvault.platform.Platform;
import com.tanzeel.galleryvault.platform.PlatformDetector;

import java.time.LocalDateTime;
import java.util.Scanner;

public class DownloadConsole {
    private final Scanner scanner;
    private final GalleryDownloader downloader;
    private final HistoryManager historyManager;
    private final PlatformDetector platformDetector;

    public DownloadConsole(
            GalleryDownloader downloader,
            HistoryManager historyManager,
            PlatformDetector platformDetector,
            Scanner scanner


    ) {

        this.scanner = scanner;
        this.downloader = downloader;
        this.historyManager = historyManager;
        this.platformDetector = platformDetector;

    }

    public void start() {
        runDownload();
    }

    private void runDownload() {
        while(true) {

            System.out.println("================Download Media================");
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
            Platform platform = platformDetector.detect(url);
            DownloadRecord record = null;

            try {
                downloader.download(url);                                           // actual start

                record = new DownloadRecord(
                        platform,
                        timestamp,
                        DownloadStatus.SUCCESS,
                        url,
                        null
                );


                System.out.println("✓ Download Completed Successfully.");

            } catch (DownloadFailedException e) {

                record = new DownloadRecord(
                        platform,
                        timestamp,
                        DownloadStatus.FAILED,
                        url,
                        e.getMessage()
                );

                showError(e);

            } finally {

                if (record != null) {
                    historyManager.save(record);
                }
            }
        }

    }             // Download Menu Logic

    private void showError(Exception e) {
        System.out.println("✗ Download failed.");
        System.out.print("Reason: " + e.getMessage());
    }

}
