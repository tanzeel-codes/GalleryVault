package com.tanzeel.galleryvault;

import com.tanzeel.galleryvault.app.GalleryVaultApp;
import com.tanzeel.galleryvault.config.Config;
import com.tanzeel.galleryvault.setup.SetupManager;

public class Main {
    public static void main(String[] args) {

        GalleryVaultApp app = new GalleryVaultApp();
        app.start();

        System.out.println("Thank you!");
    }

}