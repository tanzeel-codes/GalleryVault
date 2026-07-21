package com.tanzeel.galleryvault;

import com.tanzeel.galleryvault.config.Config;
import com.tanzeel.galleryvault.setup.SetupManager;

public class Main {
    public static void main(String[] args) {

        SetupManager setupManager = new SetupManager();

        if(setupManager.isFirstRun()) {             // IF THE SETUP HASN'T DONE YET WE WILL RUN THIS
            setupManager.runSetup();
        }

        Config config = setupManager.loadConfig();

        GalleryVaultApp app = new GalleryVaultApp(config);
        app.start();


        System.out.println("Thank you!");
    }

}