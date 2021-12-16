package com.sdgesi;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import com.sdgesi.FlakyProcessor;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class IncomingRouteBuilder extends RouteBuilder {

    @ConfigProperty(name = "application.log.exhausted", defaultValue = "false")
    boolean logExhausted;

    @ConfigProperty(name = "application.queue.name", defaultValue = "incoming.messages")
    String destinationName;

    @ConfigProperty(name = "application.rest.uri", defaultValue = "outgoing")
    String restUri;

    @ConfigProperty(name = "application.rest.host", defaultValue = "localhost:18080")
    String restHost;

    @ConfigProperty(name = "application.rest.response-message", defaultValue = "Success")
    String responseMessage;

    @Override
    public void configure() {

        onException(Exception.class)
                .logExhausted(logExhausted);
        
       onException(FlakyException.class).to("log:GeneralError?level=ERROR");
/*
        rest("/incoming")
                .id("incoming")
                .post()
                .to("direct:enqueue");

        from("direct:enqueue")
                .id("enqueue")
                .to("direct:setCorrelationId")
                .log("Enqueuing message...")
                .log(LoggingLevel.DEBUG, "request payload ${body}")
                .onException(Throwable.class)
                    .maximumRedeliveries(0)
                    .handled(true)
                    .transform()
                    .simple("Error")
                .end()
                .toF("activemq:queue:%s", destinationName)
                .log("Enqueuing message...done")
                .transform()
                .simple(responseMessage);

        fromF("activemq:queue:%s?acknowledgementModeName=CLIENT_ACKNOWLEDGE", destinationName)
                .id("dequeue")
                .log("Dequeuing message...")
                .to("direct:setCorrelationId")
                .removeHeaders("CamelHttp*")
                .log("routing message to " + restHost + "/" + restUri + "...")
                .toF("rest:post:%s?host=%s&bridgeEndpoint=true", restUri, restHost)
                .log("routing message to " + restHost + "/" + restUri + "...done")
                .log("Dequeuing message...done");

        from("direct:setCorrelationId")
              //  .convertBodyTo(String.class)
                .log("Setting CorrelationId")
                .log("${body}" );
              //  .setHeader("loggingCorrelationToken").xpath("resource:classpath:correlation_id.txt")

             //   .process(new Processor() {
             //       public void process(Exchange exchange) throws Exception {
             //           String correlationId = exchange.getIn().getHeader("loggingCorrelationToken");
              //          System.out.println("correlationId is " +correlationId);
              //          org.slf4j.MDC.put("loggingCorrelationToken",correlationId);
              //      }
             //   });
*/        
        
         
        from("timer:foo??delay=30000")
        .setBody(simple("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
                + "<!--\r\n"
                + "    Licensed to the Apache Software Foundation (ASF) under one or more\r\n"
                + "    contributor license agreements.  See the NOTICE file distributed with\r\n"
                + "    this work for additional information regarding copyright ownership.\r\n"
                + "    The ASF licenses this file to You under the Apache License, Version 2.0\r\n"
                + "    (the \"License\"); you may not use this file except in compliance with\r\n"
                + "    the License.  You may obtain a copy of the License at\r\n"
                + "         http://www.apache.org/licenses/LICENSE-2.0\r\n"
                + "    Unless required by applicable law or agreed to in writing, software\r\n"
                + "    distributed under the License is distributed on an \"AS IS\" BASIS,\r\n"
                + "    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\r\n"
                + "    See the License for the specific language governing permissions and\r\n"
                + "    limitations under the License.\r\n"
                + "-->\r\n"
                + "<students>\r\n"
                + "    <student>\r\n"
                + "        <name>Claus</name>\r\n"
                + "    </student>\r\n"
                + "    <student>\r\n"
                + "        <name>Hadrian</name>\r\n"
                + "    </student>\r\n"
                + "</students>"))
        .log("Setting CorrelationId")
         .transform().xpath("resource:classpath:correlation_id.txt")
         .process(new FlakyProcessor())
        .log("${body}");
       
    }
    
   


}
