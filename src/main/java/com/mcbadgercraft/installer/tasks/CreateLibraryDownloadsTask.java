package com.mcbadgercraft.installer.tasks;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.mcbadgercraft.installer.ModPackInstaller;
import com.mcbadgercraft.installer.Utils;
import com.mcbadgercraft.installer.config.VersionData.LibraryInfo;

import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.download.SimpleDownload;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;

public class CreateLibraryDownloadsTask extends Task {

	private static final String LIBRARY_EXTENSION = "jar.pack.xz";
	private File launcherdir;

	public CreateLibraryDownloadsTask(File launcherdir) {
		super("createLibDownloads");
		this.launcherdir = launcherdir;
	}

	@Override
	public boolean perform(Installer installer) throws InstallerException {
		if (!(installer instanceof ModPackInstaller)) {
			return false;
		}
		
		ModPackInstaller modpack = (ModPackInstaller) installer;
		
		for (LibraryInfo lib : modpack.getConfig().getVersionInfo().getLibraries()) {
			try {
				if (lib.getClientreq() == Boolean.TRUE) {
					String baseurl = lib.getUrl();
					
					if (baseurl == null) {
						baseurl = "https://libraries.minecraft.net/";
					}
					
					String path = Utils.createPath(lib.getName(), LIBRARY_EXTENSION);
					baseurl += path;
					
					File dest = new File(launcherdir, "libraries" + File.separator + path);
					URL url = new URL(baseurl.replace(File.separatorChar, '/'));
					
					if (!dest.getParentFile().exists()) {
						dest.getParentFile().mkdirs();
					}
					
					installer.addDownload(new SimpleDownload(url, dest));
				}
			} catch (IOException ex) {
				throw new InstallerException(ex);
			}
		}
		
		return true;
	}
	

}
