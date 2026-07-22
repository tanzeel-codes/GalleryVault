package com.tanzeel.galleryvault.history;

public class HistoryManager {
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
