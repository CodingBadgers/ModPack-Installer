package com.mcbadgercraft.installer.config;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
public class VersionData {

    @Getter
    private String id;
    @Getter
    private String time;
    @Getter
    private String releaseTime;
    @Getter
    private String type;
    @Getter
    private String minecraftArguments;
    @Getter
    private String mainClass;
    @Getter
    private int minimumLauncherVersion;
    @Getter
    private List<LibraryInfo> libraries;

    @ToString
    public static class LibraryInfo {
        @Getter
        private ArtifactId name;
        @Getter
        private NativesInfo natives;
        @Getter
        private ExtractInfo extract;
        @Getter
        private List<RulesInfo> rules;
        @Getter
        private Boolean serverreq;
        @Getter
        private Boolean clientreq;
        @Getter
        private String url;
        @Getter
        private List<String> checksums;
        @Getter
        private String comment;
    }

    @ToString
    public static class NativesInfo {
        @Getter
        private String linux;
        @Getter
        private String windows;
        @Getter
        private String osx;
    }

    @ToString
    public static class ExtractInfo {
        @Getter
        private List<String> exclude;
    }

    @ToString
    public static class RulesInfo {
        @Getter
        private String action;
        @Getter
        private OsInfo os;
    }

    @ToString
    public static class OsInfo {
        @Getter
        private String name;
        @Getter
        private String version;
    }
}
