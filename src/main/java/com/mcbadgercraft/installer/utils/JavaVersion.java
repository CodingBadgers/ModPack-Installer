package com.mcbadgercraft.installer.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum JavaVersion {

	LEGACY,
	JAVA_6(50.0f, false),
	JAVA_7(51.0f, false),
	JAVA_8(52.0f, true),
	FUTURE,
	;
	
	@Getter
	private float classVersion;
	
	@Getter
	private boolean javaFxEnabled;
	
}
