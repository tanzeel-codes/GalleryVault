package com.tanzeel.galleryvault.download;

import com.tanzeel.galleryvault.config.Config;
import com.tanzeel.galleryvault.exception.AuthenticationRequiredException;
import com.tanzeel.galleryvault.exception.DownloadFailedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GalleryDownloader {
    private static final String AUTHENTICATION = "authentication";
    private static final String FORBIDDEN = "forbidden";
    private static final String UNSUPPORTED_URL = "unsupported url";
    private static final String DIRECTORY_OPTION = "--directory";
    private static final String COOKIES_OPTION = "--cookies";
    private final Config config;

    public GalleryDownloader(Config config) {
        this.config = config;
    }

    public void download(String url) throws DownloadFailedException {
        try {
            List<String> command = buildCommand(url);

            Process process = executeCommand(command);

            String output = readStream(process.getInputStream());

            int exitCode = process.waitFor();                                           // Wait for the command to finish and return its "status"

            validateResult(exitCode, output);

        } catch (IOException e) {
            throw new DownloadFailedException("Unable to start gallery-dl", e);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DownloadFailedException("Download Interrupted", e);
        }
    }

    private Process executeCommand(List<String> commands) throws IOException {

        ProcessBuilder processBuilder = new ProcessBuilder(commands);      //Create the command (can throw IOException)
        processBuilder.redirectErrorStream(true);

        return processBuilder.start();                                     //Runs the command (can throw InterruptedException)
    }

    private void validateResult(int exitCode, String output) throws DownloadFailedException {

        if(exitCode == 0) return;

        String normalizedOutput = output.toLowerCase();

        if(normalizedOutput.contains(UNSUPPORTED_URL)) {
            throw new DownloadFailedException("Unsupported URL.\n\n" + output);
        }

        if(normalizedOutput.contains("403") || normalizedOutput.contains(AUTHENTICATION) || normalizedOutput.contains(FORBIDDEN)) {
            throw new AuthenticationRequiredException("Authentication required");
        }

        throw new DownloadFailedException("Download failed.\n\n" + output);
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

    private List<String> buildCommand(String url) {

        List<String> command = new ArrayList<>();

        command.add(config.getGalleryDlCommand());
        command.add(DIRECTORY_OPTION);          // command for gallery-dl to identify
        command.add(config.getDownloadPath().toString());
        command.add(url);
        // WILL BE ADDING MORE

        return command;
    }
}
