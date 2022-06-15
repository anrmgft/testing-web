package com.example.testingweb;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void greetingShouldReturnDefaultMessage() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/", String.class))
                .contains("Hello, World");
    }

    @Test
    public void catAdd() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1&b=2", String.class))
                .isEqualTo("3.0");
    }

    @Test
    public void catAddWithMissingValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1", String.class))
                .isEqualTo("1.0");
    }

    @Test
    public void catAddWithEmptyValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1&b=", String.class))
                .isEqualTo("1.0");
    }

    @Test
    public void catAddWithFractions() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1.5&b=2", String.class))
                .isEqualTo("3.5");
    }

    @Test
    public void catAddWithInvalidNumber() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1&b=X", String.class))
                .isEqualTo("");
    }

    @Test
    public void catAddNegativeNumbers() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1&b=-2", String.class))
                .isEqualTo("-1.0");
    }

}




