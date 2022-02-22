package com.mark.demo.akka.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mark.demo.akka.model.Greet;
import com.mark.demo.akka.producer.ActorsProducer;

import akka.actor.ActorRef;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.FiniteDuration;

@RestController
public class AkkaDemoController {
	
	@Autowired
    private ActorsProducer actorsProducer;

    @RequestMapping("/actor")
    public Object sendToActor() throws Exception {
        ActorRef greeter = actorsProducer.createActor("greetingActor", "demoActorName");

        String name = "mark";
        
        FiniteDuration duration = FiniteDuration.create(1, TimeUnit.SECONDS);
        Timeout timeout = Timeout.durationToTimeout(duration);

        Future<Object> future = Patterns.ask(greeter, new Greet(name), timeout);
        return Await.result(future, duration);
    }
}
