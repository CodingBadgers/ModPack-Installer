package com.mcbadgercraft.installer.packs;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import com.google.common.collect.Lists;
import com.mcbadgercraft.installer.config.PackConfig;

import lombok.Cleanup;
import lombok.Getter;
import lombok.ToString;

@ToString
public class PacksFile {

	private static URL packsUrl = null;
	@Getter private static PacksFile instance;
	
	public static void setup() throws Exception {		
		packsUrl = new URL("http://mcbadgercraft.com/adminpack/packs.json");

		@Cleanup InputStream stream = packsUrl.openStream();
		@Cleanup InputStreamReader reader = new InputStreamReader(stream);
		
		instance = PackConfig.getGson().fromJson(reader, PacksFile.class);
		
		if (instance == null) {
			instance = new PacksFile();
		}
	}
	
	@Getter private List<PackInfo> packs = Lists.newArrayList();
	
	public static class PackInfo {
		@Getter private String name;
		@Getter private Version version;
		@Getter private URL data;
		@Getter private String description;
		
		@Override
		public String toString() {
			return String.format("%1$s (%2$s)", name, version);
		}
	}	
	
	public static class Version {
		@Getter private String minecraft;
		@Getter private String pack;
		
		@Override
		public String toString() {
			return String.format("%1$s-%2$s", minecraft, pack);
		}
	}
}
