package com.tanzeel.galleryvault.model;

public enum Platform {
    INSTAGRAM("Instagram"),
    REDDIT("Reddit"),
    YOUTUBE("YouTube"),
    UNKNOWN("Unknown"),
    X("X");

    private final String displayName;

    Platform(String displayName) {
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