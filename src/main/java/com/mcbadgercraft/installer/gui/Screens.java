package com.mcbadgercraft.installer.gui;

import java.lang.reflect.Constructor;

import com.mcbadgercraft.installer.gui.swing.SwingScreen;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Screens {

	JAVA_SWING(SwingScreen.class),
	;
	
	private final Class<? extends Screen> clazz;
	
	private Constructor<? extends Screen> ctor;
	
	public Constructor<? extends Screen> getConstructor() throws ReflectiveOperationException, SecurityException {
		if (ctor == null) {
			ctor = clazz.getConstructor();
		}
		
		return ctor;
	}
	
	public Screen createScreen() throws Exception {
		Screen screen = getConstructor().newInstance();
		screen.init();
		return screen;
	}
}
