package com.mcbadgercraft.installer.config;

import java.util.List;

import lombok.Getter;
import lombok.ToString;

@ToString
public class GameDirData {

	@Getter private String name;
	@Getter private List<CopyInfo> copy;

	@ToString
	public static class CopyInfo {
		@Getter private String old;
		@Getter private String rename;
	}
}
