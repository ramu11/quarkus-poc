package com.sdgesi;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.component.ComponentsBuilderFactory;
import org.apache.camel.component.activemq.ActiveMQComponent;
import org.apache.commons.lang.RandomStringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ApplicationScoped
public class Configuration {

    @ConfigProperty(name = "application.max.redeliveries", defaultValue = "20")
    int maximumRedeliveries;

    @ConfigProperty(name = "application.initial.redelivery.delay", defaultValue = "5000")
    int initialRedeliveryDelay;

    @ConfigProperty(name = "application.redelivery.delay", defaultValue = "5000")
    int redeliveryDelay;

    @ConfigProperty(name = "application.broker.url", defaultValue = "tcp://broker-amq:61616")
    String brokerURL;

    @ConfigProperty(name = "application.amq.user", defaultValue = "admin")
    String amqUser;

    @ConfigProperty(name = "application.amq.pass", defaultValue = "admin")
    String amqPassword;

    @ConfigProperty(name = "application.amq.maxConnections", defaultValue = "10")
    Integer maxConnections;

    @ConfigProperty(name = "application.amq.ttl", defaultValue = "3600000")
    Long timeToLive;

    @ConfigProperty(name = "quarkus.application.name")
    String applicationName;


    @Inject
    CamelContext context;


    @Named
    public ActiveMQComponent activemq() {
        ActiveMQComponent component =
                ComponentsBuilderFactory
                        .activemq()
                        .brokerURL(brokerURL)
                        .errorHandlerLogStackTrace(false)
                        .build();

        component.setCamelContext(context);
        component.setMaxConcurrentConsumers(5);
        component.setUsePooledConnection(true);
        component.setCacheLevelName("CACHE_CONSUMER");
        component.setTimeToLive(timeToLive);


        configureRedeliveryPolicy(component);

        return component;
    }


    private void configureRedeliveryPolicy(ActiveMQComponent component) {
        PooledConnectionFactory connectionFactory = (PooledConnectionFactory) component.getOrCreateConnectionFactory();
        connectionFactory.setMaxConnections(maxConnections);

        ActiveMQConnectionFactory acf = (ActiveMQConnectionFactory) connectionFactory.getConnectionFactory();
        acf.setUserName(amqUser);
        acf.setPassword(amqPassword);
        acf.setNonBlockingRedelivery(true);
        acf.setClientIDPrefix(applicationName);
        acf.setConnectionIDPrefix(applicationName);

        RedeliveryPolicy redeliveryPolicy = acf.getRedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(maximumRedeliveries);
        redeliveryPolicy.setInitialRedeliveryDelay(initialRedeliveryDelay);
        redeliveryPolicy.setRedeliveryDelay(redeliveryDelay);
    }
}
