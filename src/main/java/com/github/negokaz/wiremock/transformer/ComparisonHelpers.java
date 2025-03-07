package com.github.negokaz.wiremock.transformer;

import com.github.jknack.handlebars.Options;
import com.github.tomakehurst.wiremock.extension.responsetemplating.helpers.HandlebarsHelper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ComparisonHelpers {
    public static final Map<String, HandlebarsHelper<Object>> Helpers = createHelpers();
    protected static Map<String, HandlebarsHelper<Object>> createHelpers() {
        Map<String, HandlebarsHelper<Object>> helpers = new HashMap<>();
        helpers.put("compare", new RichResponseTemplateHelper() {
            @Override
            protected Object safeApply (Object context, Options options){
                final BigDecimal lValue = new BigDecimal(context.toString());
                final String op = options.param(0).toString();
                final BigDecimal rValue = new BigDecimal(options.param(1).toString());
                switch (op) {
                    case ">":
                        return lValue.compareTo(rValue) > 0;
                    case ">=":
                        return lValue.compareTo(rValue) >= 0;
                    case "<":
                        return lValue.compareTo(rValue) < 0;
                    case "<=":
                        return lValue.compareTo(rValue) <= 0;
                    case "==":
                        return lValue.compareTo(rValue) == 0;
                    case "!=":
                        return lValue.compareTo(rValue) != 0;
                    default:
                        return false;
                }
            }
        });
        return helpers;
    }
}
