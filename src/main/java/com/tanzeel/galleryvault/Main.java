package com.tanzeel.galleryvault;

import com.tanzeel.galleryvault.config.Config;
import com.tanzeel.galleryvault.setup.SetupManager;
import com.tanzeel.galleryvault.GalleryVaultApp;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        SetupManager setupManager = new SetupManager();

        // IF THE SETUP HASN'T DONE YET WE WILL RUN THIS
        if(setupManager.isFirstRun()) {
            setupManager.runSetup();
        }

        Config config = setupManager.loadConfig();

        GalleryVaultApp galleryVaultApp = new GalleryVaultApp(config);

        galleryVaultApp.start();

        System.out.println("Thank you!");
    }

}