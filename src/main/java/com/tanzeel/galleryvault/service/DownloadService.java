package com.tanzeel.galleryvault.service;

import com.tanzeel.galleryvault.download.*;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DownloadService {

    private static final int SUCCESS_EXIT_CODE = 0;
    private static final String AUTHENTICATION = "authentication";
    private static final String FORBIDDEN = "forbidden";
    private static final String UNSUPPORTED_URL = "unsupported url";
    private static final String LOGIN_ERROR = "login";
    private static final String HTTP_403 = "403";
    private static final Logger logger = LoggerFactory.getLogger(DownloadService.class);

    private final PlatformDetector platformDetector;
    private final HistoryService historyService;
    private final ConfigurationService configurationService;
    private final GalleryDlCommandBuilder commandBuilder;
    private final ProcessExecutor processExecutor;

    public DownloadService(
            PlatformDetector platformDetector,
            ConfigurationService configurationService,
            HistoryService historyService,
            GalleryDlCommandBuilder commandBuilder,
            ProcessExecutor processExecutor
    ) {

        this.configurationService = configurationService;
        this.platformDetector = platformDetector;
        this.historyService = historyService;
        this.commandBuilder = commandBuilder;
        this.processExecutor = processExecutor;

    }

    public void download(String url, ProcessOutputListener listener) throws DownloadFailedException {

        Platform platform = platformDetector.detect(url);

        DownloadStatus status = DownloadStatus.FAILED;

        Configuration configuration = configurationService.getConfiguration();

        List<DownloadOptions> authenticationStrategies = List.of(
                DownloadOptions.normal(),
                DownloadOptions.browserCookies(),
                DownloadOptions.cookiesFile()
        );

        AuthenticationRequiredException lastAuthException = null;

        try {

            for(DownloadOptions options : authenticationStrategies) {

                if(options.isUsingCookiesPath() && configuration.getCookiesPath() == null) {    // if it goes for last attempt then we can break early if there is no cookies path

                    lastAuthException = new  AuthenticationRequiredException("Authentication required. Please configure a cookies.txt file");
                    break;
                }

                try {

                    attemptDownload(url, configuration, options, listener);

                    status = DownloadStatus.SUCCESS;

                    return;

                }
                catch (AuthenticationRequiredException e) {
                    lastAuthException = e;
                }

            }

            throw lastAuthException;

        }
        finally {

            try {
                saveHistory(url, platform, status);

            } catch (Exception e) {
                logger.error("Unable to save download history", e);
            }
        }
    }

    private void attemptDownload(String url, Configuration configuration, DownloadOptions options, ProcessOutputListener listener) throws DownloadFailedException {

        try {

            List<String> command = commandBuilder.build(url, configuration, options);

            ProcessResult result = processExecutor.execute(command, listener);

            validateResult(result.getExitCode(), result.getOutput());

        }
        catch (IOException e) {

            throw new DownloadFailedException("Unable to start gallery-dl", e);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new DownloadFailedException("Download Interrupted", e);
        }

    }

    private void validateResult(int exitCode, String output) throws DownloadFailedException {

        if(exitCode == SUCCESS_EXIT_CODE) return;

        String normalizedOutput = output.toLowerCase();

        if(normalizedOutput.contains(UNSUPPORTED_URL)) {
            throw new DownloadFailedException("Unsupported URL : " + output);
        }

        if(normalizedOutput.contains(HTTP_403)
                || normalizedOutput.contains(AUTHENTICATION)
                || normalizedOutput.contains(FORBIDDEN)
                || normalizedOutput.contains(LOGIN_ERROR)
        ) {
            throw new AuthenticationRequiredException("Authentication required");
        }

        throw new DownloadFailedException("Download failed. " + output);
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
