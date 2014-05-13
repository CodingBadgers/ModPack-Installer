package com.mcbadgercraft.installer.resource.method;

import com.mcbadgercraft.installer.config.ResourceInfo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.io.File;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum InstallMethodType {

    MODS(ModInstallMethod.class),
    LIBRARY(LibraryInstallMethod.class),
    RESOURCE(ResourceInstallMethod.class),
    SHADER(ShaderInstallMethod.class),
    SAVE(SaveInstallMethod.class);

    private final Class<? extends InstallMethod> clazz;
    private InstallMethod instance;

    public File createFilePath(File gamedir, ResourceInfo info) throws ReflectiveOperationException {
        if (clazz == null) {
            throw new RuntimeException("Install method has not been setup correctly");
        }

        InstallMethod method = getInstance();
        return method.createPath(gamedir, info);
    }

    public File postDownload(File download, ResourceInfo info) throws ReflectiveOperationException {
        if (clazz == null) {
            throw new RuntimeException("Install method has not been setup correctly");
        }

        InstallMethod method = getInstance();
        return method.postDownload(download, info);
    }

    private InstallMethod getInstance() throws ReflectiveOperationException {
        if (instance == null) {
            instance = clazz.newInstance();
        }

        return instance;
    }

}
