package org.acme.timer;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaRoute extends RouteBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaRoute.class);

    @Override
    public void configure() throws Exception {

        /*
         * from("timer://foo?period={{period}}")
         * .from("kafka:{{kafka.topic}}?brokers={{kafka.bootstrap.url}}")
         * .log("${body}");
         */

        // external
        from("timer://foo?period={{period}}")
                .from("kafka:{{kafka.topic}}?brokers={{kafka.external.bootstrap.url}}&securityProtocol=SSL"
                        + "&sslTruststoreLocation={{truststore}}&"
                        + "sslTruststorePassword={{truststore.password}}&groupId=cameltest")
                .log("Message received from Kafka : ${body}")
                .log("    on the topic ${headers[kafka.TOPIC]}")
                .log("    on the partition ${headers[kafka.PARTITION]}")
                .log("    with the offset ${headers[kafka.OFFSET]}")
                .log("    with the key ${headers[kafka.KEY]}");

    }
}