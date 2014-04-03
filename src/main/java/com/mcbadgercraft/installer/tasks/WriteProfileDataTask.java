package com.mcbadgercraft.installer.tasks;

import java.io.File;

import com.mcbadgercraft.installer.Bootstrap;
import com.mcbadgercraft.installer.ModPackInstaller;
import com.mcbadgercraft.installer.config.InstallData;
import com.mcbadgercraft.installer.mojang.AuthProfile;
import com.mcbadgercraft.installer.mojang.GameProfile;
import com.mcbadgercraft.installer.mojang.ProfilesFile;

import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;

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
				game.setLauncherVisibilityOnGameClose("keep the launcher open");
				game.setGameDir(gamedir);
				game.setJavaArgs("-Xmx1G -Dfml.ignoreInvalidMinecraftCertificates=true -Dfml.ignorePatchDiscrepancies=true");
			}
			
			if (auth != null) {
				game.setPlayerUUID(auth.getUuid());
			}
			
			ProfilesFile.getInstance().addProfile(game);
		} catch (Exception ex) {
			throw new InstallerException(ex);
		}
		
		return true;
	}

}
