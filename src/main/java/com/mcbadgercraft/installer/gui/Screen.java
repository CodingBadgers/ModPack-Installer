package com.mcbadgercraft.installer.gui;

/**
 * Represents the main UI screen for the application.
 */
public interface Screen {

    /**
     * Sets up the screen for use
     *
     * @throws Exception to signal if a error has occurred
     */
	public void init() throws Exception;

    /**
     * Show the screen to the user.
     */
	public void show();

    /**
     * Hide the screen from the user.
     */
	public void hide();

    /**
     * Start the installation of the modpack.
     */
	public void startInstallation();
	
}
