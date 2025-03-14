package com.github.negokaz.wiremock.transformer;

import com.github.jknack.handlebars.Options;
import com.github.tomakehurst.wiremock.common.RequestCache;

import java.io.IOException;

public class GenericHelper {
    public static String getStringValue(Options options, String key) {
        Object input = options.hash(key);
        if (input == null || (input instanceof String && ((String) input).isEmpty())) {
            return "";
        }

        try {
            Object value = options.apply(options.handlebars.compileInline(input.toString()));
            return value.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
