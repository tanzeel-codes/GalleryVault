package com.tanzeel.galleryvault.history;

import com.tanzeel.galleryvault.platform.Platform;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HistoryManager {
    private static final Path APP_DIRECTORY = Paths.get(System.getProperty("user.home"), ".gallery-vault");
    private static final Path HISTORY_FILE = APP_DIRECTORY.resolve("history.csv");

    public void save(DownloadRecord record) {

        String line = String.format(
                "%s,%s,%s,%s,%s%n",
                record.getPlatform(),
                record.getTimeStamp(),
                record.getStatus(),
                record.getUrl(),
                record.getReason() == null ? "" : record.getReason()
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

                // We will assign just the way we stored
                Platform platform = Platform.valueOf(parts[0]);
                LocalDateTime timestamp = LocalDateTime.parse(parts[1]);
                DownloadStatus status = DownloadStatus.valueOf(parts[2]);
                String url = parts[3];
                String reason = parts[4].isEmpty() ? null : parts[4];

                DownloadRecord record = new DownloadRecord(
                        platform,
                        timestamp,
                        status,
                        url,
                        reason
                );

                history.add(record);
            }

            return history;

        } catch (IOException e) {
            throw new RuntimeException("Unable to read history.", e);
        }
    }

    public boolean clearHistory() {
        try {
            return Files.deleteIfExists(HISTORY_FILE);

        } catch (IOException e) {
            throw new RuntimeException("Unable to clear history.", e);
        }
    }
}
