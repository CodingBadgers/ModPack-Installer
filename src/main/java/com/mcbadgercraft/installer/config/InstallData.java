package com.mcbadgercraft.installer.config;

import java.net.URL;

import lombok.Getter;
import lombok.ToString;

@ToString
public class InstallData {

	@Getter private boolean headless;
	@Getter private String title;
	@Getter private String welcome;
	@Getter private String target;
	@Getter private String profileName;
	@Getter private URL mirrorlist;
	@Getter private String logo;
	@Getter private VersionInfo version;

	@ToString
	public static class VersionInfo {
		@Getter private String minecraft;
		@Getter private String forge;
		@Getter private String pack;
	}
}
