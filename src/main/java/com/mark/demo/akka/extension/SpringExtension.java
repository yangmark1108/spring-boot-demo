package com.mark.demo.akka.extension;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.mark.demo.akka.producer.SpringActorProducer;

import akka.actor.AbstractExtensionId;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.actor.Props;

@Component("springExtension")  
public class SpringExtension extends AbstractExtensionId<SpringExtension.SpringExt> {
	
	public static final SpringExtension SPRING_EXTENSION_PROVIDER = new SpringExtension();

    @Override
    public SpringExt createExtension(ExtendedActorSystem system) {
        return new SpringExt();
    }

    public static class SpringExt implements Extension {

        private volatile ApplicationContext applicationContext;

        public void initialize(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        public Props props(String actorBeanName) {
            return Props.create(SpringActorProducer.class, applicationContext, actorBeanName);
        }

    }
}
