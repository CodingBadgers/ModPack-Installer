package com.mcbadgercraft.installer.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.mcbadgercraft.installer.config.json.ArtifactIdAdapter;
import com.mcbadgercraft.installer.config.json.FileAdapter;
import com.mcbadgercraft.installer.config.json.UUIDAdapter;
import io.github.thefishlive.installer.exception.InstallerException;
import lombok.Cleanup;
import lombok.Getter;
import lombok.ToString;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@ToString
public class PackConfig {

    @Getter
    private static final int currentVersion = 6;
    @Getter
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setVersion(currentVersion)
            .registerTypeAdapter(ArtifactId.class, new ArtifactIdAdapter())
            .registerTypeAdapter(UUID.class, new UUIDAdapter())
            .registerTypeAdapter(File.class, new FileAdapter())
            .create();

    @Getter
    private int version;
    @Getter
    private VersionData versionInfo;
    @Getter
    private InstallData install;
    @Getter
    private ResourcePackInfo resourcespack;
    @Getter
    private List<ResourceInfo> resources;
    @Getter
    private GameDirData gamedir;

    public static PackConfig loadConfig(URL datalink) throws InstallerException {
        try {
            @Cleanup InputStream stream = datalink.openStream();
            @Cleanup InputStreamReader reader = new InputStreamReader(stream);
            PackConfig config = gson.fromJson(reader, PackConfig.class);

            if (config.getVersion() != currentVersion) {
                if (config.getVersion() > currentVersion) {
                    throw InstallerException.INSTALLER_OUT_OF_DATE;
                } else if (config.getVersion() < currentVersion) {
                    throw InstallerException.CONFIG_OUT_OF_DATE;
                }
            }

            return config;
        } catch (IOException e) {
            throw new InstallerException(e);
        }
    }

}
