package co.com.bancolombia.consumer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;
import java.io.IOException;


class RestConsumerTest {

    private static RestConsumer restConsumer;

    @BeforeAll
    static void setUp() throws IOException {
    }

    @AfterAll
    static void tearDown() throws IOException {
    }

    @Test
    @DisplayName("Validate the function testGet.")
    void validateTestGet() {

    }

    @Test
    @DisplayName("Validate the function testPost.")
    void validateTestPost() {

    }
}