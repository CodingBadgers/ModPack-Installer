package com.mcbadgercraft.installer.tasks;

import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.InstallerUtils;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;

import java.io.File;
import java.io.IOException;

public class CreateDirTask extends Task {

    private File dir;

    public CreateDirTask(File dir) {
        super("createDir");
        this.dir = dir;
    }

    @Override
    public boolean perform(Installer installer) throws InstallerException {
        if (dir.exists()) {
            try {
                InstallerUtils.delete(dir);
            } catch (IOException e) {
                throw new InstallerException(e);
            }
        }

        return dir.mkdirs();
    }

}
