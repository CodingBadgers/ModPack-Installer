package com.mcbadgercraft.installer.launcher;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

public class AuthProfile {

	@Getter private List<Property> userProperties;
	@Getter private String username;
	@Getter private String accessToken;
	@Getter private String userid;
	@Getter private String uuid;
	@Getter private String displayName;
	
	@ToString
	public static class Property {
		@Getter private String name;
		@Getter private String value;
	}
	
	public String toString() {
		return displayName;
	}
}
