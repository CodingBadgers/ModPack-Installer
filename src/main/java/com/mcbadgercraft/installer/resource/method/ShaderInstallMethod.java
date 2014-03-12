package com.mcbadgercraft.installer.resource.method;

import java.io.File;

import com.mcbadgercraft.installer.config.ResourceInfo;

public class ShaderInstallMethod implements InstallMethod {

	@Override
	public File createPath(File gamedir, ResourceInfo info) {
		StringBuilder builder = new StringBuilder();
		builder.append(gamedir.getAbsolutePath()).append(File.separator).append("shaderpacks").append(File.separator).append(String.format("%1$s.%2$s", info.getArtifactId().getName(), info.getFiletype().getExtension()));
		return new File(builder.toString());
	}

}
