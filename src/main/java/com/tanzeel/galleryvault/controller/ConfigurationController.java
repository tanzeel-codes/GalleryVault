package com.tanzeel.galleryvault.controller;

import com.tanzeel.galleryvault.dto.ConfigurationRequest;
import com.tanzeel.galleryvault.dto.ConfigurationResponse;
import com.tanzeel.galleryvault.model.Configuration;
import com.tanzeel.galleryvault.service.ConfigurationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @GetMapping("/show")
    public ConfigurationResponse getConfiguration() {

        return mapToResponse(configurationService.getConfiguration());
    }

    @PutMapping("/update")
    public ConfigurationResponse updateConfiguration(@Valid @RequestBody ConfigurationRequest request) {

        return mapToResponse(configurationService.updateConfiguration(request));
    }

    private ConfigurationResponse mapToResponse(Configuration configuration) {

        ConfigurationResponse response = new ConfigurationResponse();

        response.setDownloadDirectory(configuration.getDownloadDirectory());
        response.setBrowser(configuration.getBrowser());
        response.setCookiesPath(configuration.getCookiesPath());
        response.setArchivePath(configuration.getArchivePath());
        response.setArchiveEnabled(configuration.isArchiveEnabled());
        response.setOverwriteExisting(configuration.isOverwriteExisting());

        return response;
    }
}

