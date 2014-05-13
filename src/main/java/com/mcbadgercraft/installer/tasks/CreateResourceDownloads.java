package com.mcbadgercraft.installer.tasks;

import com.mcbadgercraft.installer.Bootstrap;
import com.mcbadgercraft.installer.ModPackInstaller;
import com.mcbadgercraft.installer.config.ResourceInfo;
import com.mcbadgercraft.installer.download.CompressedDownload;
import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.download.Download;
import io.github.thefishlive.installer.download.SimpleDownload;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class CreateResourceDownloads extends Task {

    private File gamedir;

    public CreateResourceDownloads(File gamedir) {
        super("createResourceDownloads");

        this.gamedir = gamedir;
    }

    @SuppressWarnings("resource")
    @Override
    public boolean perform(Installer installer) throws InstallerException {
        if (!(installer instanceof ModPackInstaller)) {
            return false;
        }

        ModPackInstaller modpack = (ModPackInstaller) installer;
        List<ResourceInfo> resources = modpack.getConfig().getResources();

        for (ResourceInfo info : resources) {
            Bootstrap.getLog().trace("Creating download for " + info.getArtifactId());
            try {
                URL url = info.getUrl();
                File dest = info.getInstallMethod().createFilePath(gamedir, info);

                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }

                Download download = new SimpleDownload(url, dest);

                // If the file has been uploaded as a compressed file, download it as such
                if (info.isCompressed()) {
                    download = new CompressedDownload(url, dest);
                }

                installer.addDownload(download);
            } catch (IOException | ReflectiveOperationException e) {
                throw new InstallerException(e);
            }
        }

        return true;
    }

}
