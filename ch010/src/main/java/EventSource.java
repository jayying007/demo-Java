import event.Event;
import listener.MyEventListener;

import java.util.ArrayList;
import java.util.List;

public class EventSource implements EventPublisher{
    private final List<MyEventListener> list = new ArrayList<>();

    public EventSource() {

    }
    public void addListener(MyEventListener listener) {
        list.add(listener);
    }

    @Override
    public void publishEvent(Event event) {
        for (MyEventListener listener : list) {
            listener.actionPerformed(event);
        }
    }
}
