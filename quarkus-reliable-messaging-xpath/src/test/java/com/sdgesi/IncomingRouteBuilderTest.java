package com.sdgesi;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.camel.CamelContext;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


@QuarkusTest
public class IncomingRouteBuilderTest {

    @Inject
    CamelContext camelContext;

    @ConfigProperty(name = "application.rest.response-message", defaultValue = "Success")
    String responseMessage;

    //@Test
    public void testIncomingEndpoint() {

//        given()
//            .when().post("/outgoing")
//            .then()
//                .statusCode(200)
//                .body(is(responseMessage));

    }
}
