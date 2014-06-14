package com.mcbadgercraft.installer;

public final class VersionConstants {

    public static final String IMPLEMENTATION_VENDOR;
    public static final String IMPLEMENTATION_TITLE;
    public static final String IMPLEMENTATION_VERSION;

    public static final String SPECIFICATION_VENDOR;
    public static final String SPECIFICATION_TITLE;
    public static final String SPECIFICATION_VERSION;

    static {
        IMPLEMENTATION_VENDOR = Bootstrap.class.getPackage().getImplementationVendor();
        IMPLEMENTATION_TITLE = Bootstrap.class.getPackage().getImplementationTitle();
        IMPLEMENTATION_VERSION = Bootstrap.class.getPackage().getImplementationVersion();

        SPECIFICATION_VENDOR = Bootstrap.class.getPackage().getSpecificationVendor();
        SPECIFICATION_TITLE = Bootstrap.class.getPackage().getSpecificationTitle();
        SPECIFICATION_VERSION = Bootstrap.class.getPackage().getSpecificationVersion();
    }
}
