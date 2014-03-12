package com.mcbadgercraft.installer.tasks;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.mcbadgercraft.installer.ModPackInstaller;

import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.download.SimpleDownload;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;

public class CreateMcDownloadTask extends Task {

	private File launcherdir;

	public CreateMcDownloadTask(File launcherdir) {
		super("createMcDownload");
		
		this.launcherdir = launcherdir;
	}

	@Override
	public boolean perform(Installer installer) throws InstallerException {
		if (!(installer instanceof ModPackInstaller)) {
			return false;
		}
		
		ModPackInstaller modpack = (ModPackInstaller) installer;
		
		try {
			String version = modpack.getConfig().getInstall().getVersion().getMinecraft();
			
			URL url = new URL(String.format("http://s3.amazonaws.com/Minecraft.Download/versions/%1$s/%1$s.jar", version));
			File dest = new File(launcherdir, String.format("versions%1$s%2$s%1$s%2$s.jar", File.separator, version));
			
			installer.addDownload(new SimpleDownload(url, dest));
		} catch (IOException e) {
			throw new InstallerException(e);
		}
		
		return true;
	}

}
