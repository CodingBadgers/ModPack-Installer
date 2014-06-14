package com.mcbadgercraft.installer.download;

import io.github.thefishlive.installer.download.SimpleDownload;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class MinecraftDownload extends SimpleDownload {

    public MinecraftDownload(URL downloadUrl, File fileDest) {
        super(downloadUrl, fileDest);
    }

    @Override
    public URL getChecksumUrl() throws IOException {
        if (this.checksumUrl == null) {
            this.checksumUrl = URI.create(getDownloadUrl().toExternalForm() + ".sha").toURL();
        }

        return this.checksumUrl;
    }
}
