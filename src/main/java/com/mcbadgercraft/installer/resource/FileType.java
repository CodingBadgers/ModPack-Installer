package com.mcbadgercraft.installer.resource;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum FileType {

	JAR("jar"),
	LITEMOD("litemod"),
	ZIP("zip");

	@Getter private String extension;
	
}
