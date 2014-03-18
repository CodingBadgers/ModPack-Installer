package com.mcbadgercraft.installer;

import java.io.File;

import com.mcbadgercraft.installer.config.ArtifactId;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

	public static String createPath(ArtifactId artifact, String ext) {
		StringBuilder dest = new StringBuilder();
		dest.append(artifact.getGroup().replace('.', '/')).append(File.separator);
		dest.append(artifact.getName()).append("/").append(artifact.getVersion()).append(File.separator);
		dest.append(String.format("%1$s-%2$s.%3$s", artifact.getName(), artifact.getVersion(), ext));
		return dest.toString();
	}
}
