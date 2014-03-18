package com.mcbadgercraft.installer.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;

public class ArtifactId {

	private static final Pattern ARTIFACT_PATTERN = Pattern.compile("([\\w\\.-]+):([\\w\\.-]+):([\\w\\.-]+)");
	
	@Getter private String name;
	@Getter private String version;
	@Getter private String group;
	
	public ArtifactId(String id) {
		Matcher matcher = ARTIFACT_PATTERN.matcher(id);
		
		if (!matcher.matches()) {
			throw new IllegalArgumentException(String.format("Artifact id is in the wrong format, (should be '<group>:<name>:<version>' got '%1$s')", id));
		}

		group = matcher.group(1);
		name = matcher.group(2);
		version = matcher.group(3);
	}
	
	public String toString() {
		return String.format("%1$s:%2$s:%3$s", group, name, version);
	}
}
