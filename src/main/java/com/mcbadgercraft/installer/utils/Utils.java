package com.mcbadgercraft.installer.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;

import com.google.common.io.Resources;
import com.mcbadgercraft.installer.config.ArtifactId;
import com.mcbadgercraft.installer.resource.FileType;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.swing.*;

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
}
