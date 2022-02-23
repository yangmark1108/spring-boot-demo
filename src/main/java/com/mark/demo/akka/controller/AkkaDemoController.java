package com.mark.demo.akka.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mark.demo.akka.actor.GreetingActor;
import com.mark.demo.akka.extension.SpringExtension;
import com.mark.demo.akka.model.Greet;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

@RestController
public class AkkaDemoController {
	
	@Autowired
	private ActorSystem actorSystem;
	
	@Autowired
	private SpringExtension springExtension;

    @RequestMapping("/actor")
    public Object sendToActor() throws Exception {
    	ActorRef greeter = actorSystem.actorOf(springExtension.props("greetingActor"), "demoActorName");

    	String name = "mark yang";
        
        FiniteDuration duration = FiniteDuration.create(10, TimeUnit.SECONDS);
        Timeout timeout = Timeout.durationToTimeout(duration);

        Future<Object> future = Patterns.ask(greeter, new Greet(name), timeout);
        return Await.result(future, duration);
    }
}
