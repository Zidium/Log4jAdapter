package zidium.log4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import zidium.client.ZidiumClient;
import zidium.components.IComponentControl;

public class ZidiumEventsAppenderTest {

    @Test
    public void testErrorEvent() {
        
        IComponentControl component = ZidiumClient.getDefault().getRootComponentControl().getOrCreateChild("ZidiumLog4AdapterTests");
        LoggerToComponentMap.setDefaultComponent(component);
        Logger logger = LogManager.getLogger();

        logger.error("Test");
    }

    @Test
    public void testErrorEventChildComponent() {

        IComponentControl component = ZidiumClient.getDefault().getRootComponentControl().getOrCreateChild("ZidiumLog4AdapterTests");
        LoggerToComponentMap.setDefaultComponent(component);
        IComponentControl childComponent = component.getOrCreateChild("Child");
        LoggerToComponentMap.add("ChildLogger", childComponent);
        Logger logger = LogManager.getLogger("ChildLogger");

        logger.error("Test");
    }

}
