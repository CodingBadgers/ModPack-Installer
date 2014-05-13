package com.mcbadgercraft.installer.utils;

import com.google.common.io.Resources;
import com.mcbadgercraft.installer.Bootstrap;
import com.mcbadgercraft.installer.config.ArtifactId;
import com.mcbadgercraft.installer.resource.FileType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.jar.Manifest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    public static String createPath(ArtifactId artifact, FileType type) {
        StringBuilder dest = new StringBuilder();
        dest.append(artifact.getGroup().replace('.', '/')).append(File.separator);
        dest.append(artifact.getName()).append("/").append(artifact.getVersion()).append(File.separator);
        dest.append(String.format("%1$s-%2$s.%3$s", artifact.getName(), artifact.getVersion(), type.getExtension()));
        return dest.toString();
    }

    public static String readURLContents(URL url) throws IOException {
        return Resources.toString(url, Charset.forName("UTF-8"));
    }

    public static int showError(String... message) {
        StringBuilder builder = new StringBuilder();
        for (String line : message) {
            builder.append(line).append("\n");
        }
        return JOptionPane.showConfirmDialog(null, builder.toString(), "ModPack-Installer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    public static URL constantUrl(String s) {
        try {
            return URI.create(s).toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Manifest getManifest() {
        Class clazz = Bootstrap.class;
        String className = clazz.getSimpleName() + ".class";
        String classPath = clazz.getResource(className).toString();
        if (!classPath.startsWith("jar")) { // Class not from JAR, this should never happen
            return null;
        }

        try {
            String manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + "/META-INF/MANIFEST.MF";
            return new Manifest(new URL(manifestPath).openStream());
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
