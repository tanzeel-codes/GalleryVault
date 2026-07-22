package com.tanzeel.galleryvault;

import com.tanzeel.galleryvault.config.Config;
import com.tanzeel.galleryvault.setup.SetupManager;

public class Main {
    public static void main(String[] args) {

        SetupManager setupManager = new SetupManager();

        setupManager.isFirstRun();            // IF THE SETUP HASN'T DONE YET WE WILL RUN THIS

        Config config = setupManager.loadConfig();  // READS THE DATA FROM CONFIG.PROPERTIES

        GalleryVaultApp app = new GalleryVaultApp(config);
        app.start();


        System.out.println("Thank you!");
    }

}