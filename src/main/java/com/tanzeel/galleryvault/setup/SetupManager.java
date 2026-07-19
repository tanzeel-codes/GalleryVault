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
        // Will implement later
        return new Config();
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

    private void saveConfiguration(String galleryDLPath, String downloadFolder, String cookiesPath) {
    }
}
