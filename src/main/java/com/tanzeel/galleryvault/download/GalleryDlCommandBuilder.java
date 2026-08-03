package com.tanzeel.galleryvault.download;

import com.tanzeel.galleryvault.model.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GalleryDlCommandBuilder {
    private static final String GALLERY_DL = "src/main/java/com/tanzeel/galleryvault/tools/gallery-dl.exe";
    private static final String DIRECTORY_OPTION = "--directory";
    private static final String COOKIES_OPTION = "--cookies";
    private static final String COOKIES_FROM_BROWSER = "--cookies-from-browser";
    private static final String DOWNLOAD_ARCHIVE_OPTION = "--download-archive";


    public List<String> build(String url, Configuration configuration, DownloadOptions options) {

        List<String> command = new ArrayList<>();

        command.add(GALLERY_DL);

        if(options.isUsingBrowserCookies()) {    // will try --cookies-from-browser "browserName"
            command.add(COOKIES_FROM_BROWSER);
            command.add(configuration.getBrowser().getDisplayName());
        }

        if(options.isUsingCookiesPath()) {
            command.add(COOKIES_OPTION);
            command.add(configuration.getCookiesPath());
        }

        if(configuration.isArchiveEnabled()) {
            command.add(DOWNLOAD_ARCHIVE_OPTION);
            command.add(configuration.getArchivePath());
        }

        command.add(DIRECTORY_OPTION);          // command for gallery-dl to identify
        command.add(configuration.getDownloadDirectory());
        command.add(url);

        return command;
    }
}
