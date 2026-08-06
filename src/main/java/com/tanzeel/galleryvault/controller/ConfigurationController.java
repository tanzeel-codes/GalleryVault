package com.tanzeel.galleryvault.controller;

import com.tanzeel.galleryvault.dto.ConfigurationRequest;
import com.tanzeel.galleryvault.dto.ConfigurationResponse;
import com.tanzeel.galleryvault.mapper.ConfigurationMapper;
import com.tanzeel.galleryvault.service.ConfigurationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/config")
public class ConfigurationController {

    private final ConfigurationService configurationService;
    private final ConfigurationMapper configurationMapper;

    public ConfigurationController(ConfigurationService configurationService, ConfigurationMapper configurationMapper) {
        this.configurationService = configurationService;
        this.configurationMapper = configurationMapper;
    }

    @GetMapping
    public ConfigurationResponse getConfiguration() {

        return configurationMapper.toResponse(configurationService.getConfiguration());
    }

    @PutMapping
    public ConfigurationResponse updateConfiguration(@Valid @RequestBody ConfigurationRequest request) {

        return configurationMapper.toResponse(configurationService.updateConfiguration(request));
    }
}

