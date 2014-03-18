package com.mcbadgercraft.installer.tasks;

import java.io.File;
import java.net.URL;
import java.util.List;

import com.mcbadgercraft.installer.Bootstrap;
import com.mcbadgercraft.installer.ModPackInstaller;
import com.mcbadgercraft.installer.config.ResourceInfo;

import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.download.SimpleDownload;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;

public class CreateResourceDownloads extends Task {

	private File gamedir;

	public CreateResourceDownloads(File gamedir) {
		super("createResourceDownloads");
		
		this.gamedir = gamedir;
	}

	@Override
	public boolean perform(Installer installer) throws InstallerException {
		if (!(installer instanceof ModPackInstaller)) {
			return false;
		}
		
		ModPackInstaller modpack = (ModPackInstaller) installer;
		List<ResourceInfo> resources = modpack.getConfig().getResources();
		
		for (ResourceInfo info : resources) {
			Bootstrap.getLog().trace("Creating download for " + info.getArtifactId());
			try {
				URL url = info.getUrl();
				File dest = info.getInstallMethod().createFilePath(gamedir, info);
				
				if (!dest.getParentFile().exists()) {
					dest.getParentFile().mkdirs();
				}
				
				installer.addDownload(new SimpleDownload(url, dest));
			} catch (ReflectiveOperationException e) {
				throw new InstallerException(e);
			}
		}
		
		return true;
	}

}
