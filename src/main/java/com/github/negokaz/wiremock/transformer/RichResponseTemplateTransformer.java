package com.github.negokaz.wiremock.transformer;

import com.github.jknack.handlebars.Helper;
import com.github.tomakehurst.wiremock.extension.TemplateHelperProviderExtension;

import java.util.HashMap;
import java.util.Map;

public class RichResponseTemplateTransformer implements TemplateHelperProviderExtension {
    private static Map<String, Helper<?>> createHelpers() {
        return createHelpers(new HashMap<>());
    }
    private static Map<String, Helper<?>> createHelpers(Map<String, Helper<?>> additionalHelpers) {
        final Map<String, Helper<?>> helpers = new HashMap<>();

        helpers.putAll(CollectionHelpers.Helpers);
        helpers.putAll(ComparisonHelpers.Helpers);
        helpers.putAll(MathHelpers.Helpers);
        helpers.putAll(DateHelpers.Helpers);

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
