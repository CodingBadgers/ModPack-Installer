package com.mcbadgercraft.installer;

import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.InstallerUtils;
import io.github.thefishlive.installer.exception.InstallerException;
import static io.github.thefishlive.installer.InstallPhase.*;

import java.io.File;
import java.net.URL;

import com.mcbadgercraft.installer.config.PackConfig;
import com.mcbadgercraft.installer.tasks.CopySettingsTask;
import com.mcbadgercraft.installer.tasks.CreateDirTask;
import com.mcbadgercraft.installer.tasks.CreateLibraryDownloadsTask;
import com.mcbadgercraft.installer.tasks.CreateMcDownloadTask;
import com.mcbadgercraft.installer.tasks.CreateResourceDownloads;
import com.mcbadgercraft.installer.tasks.UnpackDownloadsTask;
import com.mcbadgercraft.installer.tasks.WriteProfileDataTask;
import com.mcbadgercraft.installer.tasks.WriteVersionDataTask;

import lombok.Getter;

public class ModPackInstaller extends Installer {

	@Getter private static final File launcherDir = new File(InstallerUtils.getAppData(), ".minecraft");
	@Getter private final PackConfig config;
	
	public ModPackInstaller(URL datalink) throws InstallerException {
		config = PackConfig.loadConfig(datalink);
		
		File gamedir = new File(InstallerUtils.getAppData(), String.format(".%1$s", config.getGamedir().getName()));

		addTask(PRE_DOWNLOAD, new CreateDirTask(gamedir));
		addTask(PRE_DOWNLOAD, new WriteVersionDataTask(launcherDir));
		addTask(PRE_DOWNLOAD, new CreateMcDownloadTask(launcherDir));
		addTask(PRE_DOWNLOAD, new CopySettingsTask(launcherDir, gamedir).addDependency("createDir"));
		addTask(PRE_DOWNLOAD, new CreateResourceDownloads(gamedir).addDependency("createDir"));
		addTask(PRE_DOWNLOAD, new CreateLibraryDownloadsTask(launcherDir));
		addTask(PRE_DOWNLOAD, new WriteProfileDataTask());
		
		addTask(POST_DOWNLOAD, new UnpackDownloadsTask());
	}

}
