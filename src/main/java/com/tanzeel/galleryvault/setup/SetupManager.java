package com.tanzeel.galleryvault.setup;

import com.tanzeel.galleryvault.config.Config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/* This class is responsible for set up the configuration
    If the run is firs time ->  we go for setup
    Otherwise continue
 */
public class SetupManager {
    private static final Path CONFIG_FILE = Paths.get("config.properties");

    public boolean isFirstRun() {
        return !Files.exists(CONFIG_FILE);
    }

    public void runSetup() {
        Scanner scanner = new Scanner(System.in);

        String galleryDLPath = findGalleryDL(scanner);

        String downloadFolder = setDownloadFolder(scanner);

        String cookiesPath = askCookiesPath(scanner);

        saveConfiguration(galleryDLPath, downloadFolder, cookiesPath);
    }

    public Config loadConfig(){
        Properties properties = new Properties();

        // IMPORTANT STEP IF NOT LOADED WE WONT GET ANYTHING THAT WAS SAVED BEFORE
        try{
            FileInputStream input = new FileInputStream(CONFIG_FILE.toFile());

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Unable to load configuration.", e);
        }

        // To get galleryDlCommand
        String galleryDlCommand = properties.getProperty("galleryDlCommand");

        // To Get the download folder path
        String folder = properties.getProperty("downloadFolder");
        Path downloadFolder = Paths.get(folder);

        // To get the cookies (if present)
        String cookies = properties.getProperty("cookiesPath");
        Path cookiesPath = null;
        if(cookies != null) {
            cookiesPath = Paths.get(cookies);
        }

        return new Config(galleryDlCommand, downloadFolder, cookiesPath);
    }

    private String findGalleryDL(Scanner scanner) {

        if(isCommandAvailable("gallery-dl")) return "gallery-dl";

        return askGalleryDLPath(scanner);
    }

    private String askGalleryDLPath(Scanner scanner) {
        do{
            System.out.println("Please Enter path to gallery-dl.exe: ");

            Path path = Paths.get(scanner.nextLine().trim());

            if(!Files.isRegularFile(path)) {
                System.out.println("Please Enter valid path");
                continue;
            }

            if(path.getFileName().toString().equalsIgnoreCase("gallery-dl.exe")) {
                System.out.println("Please select gallery-dl.exe");
                continue;
            }

            if(!isCommandAvailable(path.toString())) {
                System.out.println("The executable is not working. ");
                continue;
            }

            return path.toString();

        } while (true);
    }

    private boolean isCommandAvailable(String command) {
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

    private void saveConfiguration(String galleryDlCommand, Path downloadFolder, Path cookiesPath) {
        Properties properties = new Properties();

        properties.setProperty("galleryDlCommand", galleryDlCommand);

        properties.setProperty("downloadFolder", downloadFolder.toString());

        if(cookiesPath != null) {
            properties.setProperty("cookiesPath", cookiesPath.toString());
        }

        try{
            FileOutputStream output = new FileOutputStream(CONFIG_FILE.toFile());

            properties.store(output, "GalleryVault configuration");

        } catch (IOException e) {
            throw new RuntimeException("Unable to save configuration. ", e);
        }
    }
}
