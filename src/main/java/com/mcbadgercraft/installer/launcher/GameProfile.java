package com.mcbadgercraft.installer.launcher;

import java.io.File;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.NonNull;
import lombok.ToString;

@AllArgsConstructor
@RequiredArgsConstructor
public class GameProfile {

	@Getter @NonNull private final String name;
	@Getter @Setter private String lastVersionId;
	@Getter @Setter private List<ReleaseType> allowedReleaseTypes;
	@Getter @Setter UUID playerUUID;
	@Getter @Setter private String launcherVisibilityOnGameClose;
	@Getter @Setter private String javaArgs;
	@Getter @Setter private Resolution resolution;
	@Getter @Setter private File gameDir;
	@Getter @Setter private File javaDir;
	
	
	@Override
	public String toString() {
		return name;
	}
	
	@ToString
	@AllArgsConstructor
	public static class Resolution {
		@Getter private final int width;
		@Getter private final int height;
	}
	
	public static enum ReleaseType {
		snapshot,
		release,
		old_beta,
		old_alpha,
	}
}
