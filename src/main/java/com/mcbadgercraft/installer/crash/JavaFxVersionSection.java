package com.mcbadgercraft.installer.crash;

import com.sun.javafx.runtime.VersionInfo;

import io.github.thefishlive.crash.CrashReportSection;
import io.github.thefishlive.format.Format;

@SuppressWarnings("restriction")
public class JavaFxVersionSection implements CrashReportSection {

	@Override
	public String build(Format format) {
		StringBuilder builder = new StringBuilder();
		
		builder.append(format.list()).append(format.bold()).append("Version:").append(format.bold()).append(" ");
		builder.append(VersionInfo.getVersion()).append(format.newLine());
		
		builder.append(format.list()).append(format.bold()).append("Runtime Version:").append(format.bold()).append(" ");
		builder.append(VersionInfo.getRuntimeVersion()).append(format.newLine());
		
		builder.append(format.list()).append(format.bold()).append("Build Timestamp:").append(format.bold()).append(" ");
		builder.append(VersionInfo.getBuildTimestamp()).append(format.newLine());
		
		return builder.toString();
	}

	@Override
	public String getName() {
		return "Java Fx";
	}

}
