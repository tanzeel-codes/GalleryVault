package com.tanzeel.galleryvault.util;

import org.springframework.stereotype.Component;

@Component
public class PlatformDetector {

    public Platform detect(String url) {

        if(url.isEmpty()) return Platform.UNKNOWN;

        url = url.toLowerCase();

        if(url.contains("instagram.com")) return Platform.INSTAGRAM;

        if(url.contains("reddit.com")) return Platform.REDDIT;

        if(url.contains("x.com") || url.contains("twitter.com")) return Platform.X;

        if(url.contains("youtube.com") || url.contains("youtu.be")) return Platform.YOUTUBE;

        return Platform.UNKNOWN;
    }
}