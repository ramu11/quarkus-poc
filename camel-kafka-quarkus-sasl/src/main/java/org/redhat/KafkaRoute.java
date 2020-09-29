package org.redhat;

import java.util.List;

import org.apache.camel.builder.RouteBuilder;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaRoute extends RouteBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaRoute.class);

    @Override
    public void configure() throws Exception {

        //producer
        /**
         from("timer://foo?period={{period}}")
         .setBody(constant("Hi This is kafka example")) 
         .to("kafka:{{kafka.topic}}?brokers={{kafka.bootstrap.url}}"
                 + "&keySerializerClass=org.apache.kafka.common.serialization.StringSerializer"
                 + "&serializerClass=org.apache.kafka.common.serialization.StringSerializer" 
                 + "&securityProtocol={{security.protocol}}&saslJaasConfig={{sasl.jaas.config}}&saslMechanism={{sasl.mechanism}}")
         .log("${body}");
         */
         //consumer
         
        
        from("kafka:{{consumer.topic}}?brokers={{kafka.bootstrap.url}}&maxPollRecords={{consumer.max.poll.records}}"
                  + "&keySerializerClass=org.apache.kafka.common.serialization.StringSerializer"
                 + "&serializerClass=org.apache.kafka.common.serialization.StringSerializer" 
                + "&groupId={{consumer.group}}&securityProtocol={{security.protocol}}&saslJaasConfig={{sasl.jaas.config}}&saslMechanism={{sasl.mechanism}}"
                + "&autoOffsetReset={{consumer.auto.offset.reset}}&autoCommitEnable={{consumer.auto.commit.enable}}")
        .log("${body}");
    
         
         

       

    }

}
