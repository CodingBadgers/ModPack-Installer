package com.mcbadgercraft.installer.gui.swing;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mcbadgercraft.installer.Bootstrap;
import com.mcbadgercraft.installer.ModPackInstaller;
import com.mcbadgercraft.installer.gui.Screen;
import com.mcbadgercraft.installer.monitor.ProgressMonitor;
import com.mcbadgercraft.installer.packs.ModPack;
import com.mcbadgercraft.installer.packs.PackRepositoryHandler;

import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.log.InstallerLogger;
import io.github.thefishlive.minecraft.profiles.AuthProfile;
import lombok.Cleanup;
import lombok.Getter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SuppressWarnings("serial")
public class SwingScreen extends JFrame implements Screen {

    private static final Splitter NEW_LINE = Splitter.on('\n').omitEmptyStrings().trimResults();
    private final PackRepositoryHandler repoHandler;
    private JPanel contentPane;
    private List<JLabel> description = Lists.newArrayList();
    @Getter
    private JComboBox<ModPack> cbxModPack;

    /**
     * Create the frame.
     */
    public SwingScreen() throws IOException {
        this.repoHandler = Bootstrap.getInstance().getPackRepositoryHandler();
    }
    
    public void init() throws IOException {
        setTitle("Mod Pack Installer");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(100, 100, 316, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnInstall = new JButton("Install");
        btnInstall.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        startInstallation();
                    }

                }, "installer");
                setVisible(false);
                thread.start();
            }
        });
        btnInstall.setBounds(201, 228, 89, 23);
        contentPane.add(btnInstall);

        JLabel logo = new JLabel("");
        @Cleanup InputStream image = getClass().getResourceAsStream("/big_logo.png");
        logo.setIcon(new ImageIcon(ImageIO.read(image)));
        logo.setBounds(0, 0, 301, 87);
        contentPane.add(logo);

        cbxModPack = new JComboBox<>();
        cbxModPack.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateDescription();
            }
        });
        cbxModPack.setBounds(66, 99, 224, 20);
        cbxModPack.setRenderer(new ComboBoxRenderer());
        contentPane.add(cbxModPack);

        for (ModPack profile : repoHandler.getPacks()) {
            cbxModPack.addItem(profile);
        }

        JLabel lblModPack = new JLabel("Mod Pack");
        lblModPack.setBounds(10, 102, 46, 14);
        contentPane.add(lblModPack);
    }

    protected void updateDescription() {
        String desc = ((ModPack) cbxModPack.getSelectedItem()).getDescription();

        for (JLabel label : description) {
            contentPane.remove(label);
        }

        description.clear();

        int i = 0;
        for (String line : NEW_LINE.split(desc)) {
            JLabel label = new JLabel(line);
            label.setBounds(10, 127 + (i++ * 15), 280, 14);
            contentPane.add(label);
            description.add(label);
        }

        this.update(this.getGraphics());
    }

    public void startInstallation() {
        try {
            ModPack modpack = (ModPack) cbxModPack.getSelectedItem();

            if (modpack == null) {
                Bootstrap.getLog().user("No modpack selected, check your internet connection");
                return;
            }

            ModPackInstaller installer = new ModPackInstaller(this.repoHandler.getFullInfo(modpack));
            installer.getBus().register(new ProgressMonitor());

            if (!installer.perform()) {
                throw new InstallerException("Error executing install");
            }

            Bootstrap.getLog().user("Installation complete");
            System.exit(0);
        } catch (InstallerException e) {
            InstallerLogger log = Bootstrap.getLog();
            log.user("A error has occurred during the running of the installer.\n" + e.getMessage() + "\nCheck log for more details");
            log.fatal("A error has occurred trying to install this modpack to your computer");
            log.fatal("Message: {}", e.getMessage());
            log.fatal("Stacktrace: ", e);
        } catch (Throwable e) {
            InstallerLogger log = Bootstrap.getLog();
            log.user("A unexpected error has occurred during the running of the installer.\n" + e.getMessage() + "\nCheck log for more details");
            log.fatal("A unexpected error has occurred trying to install this modpack to your computer");
            log.fatal("Message: {}", e.getMessage());
            log.fatal("Stacktrace: ", e);
        }
    }

}
