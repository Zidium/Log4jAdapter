package zidium.log4j;

import zidium.components.IComponentControl;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

@Plugin(name = "ZidiumEvents", category = "Core", elementType = "appender", printObject = true)
public final class ZidiumEventsAppender extends AbstractAppender  {
     
    public  ZidiumEventsAppender(
            String name, 
            Layout layout, 
            Filter filter, 
            boolean ignoreExceptions) {
         super(name, filter, layout, ignoreExceptions);
    }
 
    @PluginFactory
    public static ZidiumEventsAppender createAppender(@PluginAttribute("name") String name,
                                              @PluginAttribute("ignoreExceptions") boolean ignoreExceptions,
                                              @PluginElement("Layout") Layout layout,
                                              @PluginElement("Filters") Filter filter) {
 
        if (name == null) {
            LOGGER.error("No name provided for StubAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
            
        return new ZidiumEventsAppender(
                name, 
                layout, 
                filter, 
                ignoreExceptions);
    }
    
    @Override
    public void append(LogEvent event) {
        try {  
            String loggerName = event.getLoggerName();
            IComponentControl componentControl = LoggerToComponentMap.getComponent(loggerName);
            if (componentControl==null){
                return;
            }
            Exception exception = null;
            Throwable throwable = event.getThrown();
            if (throwable != null && throwable instanceof Exception){
                exception = (Exception)throwable;
            }
            String message = event.getMessage().getFormattedMessage();
            if (exception==null){
                componentControl.addError(message);
            }
            else{
                componentControl.addError(message, exception);
            }
            
        } catch (Exception ex) {
            LOGGER.error("append error", ex);
        } 
    }
}
