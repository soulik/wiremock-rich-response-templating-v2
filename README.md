# WireMock Rich Response Templating

A [WireMock](http://wiremock.org/) extension that provides response templating with more Handlebars helpers.

## Usages

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

## License

Copyright (c) 2021 Kazuki Negoro

wiremock-response-templating-contrib is released under the [MIT License](./LICENSE)
