package listener;

import event.Event;

import java.util.EventListener;

public interface MyEventListener extends EventListener {
    public void actionPerformed(Event event);
}
