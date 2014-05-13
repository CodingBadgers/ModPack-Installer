package com.mcbadgercraft.installer.resource.method;

import java.io.File;

import com.mcbadgercraft.installer.config.ResourceInfo;

public interface InstallMethod {

	public File createPath(File gamedir, ResourceInfo info);

    public File postDownload(File resource, ResourceInfo info);

}
