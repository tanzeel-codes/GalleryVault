package com.tanzeel.galleryvault.download;

import com.tanzeel.galleryvault.exception.DownloadFailedException;
import com.tanzeel.galleryvault.history.DownloadRecord;
import com.tanzeel.galleryvault.history.DownloadStatus;
import com.tanzeel.galleryvault.history.HistoryManager;
import com.tanzeel.galleryvault.platform.Platform;
import com.tanzeel.galleryvault.platform.PlatformDetector;

import java.nio.file.attribute.FileAttribute;
import java.time.Duration;
import java.time.Instant;
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

            Platform platform = platformDetector.detect(url);

            DownloadStatus status = null;
            String reason = null;

            Instant start = Instant.now();        // Time starts

            try {

                downloader.download(url);             // actual start

                status = DownloadStatus.SUCCESS;

                System.out.println("✓ Download Completed Successfully.");

            } catch (DownloadFailedException e) {

                status = DownloadStatus.FAILED;

                reason = e.getMessage();

                System.out.println("✗ Download failed.");
                System.out.println("Reason: " + e.getMessage());

            } finally {
                Instant end = Instant.now();        // Time end

                Duration duration = Duration.between(start, end);

                System.out.println("Time taken - " + historyManager.formatDuration(duration));

                LocalDateTime timestamp = LocalDateTime.now();

                DownloadRecord record = new DownloadRecord(
                        platform,
                        timestamp,
                        status,
                        url,
                        duration,
                        reason
                );

                historyManager.save(record);
                System.out.println();
            }
        }

    }             // Download Menu Logic

}
