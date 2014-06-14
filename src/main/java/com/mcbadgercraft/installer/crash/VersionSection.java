package com.mcbadgercraft.installer.crash;

import com.mcbadgercraft.installer.VersionConstants;
import io.github.thefishlive.crash.CrashReportSection;
import io.github.thefishlive.format.Format;

public class VersionSection implements CrashReportSection {

    @Override
    public String build(Format format) {
        StringBuilder builder = new StringBuilder();
        builder.append(format.header(4)).append("Implementation").append(format.newLine());
        builder.append(format.list()).append(format.bold()).append("Vendor").append(format.bold()).append(" ");
        builder.append(VersionConstants.IMPLEMENTATION_VENDOR).append(format.newLine());
        builder.append(format.list()).append(format.bold()).append("Title").append(format.bold()).append(" ");
        builder.append(VersionConstants.IMPLEMENTATION_TITLE).append(format.newLine());
        builder.append(format.list()).append(format.bold()).append("Version").append(format.bold()).append(" ");
        builder.append(VersionConstants.IMPLEMENTATION_VERSION).append(format.paragraphBreak());

        builder.append(format.header(4)).append("Specification").append(format.newLine());
        builder.append(format.list()).append(format.bold()).append("Vendor").append(format.bold()).append(" ");
        builder.append(VersionConstants.SPECIFICATION_VENDOR).append(format.newLine());
        builder.append(format.list()).append(format.bold()).append("Title").append(format.bold()).append(" ");
        builder.append(VersionConstants.SPECIFICATION_TITLE).append(format.newLine());
        builder.append(format.list()).append(format.bold()).append("Version").append(format.bold()).append(" ");
        builder.append(VersionConstants.SPECIFICATION_VERSION).append(format.paragraphBreak());

        return builder.toString();
    }

    @Override
    public String getName() {
        return "Version";
    }

}
