package com.tanzeel.galleryvault.model;

public enum Browser {
    BRAVE("brave"),
    CHROME("chrome"),
    FIREFOX("firefox"),
    EDGE("edge");

    private final String displayName;

    Browser (String displayName) {
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
