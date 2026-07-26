package com.tanzeel.galleryvault.history;

public enum DownloadStatus {
    SUCCESS("Success"),
    FAILED("Failed");

    private final String displayName;

    DownloadStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

