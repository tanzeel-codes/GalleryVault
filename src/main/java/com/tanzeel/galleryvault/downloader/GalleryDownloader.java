package com.tanzeel.galleryvault.downloader;

import com.tanzeel.galleryvault.config.Config;
import com.tanzeel.galleryvault.exception.DownloadFailedException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class GalleryDownloader {
    ArrayList<String> command;

    public boolean download(String url, Config config) throws IOException, InterruptedException, DownloadFailedException {
        command = new ArrayList<>();
        command.add("gallery-dl");
        command.add(url);

//        command.add(config.getProperty("galleryDLPath"));

        //Create the command (can throw IOException)
        ProcessBuilder processBuilder = new ProcessBuilder(command);

        //Runs the command (can throw InterruptedException)
        Process process = processBuilder.start();

        //Wait for the command to finish and return its "status"
        int exitCode = process.waitFor();

        //This doesn't throw exception so we use because there maybe some factor the code didn't success
        if(exitCode != 0) {
            /* 0 - Success
               1 -
               2 -
               3 -
             */
            throw new DownloadFailedException("gallery-dl failed with exit code: " + exitCode);
        }

        return true;
    }
}
