package com.github.negokaz.wiremock.transformer;


import com.github.jknack.handlebars.Options;
import com.github.tomakehurst.wiremock.extension.responsetemplating.helpers.HandlebarsHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class CollectionHelpers {
    public static final Map<String, HandlebarsHelper<Object>> Helpers = createHelpers();

    protected static Map<String, HandlebarsHelper<Object>> createHelpers() {
        Map<String, HandlebarsHelper<Object>> helpers = new HashMap<>();
        helpers.put("seq", new RichResponseTemplateHelper() {
            @Override
            protected List<Long> safeApply(Object context, Options options) {
                final long from = Long.parseLong(options.hash("from").toString());
                final long count = Long.parseLong(options.hash("count").toString());
                return LongStream.range(from, from + count).boxed().collect(Collectors.toList());
            }
        });
        helpers.put("range", new RichResponseTemplateHelper() {
            @Override
            protected List<Long> safeApply(Object context, Options options) {
                final long from = Long.parseLong(options.hash("from").toString());
                final long to = Long.parseLong(options.hash("to").toString());
                return LongStream.range(from, to + 1).boxed().collect(Collectors.toList());
            }
        });
        return helpers;
    }
}
