package com.mcbadgercraft.installer.tasks;

import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;

import java.io.File;

public class SetupMinecraftDirectoryTask extends Task {

    private static final String[] DIRS = new String[]{"mods", "saves", "config", "resourcepacks", "shaderpacks", "logs", "liteconfig"};

    private final File gameDir;

    public SetupMinecraftDirectoryTask(File gameDir) {
        super("setupDirectory");

        this.gameDir = gameDir;
    }

    @Override
    public boolean perform(Installer installer) throws InstallerException {
        for (String dir : DIRS) {
            File file = new File(gameDir, dir);

            if (file.exists() && !file.isDirectory()) {
                file.delete();
            }

            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return true;
    }

}
