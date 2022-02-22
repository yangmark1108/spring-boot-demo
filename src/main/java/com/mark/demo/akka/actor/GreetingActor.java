package com.mark.demo.akka.actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mark.demo.akka.model.Greet;
import com.mark.demo.akka.service.GreetingService;

import akka.actor.UntypedActor;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GreetingActor extends UntypedActor {

	@Autowired
    private GreetingService greetingService;

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Greet) {
            String name = ((Greet) message).getName();
            getSender().tell(greetingService.greet(name), getSelf());
        } else {
            unhandled(message);
        }
    }
}
