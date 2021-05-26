/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package io.github.negokaz.wiremock.responsetemplating.rich;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CollectionHelpersTest {

    @Rule
    public WireMockRule wireMockRule =
            new WireMockRule(wireMockConfig().dynamicPort().extensions(new ResponseTemplateTransformer(false)));

    @Before
    public void before() {
        RestAssured.port = wireMockRule.port();
    }

    @Test
    public void testRange() {
        wireMockRule.stubFor(
            get(urlMatching("/test"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"test\": [{{#each (range from=10 to=12)}} {{this}} {{#unless @last}},{{/unless}}{{/each}}] }")
                        .withTransformers("response-template")
                )
        );
        given()
            .get("/test")
            .then()
            .body("test", hasItems(10, 11, 12));
    }

    @Test
    public void testSeq() {
        wireMockRule.stubFor(
                get(urlMatching("/test"))
                        .willReturn(
                                aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody("{ \"test\": [{{#each (seq from=10 count=3)}} {{this}} {{#unless @last}},{{/unless}}{{/each}}] }")
                                        .withTransformers("response-template")
                        )
        );
        given()
                .get("/test")
                .then()
                .body("test", hasItems(10, 11, 12));
    }
}
