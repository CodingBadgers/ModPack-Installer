package com.mcbadgercraft.installer.launcher;

import io.github.thefishlive.installer.exception.InstallerException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Maps;
import com.google.gson.JsonIOException;
import com.mcbadgercraft.installer.ModPackInstaller;
import com.mcbadgercraft.installer.config.PackConfig;

import lombok.Cleanup;
import lombok.Getter;
import lombok.ToString;

@ToString
public class ProfilesFile {

	private static File launcherProfiles = new File(ModPackInstaller.getLauncherDir(), "launcher_profiles.json");
	@Getter private static ProfilesFile instance;
	
	public static void setup() throws Exception{		
		if (!launcherProfiles.exists()) {
			throw InstallerException.PROFILES_FILE_DOES_NOT_EXIST;
		}
		
		instance = PackConfig.getGson().fromJson(new FileReader(launcherProfiles), ProfilesFile.class);
		
		if (instance == null) {
			instance = new ProfilesFile();
		}
	}
	
	@Getter private Map<String, GameProfile> profiles = Maps.newHashMap();
	@Getter private Map<String, AuthProfile> authenticationDatabase = Maps.newHashMap();
	@Getter private String selectedProfile;
	@Getter private String clientToken;
	
	public void addProfile(GameProfile profile) throws JsonIOException, IOException {
		profiles.put(profile.getName(), profile);
		write();
	}

	public void addProfile(AuthProfile profile) throws JsonIOException, IOException {
		authenticationDatabase.put(profile.getUuid(), profile);
		write();
	}

	private void write() throws JsonIOException, IOException {
		@Cleanup FileWriter writer = new FileWriter(launcherProfiles);
		PackConfig.getGson().toJson(this, writer);
		writer.flush();
	}
}
