package com.tanzeel.galleryvault.setup;

import com.tanzeel.galleryvault.config.Config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;

/* This class is responsible for -

    ~Set up the configuration for first time
    ~Ask for downloadPath, cookiesPath and so on
    ~Save the configuration
    ~Loads all the data to config.properties
    ~Update the config.properties (TO DO)

 */
public class SetupManager {
    private static final Path APP_DIRECTORY = Paths.get(System.getProperty("user.home"), ".gallery-vault");
    private static final Path CONFIG_FILE = APP_DIRECTORY.resolve("config.properties");
    private Config config;
    private final Scanner scanner = new Scanner(System.in);

    public void isFirstRun() {
        if(Files.exists(CONFIG_FILE)) return;

        runSetup();
    }

    public void runSetup() {

        String galleryDlCommand = findGalleryDL(scanner);

        Path downloadFolder = askDownloadFolder();

        Path cookiesPath = askCookiesPath();

        config = new Config(
                galleryDlCommand,
                downloadFolder,
                cookiesPath
        );

        saveConfiguration();
    }

    public Config loadConfig(){

        Properties properties = new Properties();

        // IMPORTANT STEP IF NOT LOADED WE WON'T GET ANYTHING THAT WAS SAVED BEFORE
        try (FileInputStream input = new FileInputStream(CONFIG_FILE.toFile())) {

            properties.load(input);                                                         // Loads the file

            String galleryDlCommand = properties.getProperty("galleryDlCommand");           // To get galleryDlCommand

            Path downloadPath = Paths.get(properties.getProperty("downloadFolder"));        // To get the download path

            Path cookiesPath = null;                                                        // To get the cookies (if present)
            if(properties.getProperty("cookiesPath") != null) {
                cookiesPath = Paths.get(properties.getProperty("cookiesPath"));

            }

            return new Config(galleryDlCommand, downloadPath, cookiesPath);

        } catch (IOException e) {
            throw new RuntimeException("Unable to load configuration.", e);

        }

    }

    private String findGalleryDL(Scanner scanner) {

        if(isCommandAvailable("gallery-dl")) return "gallery-dl";

        return askGalleryDLPath(scanner);
    }

    private String askGalleryDLPath(Scanner scanner) {
        while (true) {
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

        }
    }

    private boolean isCommandAvailable(String command) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command, "--version");

            Process process = processBuilder.start();

            int exitCode = process.waitFor();

            if (exitCode == 0) return true;

        } catch (IOException e) {
            System.out.println("gallery-dl not found in command");
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
        return false;
    }

    private Path askDownloadFolder() {

        while(true) {
            System.out.print("Enter the download folder: ");

            Path path = Paths.get(scanner.nextLine().trim());

            try{
                Files.createDirectories(path);

            } catch (IOException e) {
                System.out.println("Unable to create the folder: " + e.getMessage());
                continue;

            }
            return path;

        }
    }

    private Path askCookiesPath() {
        System.out.println("Do you have cookies? (Y/N): ");

        String ans = scanner.nextLine();

        if(!ans.equalsIgnoreCase("Y")) return null;

        while(true) {
            System.out.println("Enter cookies path: ");

            Path path = Paths.get(scanner.nextLine());

            if(Files.isRegularFile(path)) return path;

            System.out.println("Invalid cookies file. ");
        }
    }

    private void saveConfiguration() {

        Properties properties = new Properties();

        properties.setProperty("galleryDlCommand", config.getGalleryDlCommand());

        properties.setProperty("downloadFolder", config.getDownloadPath().toString());

        if(config.getCookiesPath() != null) {
            properties.setProperty("cookiesPath", config.getCookiesPath().toString());

        }
        try {
            Files.createDirectories(APP_DIRECTORY);

            try(FileOutputStream output = new FileOutputStream(CONFIG_FILE.toFile())) {
                properties.store(output, "GalleryVault configuration");         // saves the file

            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to save configuration. ", e);

        }
    }

    public void updateConfiguration() {

        Properties properties = new Properties();

        try(FileInputStream input = new FileInputStream(CONFIG_FILE.toFile())) {
            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        System.out.println("Do you want to update download path? (Y/N)");
        String choice = scanner.nextLine();

        if(choice.equalsIgnoreCase("Y")) {
            properties.setProperty("downloadFolder", askDownloadFolder().toString());

        }

        System.out.println("Do you want to update cookies path? (Y/N)");
        choice = scanner.nextLine();

        if(choice.equalsIgnoreCase("Y")) {
            Path cookies = askCookiesPath();

            if(cookies != null) {
                properties.setProperty("cookiesPath", cookies.toString());

            }

        }

        try{
            Files.createDirectories(APP_DIRECTORY);

            try(FileOutputStream output = new FileOutputStream(CONFIG_FILE.toFile())) {
                properties.store(output, "GalleryVault configuration");             // Saves the file

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
