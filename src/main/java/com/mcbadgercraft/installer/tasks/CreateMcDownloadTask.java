package com.mcbadgercraft.installer.tasks;

import com.google.common.io.Files;
import com.mcbadgercraft.installer.ModPackInstaller;
import com.mcbadgercraft.installer.download.MinecraftDownload;
import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.download.SimpleDownload;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class CreateMcDownloadTask extends Task {

    private File launcherdir;

    public CreateMcDownloadTask(File launcherdir) {
        super("createMcDownload");

        this.launcherdir = launcherdir;
    }

    @Override
    public boolean perform(Installer installer) throws InstallerException {
        if (!(installer instanceof ModPackInstaller)) {
            return false;
        }

        ModPackInstaller modpack = (ModPackInstaller) installer;

        try {
            String version = modpack.getConfig().getInstall().getVersion().getMinecraft();
            File versionJar = new File(launcherdir, String.format("versions%1$s%2$s%1$s%2$s.jar", File.separator, version));
            File dest = new File(launcherdir, String.format("versions%1$s%2$s%1$s%2$s.jar", File.separator, modpack.getConfig().getInstall().getTarget()));

            if (versionJar.exists()) {
                Files.copy(versionJar, dest);
                return true;
            }

            URL url = new URL(String.format("http://s3.amazonaws.com/Minecraft.Download/versions/%1$s/%1$s.jar", version));
            installer.addDownload(new MinecraftDownload(url, dest));
        } catch (IOException e) {
            throw new InstallerException(e);
        }

        return true;
    }

}
