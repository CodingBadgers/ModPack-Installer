package com.mcbadgercraft.installer.resource.method;

import io.github.thefishlive.installer.InstallerUtils;

import java.io.File;

import com.mcbadgercraft.installer.config.ResourceInfo;

public class LibraryInstallMethod implements InstallMethod {

	private static final File launcherdir = new File(InstallerUtils.getAppData(), ".minecraft");
	
	@Override
	public File createPath(File gamedir, ResourceInfo info) {
		File dest = new File(launcherdir, "libraries");
		String[] split = info.getArtifactId().getGroup().split(".");
		for (String part : split) {
			dest = new File(dest, part);
		}
		dest = new File(new File(dest, info.getArtifactId().getName()), info.getArtifactId().getVersion());
		dest.mkdirs();
		return new File(dest, String.format("%1$s-%2$s.%3$s", info.getArtifactId().getName(), info.getArtifactId().getVersion(), info.getFiletype().getExtension()));
	}

}
