package com.mcbadgercraft.installer;

import com.mcbadgercraft.installer.gui.StartupFrame;
import com.mcbadgercraft.installer.log.LogPanel;
import com.mcbadgercraft.installer.packs.PacksFile;
import com.mcbadgercraft.installer.utils.MinecraftUtils;
import com.mcbadgercraft.installer.utils.Utils;
import io.github.thefishlive.bootstrap.Bootstrapper;
import io.github.thefishlive.bootstrap.Launcher;
import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.log.InstallerLogger;
import io.github.thefishlive.installer.options.InstallerOptions;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import lombok.Getter;

import javax.swing.*;
import java.util.Arrays;

public class Bootstrap implements Launcher {

    @Getter
    private static final InstallerLogger log = InstallerLogger.getLog();
    @Getter
    private static final LogPanel logPanel = new LogPanel();
    @Getter
    private static final String version;
    @Getter
    private static StartupFrame startup = null;
    private static OptionParser parser = null;
    private static OptionSpec<Boolean> debugOption = null;
    private static OptionSpec<Boolean> threadedOption = null;
    private static OptionSpec<?> minecraftOption = null;
    private static OptionSpec<?> helpOption = null;

    static {
        parser = new OptionParser();
        debugOption = parser.acceptsAll(Arrays.asList("debug", "d"), "Sets the program to debug mode").withRequiredArg().ofType(Boolean.class).defaultsTo(false);
        threadedOption = parser.acceptsAll(Arrays.asList("threaded", "t"), "Sets the installer to download files on multiple threads").withRequiredArg().ofType(Boolean.class).defaultsTo(true);
        minecraftOption = parser.acceptsAll(Arrays.asList("minecraft", "mc"), "Tells the installer to ignore currently running minecraft instances");
        helpOption = parser.acceptsAll(Arrays.asList("help", "?"), "Shows the program help").forHelp();

        String pckgVersion = Bootstrap.class.getPackage().getImplementationVersion();

        if (pckgVersion == null) {
            pckgVersion = "[dev-SNAPSHOT]";
        }

        version = pckgVersion;
    }

    public static void main(String[] args) throws Exception {
        Bootstrapper.launch(Bootstrap.class, args);
    }

    @Override
    public void launch(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            logPanel.setVisible(true);
            log.info("Starting AdminPack Installer v{}", version);
            log.info("OS: {} ({}) [{}]", System.getProperty("os.name"), System.getProperty("os.version"), System.getProperty("os.arch"));
            log.info("Java Version: {} ({})", System.getProperty("java.version"), System.getProperty("java.class.version"));

            OptionSet options = parser.parse(args);

            if (options.has(helpOption)) {
                parser.printHelpOn(System.out);
                System.exit(0);
                return;
            }

            if (!options.has(minecraftOption) && MinecraftUtils.isMinecraftRunning()) {
                log.warn("It looks like you have minecraft running.");
                log.warn("To run this installer you must close minecraft and its launcher otherwise the installation might mess up.");

                int result = Utils.showError("It looks like you have minecraft running.",
                        "",
                        "To run this installer you must close minecraft",
                        "and its launcher otherwise the installation will",
                        "not work as intended.");

                if (result == JOptionPane.CANCEL_OPTION) {
                    log.info("Stopping installer as minecraft is already running");
                    System.exit(0);
                    return;
                }
            }

            InstallerOptions.setDebug(debugOption.value(options));
            InstallerOptions.setThreadedDownloads(threadedOption.value(options));

            if (InstallerOptions.isDebug()) {
                log.info("Installer launched in debug mode...");
            }
            log.info("Threaded Downloads: {}", InstallerOptions.isThreadedDownloads());

            PacksFile.setup();

            startup = new StartupFrame();
            startup.setVisible(true);
            log.info("Startup complete");
        } catch (InstallerException ex) {
            log.error("A error has occurred causing the installer to crash");
            log.error("Exception: " + ex.getMessage());
            log.error("Stacktrace: ", ex);
        } catch (Throwable throwable) {
            log.fatal("A unexpected error has occurred causing the installer to crash");
            log.fatal("Exception: " + throwable.getMessage());
            log.fatal("Stacktrace: ", throwable);
        }
    }

}
