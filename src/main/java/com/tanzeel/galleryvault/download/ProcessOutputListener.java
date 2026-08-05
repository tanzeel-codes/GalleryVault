package com.tanzeel.galleryvault.download;

@FunctionalInterface
public interface ProcessOutputListener {

    void onOutput(String line);
}
