package com.tanzeel.galleryvault.service;

import com.tanzeel.galleryvault.config.Config;
import com.tanzeel.galleryvault.exception.AuthenticationRequiredException;
import com.tanzeel.galleryvault.exception.DownloadFailedException;
import com.tanzeel.galleryvault.model.DownloadHistory;
import com.tanzeel.galleryvault.model.DownloadStatus;
import com.tanzeel.galleryvault.model.Platform;
import com.tanzeel.galleryvault.util.PlatformDetector;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadService {

    private static final String AUTHENTICATION = "authentication";
    private static final String FORBIDDEN = "forbidden";
    private static final String UNSUPPORTED_URL = "unsupported url";
    private static final String DIRECTORY_OPTION = "--directory";
    private static final String COOKIES_OPTION = "--cookies";

    private final ConfigService configService;
    private final PlatformDetector platformDetector;
    private final HistoryService historyService;

    public DownloadService(PlatformDetector platformDetector,
                           ConfigService configService,
                           HistoryService historyService
    ) {
        this.configService = configService;
        this.platformDetector = platformDetector;
        this.historyService = historyService;

    }

    public void download(String url) throws DownloadFailedException {

        Platform platform = platformDetector.detect(url);

        try {

            // will move the create command to different class (command builder)
            List<String> command = buildCommand(url);

            // move to different class (process Executer)
            Process process = executeCommand(command);

            // will move to util (streamutil)
            String output = readStream(process.getInputStream());

            int exitCode = process.waitFor();                                           // Wait for the command to finish and return its "status"

            DownloadHistory downloadHistory = new DownloadHistory(
                    url,
                    platform,
                    DownloadStatus.SUCCESS,
                    LocalDateTime.now()
            );

            if(exitCode == 0) {
                historyService.save(downloadHistory);
            }

            validateResult(exitCode, output);

        } catch (IOException e) {
            throw new DownloadFailedException("Unable to start gallery-dl", e);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new DownloadFailedException("Download Interrupted", e);
        }
    }

    private List<String> buildCommand(String url) {

        Config config = configService.getConfig();

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

        return processBuilder.start();                                     //Runs the command (can throw InterruptedException)
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

}
