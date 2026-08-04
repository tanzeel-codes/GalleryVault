package com.tanzeel.galleryvault.mapper;

import com.tanzeel.galleryvault.dto.ConfigurationResponse;
import com.tanzeel.galleryvault.model.Configuration;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationMapper {

    public ConfigurationResponse toResponse(Configuration configuration) {

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
