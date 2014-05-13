package com.mcbadgercraft.installer.download;

import com.mcbadgercraft.installer.Bootstrap;
import io.github.thefishlive.installer.download.SimpleDownload;
import io.github.thefishlive.installer.exception.InstallerException;
import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class CompressedDownload extends SimpleDownload {

    private static final String EXTENTION = ".pack.xz";

    @Getter
    private URL downloadUrl;
    @Getter
    private URL uncompressedUrl;
    @Getter
    private File fileDest;
    @Getter
    private File uncompressedDest;

    public CompressedDownload(URL url, File dest) throws IOException {
        super(url, dest);
        this.downloadUrl = URI.create(url.toString() + EXTENTION).toURL();
        this.fileDest = new File(dest.getAbsolutePath() + EXTENTION);

        this.uncompressedUrl = url;
        this.uncompressedDest = dest;
    }

    @Override
    public boolean setup() throws InstallerException {
        try { // If we can't find the file, try the uncompressed version
            return super.setup();
        } catch (InstallerException ex) {
            if (ex.getCause() instanceof FileNotFoundException) {
                downloadUrl = uncompressedUrl;
                fileDest = uncompressedDest;
                Bootstrap.getLog().debug("Could not find compressed version for " + fileDest.getName() + ", falling back to uncompressed download.");
                return super.setup();
            } else {
                throw ex;
            }
        }
    }
}
