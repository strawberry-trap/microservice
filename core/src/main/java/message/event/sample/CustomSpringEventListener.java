package message.event.sample;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * from Spring 4.2, event listeners can be implemented with @EventListener annotation on any public methods.
 * */
@Component
public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {

    @Override
    public void onApplicationEvent(CustomSpringEvent customSpringEvent) {
        // handle event, like push rabbitMQ message
    }
}
