package com.mcbadgercraft.installer.tasks;

import com.mcbadgercraft.installer.Bootstrap;
import com.mcbadgercraft.installer.ModPackInstaller;
import com.mcbadgercraft.installer.config.InstallData;
import com.mcbadgercraft.installer.launcher.AuthProfile;
import com.mcbadgercraft.installer.launcher.GameProfile;
import com.mcbadgercraft.installer.launcher.ProfilesFile;

import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.task.Task;

public class WriteProfileDataTask extends Task {

	public WriteProfileDataTask() {
		super("writeProfileData");
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
			game.setLastVersionId(installdata.getTarget());
			
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
