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
    }

    private void validateResult(int exitCode, String output) throws DownloadFailedException, AuthenticationRequiredException {
        if(exitCode == 0) return;

        output =  output.toLowerCase();

        if(output.contains("authentication")) {
            throw new AuthenticationRequiredException("Authentication required.");
        }

        return true;
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
