package com.mcbadgercraft.installer.utils;

import com.mcbadgercraft.installer.ModPackInstaller;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MinecraftUtils {

    private static final Pattern NATIVES_PATTERN = Pattern.compile("(.+)-natives-(.+)");
    private static final File VERSIONS_DIR = new File(ModPackInstaller.getLauncherDir(), "versions");

    public static boolean isMinecraftRunning() {

        for (File version : VERSIONS_DIR.listFiles(new DirectoryFilter())) {
            if (version.listFiles(new NativesFilter()).length > 0) {
                return true;
            }
        }

        return false;
    }

    private static class DirectoryFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            return pathname.isDirectory();
        }
    }

    private static class NativesFilter implements FileFilter {
        @Override
        public boolean accept(File pathname) {
            if (!pathname.isDirectory()) {
                return false;
            }

            Matcher matcher = NATIVES_PATTERN.matcher(pathname.getName());
            return matcher.matches();
        }
    }
}
