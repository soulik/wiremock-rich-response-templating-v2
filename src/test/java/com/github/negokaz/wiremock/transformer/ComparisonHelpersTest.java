package com.github.negokaz.wiremock.transformer;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ComparisonHelpersTest {

    @Rule
    public WireMockRule wireMockRule =
            new WireMockRule(wireMockConfig().dynamicPort().extensions(new RichResponseTemplateTransformer(false)));

    @Before
    public void before() {
        RestAssured.port = wireMockRule.port();
    }

    @Test
    public void testGreater() {
        wireMockRule.stubFor(
            get(urlMatching("/test"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody(
                                "{" +
                                    "\"test1\": {{#if (compare 0.1 '>' 10)}}\"A\"{{else}}\"B\"{{/if}}," +
                                    "\"test2\": {{#if (compare 0.5 '>' 0.1)}}\"A\"{{else}}\"B\"{{/if}} " +
                                "}"
                            )
                            .withTransformers("response-template")
                )
        );
        given()
            .get("/test")
            .then()
            .body("test1", is("B"))
            .body("test2", is("A"));
    }

    @Test
    public void testGreaterOrEqual() {
        wireMockRule.stubFor(
            get(urlMatching("/test"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                            "{" +
                                "\"test1\": {{#if (compare 0.1 '>=' 10)}}\"A\"{{else}}\"B\"{{/if}}," +
                                "\"test2\": {{#if (compare 0.1 '>=' 0.1)}}\"A\"{{else}}\"B\"{{/if}}," +
                                "\"test3\": {{#if (compare 0.5 '>=' 0.1)}}\"A\"{{else}}\"B\"{{/if}} " +
                            "}"
                        )
                        .withTransformers("response-template")
                )
        );
        given()
            .get("/test")
            .then()
            .body("test1", is("B"))
            .body("test2", is("A"))
            .body("test3", is("A"));
    }

    @Test
    public void testLess() {
        wireMockRule.stubFor(
            get(urlMatching("/test"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                            "{" +
                                "\"test1\": {{#if (compare 0.1 '<' 10)}}\"A\"{{else}}\"B\"{{/if}}," +
                                "\"test2\": {{#if (compare 0.5 '<' 0.1)}}\"A\"{{else}}\"B\"{{/if}} " +
                            "}"
                        )
                        .withTransformers("response-template")
                )
        );
        given()
            .get("/test")
            .then()
            .body("test1", is("A"))
            .body("test2", is("B"));
    }

    @Test
    public void testLessOrEqual() {
        wireMockRule.stubFor(
            get(urlMatching("/test"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                            "{" +
                                "\"test1\": {{#if (compare 0.1 '<=' 10)}}\"A\"{{else}}\"B\"{{/if}}," +
                                "\"test2\": {{#if (compare 0.1 '<=' 0.1)}}\"A\"{{else}}\"B\"{{/if}}," +
                                "\"test3\": {{#if (compare 0.5 '<=' 0.1)}}\"A\"{{else}}\"B\"{{/if}} " +
                            "}"
                        )
                        .withTransformers("response-template")
                )
        );
        given()
            .get("/test")
            .then()
            .body("test1", is("A"))
            .body("test2", is("A"))
            .body("test3", is("B"));
    }

    @Test
    public void testEqual() {
        wireMockRule.stubFor(
            get(urlMatching("/test"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                            "{" +
                                "\"test1\": {{#if (compare 0.1 '==' 10)}}\"A\"{{else}}\"B\"{{/if}}," +
                                "\"test2\": {{#if (compare 0.1 '==' 0.1)}}\"A\"{{else}}\"B\"{{/if}} " +
                            "}"
                        )
                        .withTransformers("response-template")
                )
        );
        given()
            .get("/test")
            .then()
            .body("test1", is("B"))
            .body("test2", is("A"));
    }

    @Test
    public void testNotEqual() {
        wireMockRule.stubFor(
            get(urlMatching("/test"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                            "{" +
                                "\"test1\": {{#if (compare 0.1 '!=' 10)}}\"A\"{{else}}\"B\"{{/if}}," +
                                "\"test2\": {{#if (compare 0.1 '!=' 0.1)}}\"A\"{{else}}\"B\"{{/if}} " +
                            "}"
                        )
                        .withTransformers("response-template")
                )
        );
        given()
            .get("/test")
            .then()
            .body("test1", is("A"))
            .body("test2", is("B"));
    }
}
