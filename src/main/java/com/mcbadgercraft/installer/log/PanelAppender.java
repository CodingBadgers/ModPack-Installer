package com.mcbadgercraft.installer.log;


import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import com.mcbadgercraft.installer.Bootstrap;

public class PanelAppender extends AppenderSkeleton {

	@Override
	public void close() {
		Bootstrap.getLogPanel().dispose();
	}

	@Override
	public boolean requiresLayout() {
		return true;
	}

	@Override
	protected void append(LoggingEvent event) {
		String line = layout.format(event);
		ThrowableInformation throwable = event.getThrowableInformation();
		
		if (throwable != null) {
			StringWriter writer = new StringWriter();
			writer.append(line.substring(0, line.length() - 2));
			throwable.getThrowable().printStackTrace(new PrintWriter(writer));
			Bootstrap.getLogPanel().addLogLine(writer.toString() + "\n");
		} else {
			Bootstrap.getLogPanel().addLogLine(line);
		}
	}

}
