package com.mcbadgercraft.installer.tasks;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.mcbadgercraft.installer.ModPackInstaller;
import com.mcbadgercraft.installer.config.PackConfig;

import lombok.Cleanup;

import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;

public class WriteVersionDataTask extends Task {

	private File launcherdir;

	public WriteVersionDataTask(File launcherdir) {
		super("writeVersionData");
		this.launcherdir = launcherdir;
	}
	
	@Override
	public boolean perform(Installer installer) throws InstallerException {
		if (!(installer instanceof ModPackInstaller)) {
			return false;
		}
		
		ModPackInstaller modpack = (ModPackInstaller) installer;
		
		try {
			String version = modpack.getConfig().getInstall().getTarget();
			File versionJson = new File(launcherdir, String.format("versions%1$s%2$s%1$s%2$s.json", File.separator, version));
			
			if (!versionJson.getParentFile().exists()) {
				versionJson.getParentFile().mkdirs();
			}
			
			@Cleanup FileWriter writer = new FileWriter(versionJson);
			PackConfig.getGson().toJson(modpack.getConfig().getVersionInfo(), writer);
			return true;
		} catch (IOException e) {
			throw new InstallerException(e);
		}
	}

}
