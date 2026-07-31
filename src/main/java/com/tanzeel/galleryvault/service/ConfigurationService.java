package com.tanzeel.galleryvault.service;

import com.tanzeel.galleryvault.dto.ConfigurationRequest;
import com.tanzeel.galleryvault.dto.ConfigurationResponse;
import com.tanzeel.galleryvault.model.Browser;
import com.tanzeel.galleryvault.model.Configuration;
import com.tanzeel.galleryvault.repository.ConfigurationRepository;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class ConfigurationService {

    private static final Path DOWNLOAD_DIRECTORY = Paths.get(System.getProperty("user.home"), "GalleryVault");

    private final ConfigurationRepository configurationRepository;

    public ConfigurationService(ConfigurationRepository configurationRepository) {

        this.configurationRepository = configurationRepository;
    }


    private Configuration createDefaultConfiguration() {
        Configuration configuration = new Configuration();

        configuration.setDownloadDirectory(DOWNLOAD_DIRECTORY.toString());
        configuration.setBrowser(Browser.BRAVE);
        configuration.setCookiesPath(null);
        configuration.setArchiveEnabled(true);
        configuration.setOverwriteExisting(false);

        return configurationRepository.save(configuration);
    }

    public Configuration getConfiguration() {

        return configurationRepository.findFirstBy().orElseGet(this::createDefaultConfiguration);
    }

    public Configuration updateConfiguration(ConfigurationRequest request) {

        Configuration configuration = getConfiguration();       // we didn't use new configuration because it would create new record with id 2 making the table having  entry making the method findFirstBy useless

        configuration.setDownloadDirectory(request.getDownloadDirectory());
        configuration.setBrowser(request.getBrowser());
        configuration.setCookiesPath(request.getCookiesPath());
        configuration.setArchiveEnabled(request.isArchiveEnabled());
        configuration.setOverwriteExisting(request.isOverwriteExisting());

        return configurationRepository.save(configuration);

    }

    private ConfigurationResponse mapToResponse(Configuration configuration) {
        ConfigurationResponse response = new ConfigurationResponse();

        response.setDownloadDirectory(configuration.getDownloadDirectory());
        response.setBrowser(configuration.getBrowser());
        response.setCookiesPath(configuration.getCookiesPath());
        response.setArchiveEnabled(configuration.isArchiveEnabled());
        response.setOverwriteExisting(configuration.isOverwriteExisting());

        return response;
    }
}
