package com.mcbadgercraft.installer.launcher;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.NonNull;

@AllArgsConstructor
@RequiredArgsConstructor
public class GameProfile {

	@Getter @NonNull private final String name;
	@Getter @Setter private String lastVersionId;
	@Getter @Setter private List<String> allowedReleaseTypes;
	@Getter @Setter private String playerUUID;
	@Getter @Setter private String launcherVisibilityOnGameClose;
	
	@Override
	public String toString() {
		return name;
	}
	
}
