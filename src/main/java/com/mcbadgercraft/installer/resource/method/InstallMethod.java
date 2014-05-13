package com.mcbadgercraft.installer.resource.method;

import com.mcbadgercraft.installer.config.ResourceInfo;

import java.io.File;

public interface InstallMethod {

    public File createPath(File gamedir, ResourceInfo info);

    public File postDownload(File resource, ResourceInfo info);

}
