package com.tanzeel.galleryvault.downloader;

import java.io.IOException;
import java.io.InterruptedIOException;

public class GalleryDownloader {
    public boolean download(String url) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("gallery-dl", url);

        Process process = processBuilder.start();

        int exitCode = process.waitFor();

        if(exitCode != 0) return false;



        return false;
    }
}
