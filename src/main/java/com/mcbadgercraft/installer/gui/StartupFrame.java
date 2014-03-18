package com.mcbadgercraft.installer.gui;

import io.github.thefishlive.installer.exception.InstallerException;
import io.github.thefishlive.installer.log.InstallerLogger;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mcbadgercraft.installer.Bootstrap;
import com.mcbadgercraft.installer.ModPackInstaller;
import com.mcbadgercraft.installer.launcher.AuthProfile;
import com.mcbadgercraft.installer.launcher.ProfilesFile;
import com.mcbadgercraft.installer.monitor.ProgressMonitor;
import com.mcbadgercraft.installer.packs.PacksFile;
import com.mcbadgercraft.installer.packs.PacksFile.PackInfo;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import lombok.Cleanup;
import lombok.Getter;

@SuppressWarnings("serial")
public class StartupFrame extends JFrame {

	private static final Splitter NEW_LINE = Splitter.on('\n').omitEmptyStrings().trimResults();
	private JPanel contentPane;
	private List<JLabel> description = Lists.newArrayList();
	@Getter private JComboBox<PackInfo> cbxModPack;
	@Getter private JComboBox<AuthProfile> cbxProfile;

	/**
	 * Create the frame.
	 */
	public StartupFrame() throws IOException {
		setTitle("Mod Pack Installer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		cbxProfile = new JComboBox<AuthProfile>();
		cbxProfile.setBounds(10, 229, 181, 20);
		contentPane.add(cbxProfile);
		
		for (AuthProfile profile : ProfilesFile.getInstance().getAuthenticationDatabase().values()) {
			cbxProfile.addItem(profile);
		}
		
		JLabel logo = new JLabel("");
		@Cleanup InputStream image = getClass().getResourceAsStream("/big_logo.png");
		logo.setIcon(new ImageIcon(ImageIO.read(image)));
		logo.setBounds(0, 0, 301, 87);
		contentPane.add(logo);
		
		cbxModPack = new JComboBox<PackInfo>();
		cbxModPack.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				updateDescription();
			}
		});
		cbxModPack.setBounds(66, 99, 224, 20);
		contentPane.add(cbxModPack);

		for (PackInfo profile : PacksFile.getInstance().getPacks()) {
			cbxModPack.addItem(profile);
		}
		
		JLabel lblModPack = new JLabel("Mod Pack");
		lblModPack.setBounds(10, 102, 46, 14);
		contentPane.add(lblModPack);
	}

	protected void updateDescription() {
		String desc = ((PackInfo)cbxModPack.getSelectedItem()).getDescription();
		
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

	private void startInstallation() {
		try {
			PackInfo modpack = (PackInfo)cbxModPack.getSelectedItem();
			
			if (modpack == null) {
				Bootstrap.getLog().user("No modpack selected, check your internet connection");
				return;
			}
			
			ModPackInstaller installer = new ModPackInstaller(modpack.getData());
			installer.getBus().register(new ProgressMonitor());
			
			if (!installer.perform()) {
				throw new InstallerException("Error executing install");
			}
			
			Bootstrap.getLog().user("Installation complete");
			System.exit(0);
		} catch (InstallerException e) {
			InstallerLogger log = Bootstrap.getLog();
			log.user("A unexpected error has occurred, check log for more details");
			log.fatal("A error has occurred trying to install this modpack to your computer");
			log.fatal("Message: " + e.getMessage());
			log.fatal("Stacktrace: ", e);
		}
	}
}
