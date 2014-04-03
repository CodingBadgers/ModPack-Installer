package com.mcbadgercraft.installer.download;

import io.github.thefishlive.installer.download.Download;
import io.github.thefishlive.installer.download.SimpleDownload;
import io.github.thefishlive.installer.exception.InstallerException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import com.mcbadgercraft.installer.Bootstrap;

import lombok.Getter;

public class CompressedDownload extends Download {

	private static final String EXTENTION = ".pack.xz";
	
	@Getter private URL downloadUrl;
	@Getter private URL uncompressedUrl;
	@Getter private File fileDest;
	@Getter private File uncompressedDest;

	public CompressedDownload(URL url, File dest) throws IOException {
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

	@Override
	public Download clone() {
		Download download = new SimpleDownload(this.downloadUrl, this.fileDest);
		download.active = this.active;
		return download;
	}
}
