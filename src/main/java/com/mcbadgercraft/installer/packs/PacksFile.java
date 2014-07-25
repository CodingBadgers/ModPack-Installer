package com.mcbadgercraft.installer.packs;

import com.google.common.collect.Lists;
import com.mcbadgercraft.installer.Bootstrap;
import com.mcbadgercraft.installer.config.PackConfig;
import com.mcbadgercraft.installer.gui.Named;
import com.mcbadgercraft.installer.utils.Utils;
import lombok.Cleanup;
import lombok.Getter;
import lombok.ToString;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

@ToString
public class PacksFile {

    private static final String BASE_URL;

    static {
        Manifest manifest = Utils.getManifest();

        if (manifest == null) {
            Bootstrap.getLog().warn("Could not find manifest, falling back on default data url");
            BASE_URL = "http://codingbadgers.github.io/AdminPack-Data/";
        } else {
            Bootstrap.getLog().debug("Found manifest file, using data url from manifest");
            Attributes attr = manifest.getAttributes("Installer");
            BASE_URL = (String) attr.get(new Attributes.Name("Base-Url"));
        }

        Bootstrap.getLog().info("Base Url: {}", BASE_URL);
    }

    private static URL PACKS_URL = Utils.constantUrl(BASE_URL + "packs.json");
    @Getter
    private static PacksFile instance;
    @Getter
    private List<PackInfo> packs = Lists.newArrayList();

    public static void setup() throws Exception {
        @Cleanup InputStream stream = PACKS_URL.openStream();
        @Cleanup InputStreamReader reader = new InputStreamReader(stream);

        instance = PackConfig.getGson().fromJson(reader, PacksFile.class);

        if (instance == null) {
            instance = new PacksFile();
        }
    }

    public static class PackInfo implements Named {
        @Getter
        private String name;
        @Getter
        private Version latest;
        @Getter
        private String description;

        public URL getDataUrl() {
            return Utils.constantUrl(BASE_URL + name + "/" + latest.toString() + ".json");
        }
    }

    public static class Version {
        @Getter
        private String minecraft;
        @Getter
        private String pack;

        @Override
        public String toString() {
            return String.format("%1$s-%2$s", minecraft, pack);
        }

    }
}
