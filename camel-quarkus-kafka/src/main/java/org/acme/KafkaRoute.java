package org.acme.timer;

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

        
         from("timer://foo?period={{period}}")
         .setBody(constant("Hi This is kafka example")) 
         .to("kafka:{{kafka.topic}}?brokers={{kafka.bootstrap.url}}"
                 + "&keySerializerClass=org.apache.kafka.common.serialization.StringSerializer&serializerClass=org.apache.kafka.common.serialization.StringSerializer")
         .log("${body}");
         

        // external
        /*from("timer://foo?period={{period}}").setBody(constant("post message to kafka cluster topic"))
                .to("kafka:{{kafka.topic}}?brokers={{kafka.external.bootstrap.url}}"
                        + "&keySerializerClass={{kafka.key.serializer}}&serializerClass={{kafka.value.serializer}}"
                        + "&securityProtocol=SSL&sslTruststoreLocation={{truststore}}"
                        + "&sslTruststorePassword={{truststore.password}}")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        List<RecordMetadata> recordMetaData1 = (List<RecordMetadata>) exchange.getIn()
                                .getHeader(KafkaConstants.KAFKA_RECORDMETA);
                        for (RecordMetadata rd : recordMetaData1) {
                            LOG.info("producer partition is:" + rd.partition());
                            LOG.info("producer partition message is:" + rd.toString());
                        }
                    }

                });*/

    }

}