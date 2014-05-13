package com.mcbadgercraft.installer.tasks;

import com.google.common.io.Files;
import com.mcbadgercraft.installer.ModPackInstaller;
import com.mcbadgercraft.installer.config.GameDirData.CopyInfo;
import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CopySettingsTask extends Task {

    private File launcherdir;
    private File gamedir;

    public CopySettingsTask(File launcherdir, File gamedir) {
        super("copySettings");
        this.launcherdir = launcherdir;
        this.gamedir = gamedir;
    }

    @Override
    public boolean perform(Installer installer) throws InstallerException {
        if (!(installer instanceof ModPackInstaller)) {
            return false;
        }

        ModPackInstaller modpack = (ModPackInstaller) installer;

        List<CopyInfo> files = modpack.getConfig().getGamedir().getCopy();

        for (CopyInfo copy : files) {
            try {
                File old = new File(launcherdir, copy.getOld());

                if (old.exists()) {
                    File dest = new File(gamedir, copy.getRename() != null && !copy.getRename().equals("false") ? copy.getRename() : copy.getOld());
                    Files.copy(old, dest);
                }

            } catch (IOException ex) {
                throw new InstallerException(ex);
            }
        }
        return true;
    }
}
