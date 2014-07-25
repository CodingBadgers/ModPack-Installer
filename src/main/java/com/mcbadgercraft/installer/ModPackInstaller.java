package com.mcbadgercraft.installer;

import com.mcbadgercraft.installer.config.PackConfig;
import com.mcbadgercraft.installer.tasks.*;
import io.github.thefishlive.installer.Installer;
import io.github.thefishlive.installer.InstallerUtils;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.minecraft.profiles.ProfilesFile;
import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import static io.github.thefishlive.installer.InstallPhase.POST_DOWNLOAD;
import static io.github.thefishlive.installer.InstallPhase.PRE_DOWNLOAD;

public class ModPackInstaller extends Installer {

    @Getter
    private static final File launcherDir = new File(InstallerUtils.getAppData(), ".minecraft");
    @Getter
    private static ProfilesFile profilesFile;
    @Getter
    private final PackConfig config;

    static {
        try {
            profilesFile = ProfilesFile.load(new File(launcherDir, "launcher_profiles.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ModPackInstaller(PackConfig config) throws InstallerException {
        this.config = config;

        File gamedir = new File(InstallerUtils.getAppData(), String.format(".%s", config.getGamedir().getName()));

        addTask(PRE_DOWNLOAD, new CreateDirTask(gamedir));
        addTask(PRE_DOWNLOAD, new WriteVersionDataTask(launcherDir));
        addTask(PRE_DOWNLOAD, new CreateMcDownloadTask(launcherDir));
        addTask(PRE_DOWNLOAD, new CopySettingsTask(launcherDir, gamedir).addDependency("createDir"));
        addTask(PRE_DOWNLOAD, new SetupMinecraftDirectoryTask(gamedir).addDependency("createDir"));
        addTask(PRE_DOWNLOAD, new CreateResourceDownloads(gamedir).addDependency("setupDirectory"));
        addTask(PRE_DOWNLOAD, new CreateLibraryDownloadsTask(launcherDir));
        addTask(PRE_DOWNLOAD, new WriteProfileDataTask(gamedir));

        addTask(POST_DOWNLOAD, new UnpackDownloadsTask());
        addTask(POST_DOWNLOAD, new ValidateDownloadsTask().addDependency("unpackDownloads"));
        addTask(POST_DOWNLOAD, new ValidatePackedDownloadsTask().addDependency("unpackDownloads"));
        addTask(POST_DOWNLOAD, new PostDownloadTask(gamedir));
    }
}
