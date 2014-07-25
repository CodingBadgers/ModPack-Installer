package com.mcbadgercraft.installer.packs;

import com.mcbadgercraft.installer.gui.Named;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ModPack implements Named {

    @Getter
    private String name;
    @Getter
    private String description;
    @Getter
    private Version latest;
    @Getter
    private List<Version> versions;

    public void addVersion(Version version) {
        this.versions.add(version);
    }

    public String getPath() {
        return this.name + "/" + latest.getMinecraft() + "-" + latest.getPack() + ".json";
    }

    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class Version {
        @Getter
        private String minecraft;
        @Getter
        private String pack;
    }

}
