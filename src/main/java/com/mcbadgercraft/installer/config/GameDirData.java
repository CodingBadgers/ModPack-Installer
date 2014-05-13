package com.mcbadgercraft.installer.config;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
public class GameDirData {

    @Getter
    private String name;
    @Getter
    private List<CopyInfo> copy;

    @ToString
    public static class CopyInfo {
        @Getter
        private String old;
        @Getter
        private String rename;
    }
}
