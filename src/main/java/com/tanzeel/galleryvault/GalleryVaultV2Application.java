package com.tanzeel.galleryvault;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class GalleryVaultV2Application implements CommandLineRunner {

    @Override
    public void run(String... args) {
        System.out.println("Application Started");
    }

    public static void main(String[] args) {
        SpringApplication.run(GalleryVaultV2Application.class, args);
    }
}
