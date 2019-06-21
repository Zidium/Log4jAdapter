package zidium.log4j;

import java.util.HashMap;
import java.util.Map;
import zidium.components.IComponentControl;


public class LoggerToComponentMap {
    
    private static IComponentControl _defaultComponent;
    private static final Map<String, IComponentControl> _components = new HashMap<>();
    
    public static void setDefaultComponent(IComponentControl componentControl){
        _defaultComponent = componentControl;
    }
    
    public static synchronized void add(String loggerName, IComponentControl componentControl){
        _components.put(loggerName, componentControl);
    }
    
    public static synchronized IComponentControl getComponent(String loggerName){
        return _components.getOrDefault(loggerName, _defaultComponent);
    }
    
    public static synchronized IComponentControl[] getComponents(){
        return _components.values().toArray(new IComponentControl[_components.size()]);
    }
}
