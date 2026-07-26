package com.tanzeel.galleryvault.history;

import com.tanzeel.galleryvault.platform.Platform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class HistoryManager {
    private static final Path APP_DIRECTORY = Paths.get(System.getProperty("user.home"), ".gallery-vault");
    private static final Path HISTORY_FILE = APP_DIRECTORY.resolve("history.csv");

    public void save(DownloadRecord record) {

        String line = String.format(
                "%s,%s,%s,%s,%s,%s%n",
                record.getPlatform().name(),    // part[0]
                record.getTimestamp(),          // part[1]
                record.getStatus().name(),      // part[2]
                record.getUrl(),                // part[3]
                record.getDuration(),           // part[4]
                record.getReason() == null ? "" : record.getReason()    // part[5]
        );

        try {
            Files.writeString(HISTORY_FILE, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);

        } catch (IOException e) {
            throw new RuntimeException("Unable to save history.", e);

        }
    }

    public List<DownloadRecord> readHistory() {

        if(!Files.exists(HISTORY_FILE)) return Collections.emptyList();

        try {
            List<String> allLine = Files.readAllLines(HISTORY_FILE);        // load all the data from csv separated by line

            List<DownloadRecord> history = new ArrayList<>();

            for(String line : allLine) {            // Traversing on each line

                String[] parts = line.split(",",-1);        // store each string separated by comma

                // We will assign just the way we stored in csv
                Platform platform       = Platform.valueOf(parts[0]);
                LocalDateTime timestamp = LocalDateTime.parse(parts[1]);
                DownloadStatus status   = DownloadStatus.valueOf(parts[2]);
                String url              = parts[3];
                Duration duration       = Duration.parse(parts[4]);
                String reason           = parts[5].isEmpty() ? null : parts[5];

                DownloadRecord record = new DownloadRecord(
                        platform,
                        timestamp,
                        status,
                        url,
                        duration,
                        reason
                );

                history.add(record);
            }

            return history;

        } catch (IOException e) {
            throw new RuntimeException("Unable to read history.", e);
        }
    }

    public List<DownloadRecord> getHistory(SortOrder sortOrder) {

        return filterHistory(
                record -> true, sortOrder
        );
    }           // Get all history

    public  List<DownloadRecord> getSuccessfulHistory(SortOrder sortOrder) {
        return filterHistory(
                record -> record.getStatus() == DownloadStatus.SUCCESS, sortOrder
        );
    }   // Get successful History

    public List<DownloadRecord> getFailedHistory(SortOrder sortOrder) {
        return filterHistory(
                record -> record.getStatus() == DownloadStatus.FAILED, sortOrder
        );
    }       // Get Failed History

    public List<DownloadRecord> getHistoryByPlatform(Platform platform, SortOrder sortOrder) {
        return filterHistory(
                record -> record.getPlatform() == platform, sortOrder
        );
    }   // Get History based on Platform

    public List<DownloadRecord> searchHistory(String keyword, SortOrder sortOrder) {
        String search = keyword.toLowerCase().trim();

        return filterHistory(
                record -> {
                    boolean platformMatches = record.getPlatform().name().toLowerCase().contains(search);
                    boolean statusMatches = record.getStatus().name().toLowerCase().contains(search);
                    boolean urlMatches = record.getUrl().toLowerCase().contains(search);

                    return  platformMatches
                            || statusMatches
                            || urlMatches;
                },

                sortOrder
        );
    }       // Search by keyword

    private List<DownloadRecord> filterHistory(Predicate<DownloadRecord> condition, SortOrder sortOrder) {
        List<DownloadRecord> history = readHistory();

        List<DownloadRecord> filteredHistory = new ArrayList<>();

        for(DownloadRecord record : history) {

            if(condition.test(record)) {
                filteredHistory.add(record);
            }
        }

        Comparator<DownloadRecord> comparator = Comparator.comparing(DownloadRecord::getTimestamp);

        if(sortOrder == SortOrder.NEWEST_FIRST) {
            comparator = comparator.reversed();
        }

        filteredHistory.sort(comparator);

        return filteredHistory;
    }

    public String formatDuration(Duration duration) {
        long millis = duration.toMillis();

        if(millis < 1000) {
            return millis + "ms";
        }

        long seconds = duration.getSeconds();

        if(seconds < 60) {
            return String.format("%.1f sec", millis / 1000.0);
        }

        long minutes = seconds / 60;

        long remainingSeconds = seconds % 60;

        return minutes + " min" + remainingSeconds + " sec";
    }

    public boolean clearHistory() {
        try {
            return Files.deleteIfExists(HISTORY_FILE);

        } catch (IOException e) {
            throw new RuntimeException("Unable to clear history.", e);
        }
    }
}
