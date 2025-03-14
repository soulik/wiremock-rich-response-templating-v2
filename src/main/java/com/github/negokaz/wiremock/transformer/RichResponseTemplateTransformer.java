package com.github.negokaz.wiremock.transformer;

import com.github.jknack.handlebars.Helper;
import com.github.tomakehurst.wiremock.extension.TemplateHelperProviderExtension;
import com.github.tomakehurst.wiremock.extension.responsetemplating.helpers.HandlebarsHelper;

import java.util.HashMap;
import java.util.Map;

public class RichResponseTemplateTransformer implements TemplateHelperProviderExtension {
    private static Map<String, Helper<?>> createHelpers() {
        return createHelpers(new HashMap<>());
    }
    private static Map<String, Helper<?>> createHelpers(Map<String, HandlebarsHelper<Object>> additionalHelpers) {
        final Map<String, Helper<?>> helpers = new HashMap<>();

        helpers.putAll(CollectionHelperTransformers.createHelpers());
        helpers.putAll(ComparisonHelpersTransformers.createHelpers());
        helpers.putAll(MathHelpersTransformers.createHelpers());
        helpers.putAll(DateHelpersTransformers.createHelpers());

        helpers.putAll(additionalHelpers);
        return helpers;
    }

    @Override
    public Map<String, Helper<?>> provideTemplateHelpers() {
        return createHelpers();
    }

    @Override
    public String getName() {
        return "rich-response-template";
    }
}
