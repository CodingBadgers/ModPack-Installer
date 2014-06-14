package com.mcbadgercraft.installer.tasks;

import com.mcbadgercraft.installer.Bootstrap;
import com.mcbadgercraft.installer.ModPackInstaller;
import com.mcbadgercraft.installer.config.InstallData;
import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;
import io.github.thefishlive.minecraft.ReleaseType;
import io.github.thefishlive.minecraft.profiles.AuthProfile;
import io.github.thefishlive.minecraft.profiles.GameProfile;
import io.github.thefishlive.minecraft.profiles.JavaArgs;
import io.github.thefishlive.minecraft.profiles.VisibilityRule;

import java.io.File;

public class WriteProfileDataTask extends Task {

    private File gamedir;

    public WriteProfileDataTask(File gamedir) {
        super("writeProfileData");

        this.gamedir = gamedir;
    }

    @Override
    public boolean perform(Installer installer) throws InstallerException {
        if (!(installer instanceof ModPackInstaller)) {
            return false;
        }

        ModPackInstaller modpack = (ModPackInstaller) installer;
        InstallData installdata = modpack.getConfig().getInstall();

        try {
            AuthProfile auth = (AuthProfile) Bootstrap.getStartup().getCbxProfile().getSelectedItem();
            GameProfile game = new GameProfile(installdata.getProfileName());
            {
                game.setLastVersionId(installdata.getTarget());
                game.setLauncherVisibility(VisibilityRule.KEEP_LAUNCHER);
                game.addReleaseType(ReleaseType.RELEASE);
                game.setGameDir(gamedir);
                game.setJavaArgs(new JavaArgs().setMaxMemory("1G").addSysProperty("fml.ignoreInvalidMinecraftCertificates", "true").addSysProperty("fml.ignorePatchDiscrepancies", "true"));
            }

            if (auth != null) {
                game.setPlayerUUID(auth.getUuid());
            }

            ModPackInstaller.getProfilesFile().addProfile(game);
            ModPackInstaller.getProfilesFile().setSelectedProfile(game);
            ModPackInstaller.getProfilesFile().write();
        } catch (Exception ex) {
            throw new InstallerException(ex);
        }

        return true;
    }

}
