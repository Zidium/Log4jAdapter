package zidium.log4j;

import zidium.logs.ILog;
import zidium.components.IComponentControl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

@Plugin(name = "ZidiumLogs", category = "Core", elementType = "appender", printObject = true)
public final class ZidiumLogsAppender extends AbstractAppender  {
     
    public ZidiumLogsAppender(
            String name, 
            Layout layout, 
            Filter filter, 
            boolean ignoreExceptions) {
         super(name, filter, layout, ignoreExceptions);
    }
 
    @PluginFactory
    public static ZidiumLogsAppender createAppender(@PluginAttribute("name") String name,
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
        return new ZidiumLogsAppender(name, layout, filter, ignoreExceptions);
    }
    
    @Override
    public void append(LogEvent event) {
        try {
            String loggerName = event.getLoggerName();
            IComponentControl componentControl = LoggerToComponentMap.getComponent(loggerName);
            if (componentControl==null){
                return;
            }
            ILog log = componentControl.getLog();
            String message = event.getMessage().getFormattedMessage();
            Exception exception = null;
            Throwable throwable = event.getThrown();
            if (throwable != null && throwable instanceof Exception){
                exception = (Exception)throwable;
            }
            Level level = event.getLevel();
            
            if (exception==null){
                if (level==Level.TRACE){
                    log.trace(message);
                }
                else if (level==Level.DEBUG){
                    log.debug(message);
                }
                else if (level==Level.INFO){
                    log.info(message);
                }
                else if (level==Level.WARN){
                    log.warning(message);
                }
                else if (level==Level.ERROR){
                    log.error(message);
                }
                else if (level==Level.FATAL){
                    log.fatal(message);
                }
            }
            else{
                if (level==Level.TRACE){
                    log.trace(message, exception);
                }
                else if (level==Level.DEBUG){
                    log.debug(message, exception);
                }
                else if (level==Level.INFO){
                    log.info(message, exception);
                }
                else if (level==Level.WARN){
                    log.warning(message, exception);
                }
                else if (level==Level.ERROR){
                    log.error(message, exception);
                }
                else if (level==Level.FATAL){
                    log.fatal(message, exception);
                }
            }
            
        } catch (Exception ex) {
            LOGGER.error("append error", ex);
        } 
    }
}
