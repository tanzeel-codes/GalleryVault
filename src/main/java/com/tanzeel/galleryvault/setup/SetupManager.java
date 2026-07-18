package com.tanzeel.galleryvault.setup;

import com.tanzeel.galleryvault.config.Config;
import com.tanzeel.galleryvault.exception.DownloadFailedException;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/* This class is responsible for set up the configuration
    If the run is firs time ->  we go for setup
    Otherwise continue
 */
public class SetupManager {

    public boolean isFirstRun() {
        Path configPath = Paths.get("config.properties");
        return !Files.exists(configPath);
    }

    public void runSetup() {
        Scanner scanner = new Scanner(System.in);

        String galleryDLPath = findGalleryDL(scanner);

        String downloadFolder = setDownloadFolder(scanner);

        String cookiesPath = askCookiesPath(scanner);

        saveConfiguration(galleryDLPath, downloadFolder, cookiesPath);
    }

    public Config loadConfig(){

        return new Config();
    }

    private String findGalleryDL(Scanner scanner) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("gallery-dl", "--version");

            Process process = processBuilder.start();

            int exitCode = process.waitFor();

            if (exitCode == 0) return "gallery-dl";

        } catch (IOException e) {
            System.out.println("gallery-dl not found in command");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Please Enter path to gallery-dl.exe: ");
        return scanner.next();
    }

    private String setDownloadFolder(Scanner scanner) {
        System.out.println("Enter the download location: ");

        return scanner.next();
    }

    private String askCookiesPath(Scanner scanner) {
        return "";
    }

    private void saveConfiguration(String galleryDLPath, String downloadFolder, String cookiesPath) {
    }
}
