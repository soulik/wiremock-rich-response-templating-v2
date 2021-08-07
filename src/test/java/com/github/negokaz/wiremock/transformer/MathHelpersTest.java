package com.github.negokaz.wiremock.transformer;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class MathHelpersTest {

    @Rule
    public WireMockRule wireMockRule =
            new WireMockRule(wireMockConfig().dynamicPort().extensions(new RichResponseTemplateTransformer(false)));

    @Before
    public void before() {
        RestAssured.port = wireMockRule.port();
    }

    @Test
    public void testAdd() {
        wireMockRule.stubFor(
            get(urlMatching("/test"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"add\": {{add 1 1}} }")
                        .withTransformers("response-template")
                )
        );
        given()
            .get("/test")
            .then()
            .body("add", is(2));
    }

    @Test
    public void testSub() {
        wireMockRule.stubFor(
            get(urlMatching("/test"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"sub\": {{sub 10 1}} }")
                        .withTransformers("response-template")
                )
        );
        given()
            .get("/test")
            .then()
            .body("sub", is(9));
    }

    @Test
    public void testMultiply() {
        wireMockRule.stubFor(
            get(urlMatching("/test"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"multiply\": {{multiply 2 3}} }")
                        .withTransformers("response-template")
                )
        );
        given()
            .get("/test")
            .then()
            .body("multiply", is(6));
    }

    @Test
    public void testDivide() {
        wireMockRule.stubFor(
            get(urlMatching("/test"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"divide\": {{divide 6 2}} }")
                        .withTransformers("response-template")
                )
        );
        given()
            .get("/test")
            .then()
            .body("divide", is(3));
    }
}
