package com.mcbadgercraft.installer.tasks;

import com.mcbadgercraft.installer.ModPackInstaller;
import com.mcbadgercraft.installer.config.ResourceInfo;
import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;

import java.io.File;
import java.util.List;

public class PostDownloadTask extends Task {

    private final File gameDir;

    public PostDownloadTask(File gameDir) {
        super("postDownload");

        this.gameDir = gameDir;
    }

    @Override
    public boolean perform(Installer installer) throws InstallerException {
        if (!(installer instanceof ModPackInstaller)) {
            return false;
        }

        ModPackInstaller modpack = (ModPackInstaller) installer;

        List<ResourceInfo> resources = modpack.getConfig().getResources();

        for (ResourceInfo info : resources) {
            try {
                File dest = info.getInstallMethod().createFilePath(gameDir, info);
                info.getInstallMethod().postDownload(dest, info);
            } catch (ReflectiveOperationException e) {
                throw new InstallerException(e);
            }
        }

        return true;
    }
}
