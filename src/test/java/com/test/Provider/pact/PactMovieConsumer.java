package com.test.Provider.pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.jayway.jsonpath.JsonPath;
import io.pactfoundation.consumer.dsl.LambdaDsl;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;



@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "movieProvider", port = "1234")
public class PactMovieConsumer {



    @Pact(consumer = "movieConsumer", provider = "movieProvider")
    public RequestResponsePact validMovieFromProvider(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("content-type", "application/json");

        return builder
                .given(" A valid movie received from provider")
                .uponReceiving("movie from provider")
                .method("GET")
                .path("/movie")
                .willRespondWith()
                .headers(headers)
                .status(200)
                .body(LambdaDsl.newJsonBody((object) -> {
                    object.numberType("id", 2000);
                    object.stringType("name", "name");
                }).build())
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "validMovieFromProvider")
    public void testValidDateFromProvider(MockServer mockServer) throws IOException {
        System.setProperty("pact.rootDir","target/pacts");
        HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/movie")
                .execute().returnResponse();

        assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
        //assertThat(JsonPath.read(httpResponse.getEntity().getContent(), "$.isValidDate").toString()).isEqualTo("true");
    }
}
