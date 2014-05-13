package com.mcbadgercraft.installer.resource.method;

import com.mcbadgercraft.installer.config.ResourceInfo;
import com.mcbadgercraft.installer.resource.FileType;
import com.mcbadgercraft.installer.utils.Utils;
import io.github.thefishlive.installer.InstallerUtils;

import java.io.File;

public class LibraryInstallMethod implements InstallMethod {

    private static final File launcherdir = new File(InstallerUtils.getAppData(), ".minecraft");

    @Override
    public File createPath(File gamedir, ResourceInfo info) {
        return new File(launcherdir, "libraries" + File.separator + Utils.createPath(info.getArtifactId(), FileType.JAR));
    }

    @Override
    public File postDownload(File resource, ResourceInfo info) {
        return null;
    }

}
