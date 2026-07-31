package com.tanzeel.galleryvault.download;

public class DownloadOptions {

    private final boolean useBrowserCookies;

    private final boolean useCookiesPath;


    public DownloadOptions(boolean useBrowserCookies, boolean useCookiesPath) {
        this.useBrowserCookies = useBrowserCookies;
        this.useCookiesPath = useCookiesPath;
    }

    public static DownloadOptions normal() {
        return new DownloadOptions(false, false);
    }

    public static DownloadOptions browserCookies() {
        return new DownloadOptions(true, false);
    }

    public static DownloadOptions cookiesFile() {
        return new DownloadOptions(false, true);
    }

    public boolean isUsingBrowserCookies() {
        return useBrowserCookies;
    }

    public boolean isUsingCookiesPath() {
        return useCookiesPath;
    }
}
