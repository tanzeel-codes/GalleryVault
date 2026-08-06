package com.tanzeel.galleryvault.service;

import com.tanzeel.galleryvault.dto.ConfigurationRequest;
import com.tanzeel.galleryvault.model.Browser;
import com.tanzeel.galleryvault.model.Configuration;
import com.tanzeel.galleryvault.repository.ConfigurationRepository;
import com.tanzeel.galleryvault.util.ApplicationPaths;
import org.springframework.stereotype.Service;

import java.nio.file.Path;


@Service
public class ConfigurationService {
    private final ConfigurationRepository configurationRepository;

    public ConfigurationService(ConfigurationRepository configurationRepository) {

        this.configurationRepository = configurationRepository;
    }

    private Configuration createDefaultConfiguration() {
        Configuration configuration = new Configuration();

        configuration.setDownloadDirectory(ApplicationPaths.downloads().toString());
        configuration.setBrowser(Browser.BRAVE);
        configuration.setCookiesPath(null);
        configuration.setArchivePath(ApplicationPaths.archive().toString());
        configuration.setArchiveEnabled(true);
        configuration.setOverwriteExisting(false);

        return configurationRepository.save(configuration);
    }

    public Configuration getConfiguration() {

        return configurationRepository.findFirstBy().orElseGet(this::createDefaultConfiguration);
    }

    public Configuration updateConfiguration(ConfigurationRequest request) {

        Configuration configuration = getConfiguration();

        configuration.setDownloadDirectory(request.getDownloadDirectory());
        configuration.setBrowser(request.getBrowser());
        configuration.setCookiesPath(request.getCookiesPath());
        configuration.setArchivePath(request.getArchivePath());
        configuration.setArchiveEnabled(request.isArchiveEnabled());
        configuration.setOverwriteExisting(request.isOverwriteExisting());

        return configurationRepository.save(configuration);

    }
}
