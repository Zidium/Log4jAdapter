package zidium.log4j;

import zidium.events.ZidiumEvent;

public class EventsContainer {

    private ZidiumEvent _firstEvent;
    private ZidiumEvent _lastEvent;

    public synchronized void sendAndClear() {
        if (_firstEvent != null && !_firstEvent.getMessage().equals(_lastEvent.getMessage())){
            _firstEvent.getComponentControl().addEvent(_firstEvent);
        }
        
        if (_lastEvent != null) {
            _lastEvent.getComponentControl().addEvent(_lastEvent);
        }
        
        _firstEvent = null;
        _lastEvent = null;
    }

    public synchronized void add(ZidiumEvent event) {
        if (_firstEvent == null)
            _firstEvent = event;
        _lastEvent = event;
    }
}
