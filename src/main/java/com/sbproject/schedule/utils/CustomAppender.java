package com.sbproject.schedule.utils;

import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.IOException;
import java.io.Serializable;

// name attr - name which will be used in Configuration
@Plugin(name="CustomAppender", category= Core.CATEGORY_NAME, elementType= Appender.ELEMENT_TYPE)
public class CustomAppender extends AbstractAppender {

    protected CustomAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions, Property[] properties) {
        super(name, filter, layout, ignoreExceptions, properties);
    }


//    Note that the packages attribute should reference the package that contains your custom appender.


//    @PluginFactory
//    public static CustomAppender createAppender(@PluginAttribute("name") String name, @PluginElement("Filter") Filter filter) {
//        return new CustomAppender(name, filter);
//    }

    @PluginFactory
    public static CustomAppender createAppender(@PluginAttribute("name") String name,
                                                @PluginElement("Filter") Filter filter,
                                                @PluginElement("Layout") Layout<? extends Serializable> layout) {
        if (name == null) {
            LOGGER.error("No name provided for CustomAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new CustomAppender(name, filter,layout,false,null);
    }


    //private ConcurrentMap<String, LogEvent> eventMap = new ConcurrentHashMap<>();
// eventMap.put(Instant.now().toString(), event);

    @Override
    public void append(LogEvent event) {
        System.out.println("CustomAppender works: "+ getLayout().toSerializable(event));
    }
}