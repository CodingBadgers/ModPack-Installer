package com.mcbadgercraft.installer.resource.method;

import java.io.File;

import com.mcbadgercraft.installer.config.ResourceInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum InstallMethodType {

	MODS(ModInstallMethod.class),
	LIBRARY(LibraryInstallMethod.class),
	RESOURCE(ResourceInstallMethod.class),
	SHADER(ShaderInstallMethod.class);

	private Class<? extends InstallMethod> clazz;
	
	public File createFilePath(File gamedir, ResourceInfo info) throws ReflectiveOperationException {
		if (clazz == null) {
			throw new RuntimeException("Install method has not been setup correctly");
		}
		
		InstallMethod method = clazz.newInstance();
		return method.createPath(gamedir, info);
	}
	
}
