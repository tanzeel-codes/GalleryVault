package com.tanzeel.galleryvault.history;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class HistoryManager {
    private static final Path APP_DIRECTORY = Paths.get(System.getProperty("user.home"), ".gallery-vault");
    private static final Path HISTORY_FILE = APP_DIRECTORY.resolve("history.csv");

    public void save(DownloadRecord record) {
        String line = String.format(
                "%s,%s,%s, $%s%n",
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
}
