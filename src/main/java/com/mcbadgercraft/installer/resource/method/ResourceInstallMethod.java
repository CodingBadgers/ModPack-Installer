package com.mcbadgercraft.installer.resource.method;

import com.mcbadgercraft.installer.config.ResourceInfo;

import java.io.File;

public class ResourceInstallMethod implements InstallMethod {

    @Override
    public File createPath(File gamedir, ResourceInfo info) {
        StringBuilder builder = new StringBuilder();
        builder.append(gamedir.getAbsolutePath()).append(File.separator).append("resourcepacks").append(File.separator).append(String.format("%1$s.%2$s", info.getArtifactId().getName(), info.getFiletype().getExtension()));
        return new File(builder.toString());
    }

    @Override
    public File postDownload(File resource, ResourceInfo info) {
        return null;
    }
}
