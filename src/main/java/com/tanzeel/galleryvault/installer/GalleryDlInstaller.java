package com.tanzeel.galleryvault.installer;

import com.tanzeel.galleryvault.util.ApplicationPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
public class GalleryDlInstaller {
    private static final String GALLER_DL_VERSION = "1.31.10";
    private static final String DOWNLOAD_URL = "https://github.com/mikf/gallery-dl/releases/download/v" + GALLER_DL_VERSION +"/gallery-dl.exe";

    private static final Logger log = LoggerFactory.getLogger(GalleryDlInstaller.class);

    public void installIfMissing() {

        Path galleryDl = ApplicationPaths.galleryDl();

        if(Files.exists(galleryDl)) return;

        log.info("gallery-dl not found. Downloading... ");

        try {

            Path executable = downloadGalleryDl();

            log.info("gallery-dl installed successfully at {}", executable);

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new IllegalStateException("Interrupted while installing gallery-dl", e);

        } catch (IOException e) {

            throw new IllegalStateException("Unable to install gallery-dl", e);
        }

    }

    private Path downloadGalleryDl() throws IOException, InterruptedException {

        Path executable = ApplicationPaths.galleryDl();
        Path tempFile = executable.resolveSibling("gallery-dl.exe.tmp");

        try(HttpClient client = HttpClient
                .newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build()
        ) {

            HttpRequest request = HttpRequest.newBuilder()      // Build GET URL request
                    .uri(URI.create(DOWNLOAD_URL))
                    .build();

            HttpResponse<Path> response = client.send(                  // <- Execute HTTP request
                    request,                                                    // <- contains the URL header for request
                    HttpResponse.BodyHandlers.ofFile(tempFile)      // <- Stores the file in designated location
            );

            if (response.statusCode() != 200) {

                throw new IOException("Unable to download gallery-dl. HTTP Status: " + response.statusCode());
            }

            try {

                Files.move(
                        tempFile,
                        executable,
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE
                );

            } catch (AtomicMoveNotSupportedException ex) {

                Files.move(
                        tempFile,
                        executable,
                        StandardCopyOption.REPLACE_EXISTING
                );

            }

            Files.writeString(
                    ApplicationPaths.galleryDlVersion(),
                    GALLER_DL_VERSION
            );

            return executable;

        } catch (IOException | InterruptedException e) {

            Files.deleteIfExists(tempFile);

            throw e;
        }
    }
}
