package com.sbproject.schedule.utils;

import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import java.nio.charset.Charset;

@Plugin(name = "CustomLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)

public class CustomLayout extends AbstractStringLayout {
    protected CustomLayout(Charset charset) {
        super(charset);
    }

    @PluginFactory
    public static CustomLayout createCustomLayout(@PluginAttribute(value = "charset", defaultString = "UTF-8") Charset charset) {
        return new CustomLayout(charset);
    }

    @Override
    public String toSerializable(LogEvent event) {
        return "Custom Layout: "+event.getLoggerName()+", message: "+event.getMessage().getFormattedMessage();
    }
}
