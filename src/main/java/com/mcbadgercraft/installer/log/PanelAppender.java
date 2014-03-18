package com.mcbadgercraft.installer.log;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;

import com.mcbadgercraft.installer.Bootstrap;

@Plugin(name = "Panel", category = "Core", elementType = "appender", printObject = true)
public class PanelAppender extends AbstractAppender {

	protected PanelAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
		super(name, filter, layout);
	}

	@Override
	public void append(LogEvent event) {
		String line = getLayout().toSerializable(event).toString();
		Throwable throwable = event.getThrown();
		
		if (throwable != null) {
			StringWriter writer = new StringWriter();
			writer.append(line.substring(0, line.length() - 2));
			throwable.printStackTrace(new PrintWriter(writer));
			Bootstrap.getLogPanel().addLogLine(writer.toString() + "\n");
		} else {
			Bootstrap.getLogPanel().addLogLine(line);
		}
	}

	@PluginFactory
	public static PanelAppender createAppender(@PluginAttribute("name") String name, 
													@PluginElement("Layout") Layout<? extends Serializable> layout, 
													@PluginAttribute("Filters") Filter filter) {

		if (name == null) {
			LOGGER.error("No name provided for PanelAppender");
			return null;
		}
		
		if (layout == null) {
			LOGGER.error("No layout provided for PanelAppender");
			return null;
		}
		
		return new PanelAppender(name, filter, layout);
	}
}
