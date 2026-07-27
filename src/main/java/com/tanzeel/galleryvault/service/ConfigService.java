package com.tanzeel.galleryvault.service;

import com.tanzeel.galleryvault.config.Config;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class ConfigService {

    public Config getConfig() {
        return new Config(
                "gallery-dl",
                Path.of("E:/Download/Gallery_dl"),
                null
        );
    }
}
