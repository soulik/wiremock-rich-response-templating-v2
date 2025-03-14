package com.github.negokaz.wiremock.transformer;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class DateHelpersTest {
    @Rule
    public WireMockRule wireMockRule =
            new WireMockRule(wireMockConfig().dynamicPort().extensions(new RichResponseTemplateTransformer()));

    @Before
    public void before() {
        RestAssured.port = wireMockRule.port();
    }

    @Test
    public void testDatePlusDays() {
        wireMockRule.stubFor(
                get(urlMatching("/test"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody("{ \"date\": \"{{date-plus-days date=\"2025-03-07\" days=13}}\" }")
                                        .withTransformers("response-template")
                        )
        );
        given()
                .get("/test")
                .then()
                .body("date", is("2025-03-20"));
    }

    @Test
    public void testDateMinusDays() {
        wireMockRule.stubFor(
                get(urlMatching("/test"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody("{ \"date\": \"{{date-plus-days date=\"2025-03-07\" days=-6}}\" }")
                                        .withTransformers("response-template")
                        )
        );
        given()
                .get("/test")
                .then()
                .body("date", is("2025-03-01"));
    }

    @Test
    public void testDateRange() {
        wireMockRule.stubFor(
                get(urlMatching("/test"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody("{ \"dates\": [{{#each (date-range from=\"2025-03-07\" to=\"2025-03-10\")}} \"{{this}}\" {{#unless @last}},{{/unless}}{{/each}}] }")
                                        .withTransformers("response-template")
                        )
        );
        given()
                .get("/test")
                .then()
                .body("dates", hasItems("2025-03-07", "2025-03-08", "2025-03-09"));
    }

    @Test
    public void testDateRangeAsQueryParameter() {
        wireMockRule.stubFor(
                get(urlPathMatching("/test")
                )
                        .withQueryParam("fromDate", equalTo("2025-03-07"))
                        .withQueryParam("toDate", equalTo("2025-03-10"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody("{ \"dates\": [{{#each (date-range from='{{request.query.fromDate}}' to='{{request.query.toDate}}')}} \"{{this}}\" {{#unless @last}},{{/unless}}{{/each}}] }")
                                        .withTransformers("response-template")
                        )
        );
        given()
                .get("/test?fromDate=2025-03-07&toDate=2025-03-10")
                .then()
                .body("dates", hasItems("2025-03-07", "2025-03-08", "2025-03-09"));
    }

    @Test
    public void testDateParse() {
        wireMockRule.stubFor(
                get(urlMatching("/test"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody("{ \"date\": { {{#with (date-parse date=\"2025-03-07\")}} \"year\":{{year}},\"month\":{{month}},\"day\":{{day}},\"dayOfWeek\":{{dayOfWeek}},\"dayOfYear\":{{dayOfYear}} {{/with}} } }")
                                        .withTransformers("response-template")
                        )
        );
        given()
                .get("/test")
                .then()
                .body("date.year", is(2025))
                .body("date.month", is(3))
                .body("date.day", is(7))
                .body("date.dayOfWeek", is(5))
                .body("date.dayOfYear", is(66));
    }

    @Test
    public void testDateParseToUnix() {
        wireMockRule.stubFor(
                get(urlMatching("/test"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody("{ \"unix_date\": {{date-parse-to-unix date=\"2025-03-07\"}} }")
                                        .withTransformers("response-template")
                        )
        );
        given()
                .get("/test")
                .then()
                .body("unix_date", is(1741305600));
    }
}
