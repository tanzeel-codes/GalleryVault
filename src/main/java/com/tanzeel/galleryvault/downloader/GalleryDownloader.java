package com.tanzeel.galleryvault.downloader;

import com.tanzeel.galleryvault.config.Config;
import com.tanzeel.galleryvault.exception.DownloadFailedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GalleryDownloader {
    private final Config CONFIG;

    public GalleryDownloader(Config config) {
        this.CONFIG = config;
    }

    public void download(String url) throws DownloadFailedException {
        List<String> command = new ArrayList<>();
        try {
            //Create the command (can throw IOException)
            ProcessBuilder processBuilder = new ProcessBuilder(CONFIG.getGalleryDlCommand(), url);

            processBuilder.redirectErrorStream(true);

            //Runs the command (can throw InterruptedException)
            Process process = processBuilder.start();

            String output = readStream(process.getInputStream());

            //Wait for the command to finish and return its "status"
            int exitCode = process.waitFor();

//            validateResult(exitCode, output);
        } catch (IOException e) {
            throw new DownloadFailedException("Unable to start gallery-dl", e);
        } catch (InterruptedException e) {
            throw new DownloadFailedException("Download Interrupted", e);
        }

        System.out.println("Downloaded in folder: " + CONFIG.getVaultPath());
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

   private Process executeCommand(List<String> commands) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(commands);      //Create the command (can throw IOException)
        processBuilder.redirectErrorStream(true);

        return processBuilder.start();                                       //Runs the command (can throw InterruptedException)
   }

    /*
    STILL HAS SOMETHING TO DO WITH VALIDATE RESULT (WILL DO LATER)
     */
//    private void validateResult(int exitCode, String output) throws DownloadFailedException, AuthenticationRequiredException {
//        if(exitCode == 0) return;
//
//        output =  output.toLowerCase();
//
//        if(output.contains("authentication")) {
//            throw new AuthenticationRequiredException("Authentication required.");
//        }
//    }

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
