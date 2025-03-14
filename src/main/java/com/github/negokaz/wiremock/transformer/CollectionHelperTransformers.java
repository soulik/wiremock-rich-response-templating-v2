package com.github.negokaz.wiremock.transformer;


import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.tomakehurst.wiremock.extension.responsetemplating.helpers.HandlebarsHelper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class CollectionHelperTransformers extends GenericHelper {
    public static final Map<String, HandlebarsHelper<Object>> Helpers = createHelpers();

    public static Map<String, HandlebarsHelper<Object>> createHelpers() {
        Map<String, HandlebarsHelper<Object>> helpers = new HashMap<>();
        helpers.put("seq", new HandlebarsHelper<Object>()  {
            @Override
            public Object apply(Object context, Options options) throws IOException {
                final long from = Long.parseLong(getStringValue(options,"from"));
                final long count = Long.parseLong(getStringValue(options,"count"));
                return LongStream.range(from, from + count).boxed().collect(Collectors.toList());
            }
        });
        helpers.put("range", new HandlebarsHelper<Object>() {
            @Override
            public Object apply(Object context, Options options) throws IOException{
                final long from = Long.parseLong(getStringValue(options,"from"));
                final long to = Long.parseLong(getStringValue(options,"to"));
                return LongStream.range(from, to + 1).boxed().collect(Collectors.toList());
            }
        });
        return helpers;
    }
}
