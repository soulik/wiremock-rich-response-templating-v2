# WireMock Rich Response Templating Extension

A [WireMock](http://wiremock.org/) extension that provides response templating with more Handlebars helpers.

## Usage with WireMock Standalone JAR

Download the following JARs

- WireMock standalone JAR from [official website](https://wiremock.org/docs/running-standalone/)
- This extension JAR (`wiremock-rich-response-templating-x.x.x.jar`) from [releases page](https://github.com/negokaz/wiremock-rich-response-templating/releases)

Start WireMock server using the following command.

```shell
java -cp "wiremock-jre8-standalone-2.29.1.jar;wiremock-rich-response-templating-0.1.1-SNAPSHOT.jar" \
  com.github.tomakehurst.wiremock.standalone.WireMockServerRunner \
  --extensions com.github.negokaz.wiremock.transformer.RichResponseTemplateTransformer
```

You can use response templating with `.withTransformers("response-template")` which provides additional helpers.

You can use the response templating with additional helpers this extension provides
by specifying `.withTransformers("response-template")` on each stub.

```java
wireMock.stubFor(get(urlMatching("/templated"))
    .willReturn(aResponse()
        .withHeader("Content-Type", "application/json")
        .withBody("{ \"numbers\": [{{#each (range from=10 to=12)}} {{this}} {{#unless @last}},{{/unless}}{{/each}}] }")
        .withTransformers("response-template")));
```

You can also use `com.github.negokaz.wiremock.transformer.GlobalRichResponseTemplateTransformer`
which enables extended response templating automatically for all stubs.

```shell
java -cp "wiremock-jre8-standalone-2.29.1.jar;wiremock-rich-response-templating-0.1.1-SNAPSHOT.jar" \
  com.github.tomakehurst.wiremock.standalone.WireMockServerRunner \
  --extensions com.github.negokaz.wiremock.transformer.GlobalRichResponseTemplateTransformer
```

## Usage with WireMock Java API

WIP

## Helpers

[All WireMock built-in Helpers](http://wiremock.org/docs/response-templating/#handlebars-helpers) are available.

This extension provide following additional helpers.

### Collection Helper

#### seq

*template*
```
{ "seq": [{{#each (seq from=3 count=3)}}{{this}}{{#unless @last}}, {{/unless}}{{/each}}] }
```
*response*
```json
{ "seq": [3, 4, 5] }
```

#### range

*template*
```
{ "range": [{{#each (range from=5 to=10)}}{{this}}{{#unless @last}}, {{/unless}}{{/each}}] }
```
*response*
```json
{ "range": [5, 6, 7, 8, 9, 10] }
```

### Comparison Helpers

#### compare

*template*
```
{ 
    "gt": {{#if (compare 1 '>' 10)}}"A"{{else}}"B"{{/if}},
    "ge": {{#if (compare 1 '>=' 10)}}"A"{{else}}"B"{{/if}},
    "lt": {{#if (compare 1 '<' 10)}}"A"{{else}}"B"{{/if}},
    "le": {{#if (compare 1 '<=' 10)}}"A"{{else}}"B"{{/if}},
    "eq": {{#if (compare 1 '==' 10)}}"A"{{else}}"B"{{/if}},
    "ne": {{#if (compare 1 '!=' 10)}}"A"{{else}}"B"{{/if}}
}
```
*response*
```json
{
    "gt": "B",
    "ge": "B",
    "lt": "A",
    "le": "A",
    "eq": "B",
    "ne": "A"
}
```

### Math Helpers

#### add

*template*
```
{ "add": {{add 1 1}} }
```
*response*
```json
{ "add": 2 }
```

#### sub

*template*
```
{ "sub": {{sub 10 1}} }
```
*response*
```json
{ "sub": 9 }
```

#### multiply

*template*
```
{ "multiply": {{multiply 2 3}} }
```
*response*
```json
{ "multiply": 6 }
```

#### divide

*template*
```
{ "divide": {{divide 6 2}} }
```
*response*
```json
{ "divide": 3 }
```

### Date Helpers

#### date-plus-days

*template*
```
{ "date": {{date-plus-days date="2025-03-07" days=13}} }
```
*response*
```json
{ "date": "2025-03-20" }
```

#### date-plus-days - negative amount

*template*
```
{ "date": {{date-plus-days date="2025-03-07" days=-6}} }
```
*response*
```json
{ "date": "2025-03-01" }
```

#### date-range

*template*
```
{ "dates": [{{#each (date-range from="2025-03-07" to="2025-03-10")}} "{{this}}" {{#unless @last}},{{/unless}} {{/each}}] }
```
*response*
```json
{ "dates": ["2025-03-07", "2025-03-08", "2025-03-09"] }
```

#### date-parse

*template*
```
{ "date": { {{#with (date-parse date="2025-03-07")}} "year":{{year}},"month":{{month}},"day":{{day}},"dayOfWeek":{{dayOfWeek}},"dayOfYear":{{dayOfYear}} {{/with}} } }
```
*response*
```json
{
  "date": {
    "year": 2025,
    "month": 3,
    "day": 7,
    "dayOfWeek": 5,
    "dayOfYear": 66
  }
}
```

#### date-parse-to-unix

*template*
```
{ "unix_date": {{date-parse-to-unix date="2025-03-07"}} }
```
*response*
```json
{ "unix_date": 1678137600 }
```

## License

Copyright (c) 2021 Kazuki Negoro

wiremock-response-templating-contrib is released under the [MIT License](./LICENSE)
