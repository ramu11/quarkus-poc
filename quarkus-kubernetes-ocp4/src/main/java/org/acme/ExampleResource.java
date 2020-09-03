package org.acme;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.camel.CamelContext;

@Path("/hello")
public class ExampleResource {
    
 // This is to test wiring the camel context and creating a producer template.
    @Inject
    CamelContext context;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
      return "hello";
    }
}