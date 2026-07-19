package com.tanzeel.galleryvault.downloader;

import com.tanzeel.galleryvault.config.Config;
import com.tanzeel.galleryvault.exception.AuthenticationRequiredException;
import com.tanzeel.galleryvault.exception.DownloadFailedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GalleryDownloader {
    private final Config CONFIG;

    public GalleryDownloader(Config config) {
        this.CONFIG = config;
    }

    public void download(String url) throws IOException, InterruptedException, DownloadFailedException{
        //Create the command (can throw IOException)
        ProcessBuilder processBuilder = new ProcessBuilder(CONFIG.getGalleryDlCommand(), url);

        processBuilder.redirectErrorStream(true);

        //Runs the command (can throw InterruptedException)
        Process process = processBuilder.start();

        String output = readStream(process.getInputStream());

        //Wait for the command to finish and return its "status"
        int exitCode = process.waitFor();

        validateResult(exitCode, output);

        System.out.println("Downloaded in folder: " + CONFIG.getVaultPath());
    }

    private void validateResult(int exitCode, String output) throws DownloadFailedException, AuthenticationRequiredException {
        if(exitCode == 0) return;

        output =  output.toLowerCase();

        if(output.contains("authentication")) {
            throw new AuthenticationRequiredException("Authentication required.");
        }

        throw new DownloadFailedException("gallery-dl failed.\n\n" + output);
    }

    private String readStream(InputStream stream) throws IOException {
        StringBuilder output = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;

            while((line = reader.readLine()) != null) {
                output.append(line)
                      .append(System.lineSeparator());

            }
        }

        return output.toString();
    }
}
