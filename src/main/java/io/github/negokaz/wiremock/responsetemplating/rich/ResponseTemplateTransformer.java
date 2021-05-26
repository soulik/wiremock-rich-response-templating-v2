package io.github.negokaz.wiremock.responsetemplating.rich;

import wiremock.com.github.jknack.handlebars.Handlebars;
import wiremock.com.github.jknack.handlebars.Helper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResponseTemplateTransformer extends com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer {

    private static Map<String, Helper> createHelpers(Map<String, Helper> additionalHelpers) {
        final Map<String, Helper> helpers = new HashMap<>();
        for (CollectionHelpers helper: CollectionHelpers.values()) {
            helpers.put(helper.name(), helper);
        }
        for (ComparisonHelpers helper: ComparisonHelpers.values()) {
            helpers.put(helper.name(), helper);
        }
        for (MathHelpers helper: MathHelpers.values()) {
            helpers.put(helper.name(), helper);
        }
        helpers.putAll(additionalHelpers);
        return helpers;
    }

    public ResponseTemplateTransformer(boolean global) {
        super(global, createHelpers(new HashMap<>()));
    }

    public ResponseTemplateTransformer(boolean global, Map<String, Helper> helpers) {
        super(global, createHelpers(helpers));
    }

    public ResponseTemplateTransformer(boolean global, Handlebars handlebars, Map<String, Helper> helpers, Long maxCacheEntries, Set<String> permittedSystemKeys) {
        super(global, handlebars, createHelpers(helpers), maxCacheEntries, permittedSystemKeys);
    }
}
