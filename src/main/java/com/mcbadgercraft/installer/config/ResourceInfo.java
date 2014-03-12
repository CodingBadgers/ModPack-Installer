package com.mcbadgercraft.installer.config;

import java.net.URL;

import com.mcbadgercraft.installer.resource.FileType;
import com.mcbadgercraft.installer.resource.method.InstallMethodType;

import lombok.Getter;
import lombok.ToString;

@ToString
public class ResourceInfo {

	@Getter private ArtifactId artifactId;
	@Getter private InstallMethodType installMethod;
	@Getter private FileType filetype;
	@Getter private URL url;
	
}
