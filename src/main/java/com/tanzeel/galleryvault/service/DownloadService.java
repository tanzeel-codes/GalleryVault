package com.tanzeel.galleryvault.service;

import com.tanzeel.galleryvault.exception.AuthenticationRequiredException;
import com.tanzeel.galleryvault.exception.DownloadFailedException;
import com.tanzeel.galleryvault.model.Configuration;
import com.tanzeel.galleryvault.model.DownloadHistory;
import com.tanzeel.galleryvault.model.DownloadStatus;
import com.tanzeel.galleryvault.model.Platform;
import com.tanzeel.galleryvault.util.PlatformDetector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DownloadService {

    private static final int SUCCESS_EXIT_CODE = 0;
    private static final String AUTHENTICATION = "authentication";
    private static final String FORBIDDEN = "forbidden";
    private static final String UNSUPPORTED_URL = "unsupported url";
    private static final String DIRECTORY_OPTION = "--directory";
//    private static final Path GALLERY_DL = Paths.get("tools", "gallery-dl.exe");
    private static final String GALLERY_DL = "src/main/java/com/tanzeel/galleryvault/tools/gallery-dl.exe";
    private static final String COOKIES_OPTION = "--cookies";

    private final ConfigurationService configurationService;
    private final PlatformDetector platformDetector;
    private final HistoryService historyService;
    private static final Logger logger = LoggerFactory.getLogger(DownloadService.class);

    public DownloadService(PlatformDetector platformDetector,
                           ConfigurationService configurationService,
                           HistoryService historyService
    ) {
        this.configurationService = configurationService;
        this.platformDetector = platformDetector;
        this.historyService = historyService;

    }

    public void download(String url) throws DownloadFailedException {

        Platform platform = platformDetector.detect(url);

        DownloadStatus status = DownloadStatus.FAILED;

        int exitCode = -1;

        try {

            // will move the create command to different class (command builder)
            List<String> command = buildCommand(url);

            // move to different class (process Executer)
            Process process = executeCommand(command);

            // will move to util (streamutil)
            String output = readStream(process.getInputStream());

            exitCode = process.waitFor();                                           // Wait for the command to finish and return its "status"

            status = exitCode == SUCCESS_EXIT_CODE ? DownloadStatus.SUCCESS : DownloadStatus.FAILED;

            validateResult(exitCode, output);

        } catch (IOException e) {

            throw new DownloadFailedException("Unable to start gallery-dl", e);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new DownloadFailedException("Download Interrupted", e);
        }
        finally {
            try {

                saveHistory(url, platform, status);
            } catch (Exception e) {
                logger.error("Unable to save history");
            }
        }
    }

    private List<String> buildCommand(String url) {

        Configuration configuration = configurationService.getConfiguration();

        List<String> command = new ArrayList<>();

        command.add(GALLERY_DL);
        // NEED TO ADD --COOKIES-FROM-BROWSER
        // BROWSER NAME
        command.add(DIRECTORY_OPTION);          // command for gallery-dl to identify
        command.add(configuration.getDownloadDirectory());
        command.add(url);

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

    private void saveHistory(String url, Platform platform, DownloadStatus status) {

        DownloadHistory downloadHistory = new DownloadHistory(
                url,
                platform,
                status,
                LocalDateTime.now()
        );

        historyService.save(downloadHistory);
    }

}
