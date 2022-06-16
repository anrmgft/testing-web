package com.example.testingweb;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import org.skyscreamer.jsonassert.JSONAssert;
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
    public void catAddWithInvalidNumber() throws JSONException {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1&b=X", String.class))
                .contains(":400");
        assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/add?a=1&b=X", String.class)
                .getStatusCode()
                .value()
        ).isEqualTo(400);
        assertThat(this.restTemplate.getForEntity("http://localhost:" + port + "/add?a=1&b=X", String.class)
                .getStatusCode()
                .is4xxClientError()
        ).isTrue();
        JSONAssert.assertEquals(
                "{status:400}",
                this.restTemplate.getForEntity("http://localhost:" + port + "/add?a=1&b=X", String.class).getBody(),
                false
        );
    }

    @Test
    public void catAddNegativeNumbers() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/add?a=1&b=-2", String.class))
                .isEqualTo("-1.0");
    }

    @Test
    public void catSubtract() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/subtract?a=1&b=2", String.class))
                .isEqualTo("-1.0");
    }

    @Test
    public void catSubtractWithMissingValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/subtract?a=1", String.class))
                .isEqualTo("1.0");
    }

    @Test
    public void catSubtractWithEmptyValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/subtract?a=1&b=", String.class))
                .isEqualTo("1.0");
    }

    @Test
    public void catSubtractWithFractions() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/subtract?a=1.5&b=2", String.class))
                .isEqualTo("-0.5");
    }

    @Test
    public void catSubtractNegativeNumbers() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/subtract?a=1&b=-2", String.class))
                .isEqualTo("3.0");
    }

    @Test
    public void catMultiply() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/multiply?a=1&b=2", String.class))
                .isEqualTo("2.0");
    }

    @Test
    public void catMultiplyWithMissingValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/multiply?a=1", String.class))
                .isEqualTo("0.0");
    }

    @Test
    public void catMultiplyWithEmptyValue() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/multiply?a=1&b=", String.class))
                .isEqualTo("0.0");
    }

    @Test
    public void catMultiplyWithFractions() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/multiply?a=1.5&b=2", String.class))
                .isEqualTo("3.0");
    }

    @Test
    public void catMultiplyNegativeNumbers() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/multiply?a=1&b=-2", String.class))
                .isEqualTo("-2.0");
    }
    @Test
    public void catDivide() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/divide?a=1&b=2", String.class))
                .isEqualTo("0.5");
    }
    @Test
    public void catDivideZero() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/divide?a=1&b=0", String.class))
                .isEqualTo("\"Infinity\"");
    }
}




