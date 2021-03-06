package com.mcbadgercraft.installer.resource.method;

import com.mcbadgercraft.installer.config.ResourceInfo;

import java.io.File;

public class ModInstallMethod implements InstallMethod {

    @Override
    public File createPath(File gamedir, ResourceInfo info) {
        return new File(gamedir, String.format("mods%1$s%2$s.%3$s", File.separator, info.getArtifactId().getName(), info.getFiletype().getExtension()));
    }

    @Override
    public File postDownload(File resource, ResourceInfo info) {
        return null;
    }
}
