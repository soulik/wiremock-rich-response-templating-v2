package com.github.negokaz.wiremock.transformer;

import com.github.jknack.handlebars.Options;
import com.github.tomakehurst.wiremock.extension.responsetemplating.helpers.HandlebarsHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class MathHelpers {
    public static final Map<String, HandlebarsHelper<Object>> Helpers = createHelpers();
    protected static Map<String, HandlebarsHelper<Object>> createHelpers() {
        Map<String, HandlebarsHelper<Object>> helpers = new HashMap<>();
        helpers.put("add", new RichResponseTemplateHelper() {
            @Override
            protected Object safeApply (Object context, Options options){
                final BigDecimal lValue = new BigDecimal(context.toString());
                final BigDecimal rValue = new BigDecimal(options.param(0).toString());
                return lValue.add(rValue);
            }
        });
        helpers.put("sub", new RichResponseTemplateHelper() {
            @Override
            protected Object safeApply (Object context, Options options){
                final BigDecimal lValue = new BigDecimal(context.toString());
                final BigDecimal rValue = new BigDecimal(options.param(0).toString());
                return lValue.subtract(rValue);
            }
        });
        helpers.put("multiply", new RichResponseTemplateHelper() {
            @Override
            protected Object safeApply (Object context, Options options){
                final BigDecimal lValue = new BigDecimal(context.toString());
                final BigDecimal rValue = new BigDecimal(options.param(0).toString());
                return lValue.multiply(rValue);
            }
        });
        helpers.put("divide", new RichResponseTemplateHelper() {
            @Override
            protected Object safeApply (Object context, Options options){
                final BigDecimal lValue = new BigDecimal(context.toString());
                final BigDecimal rValue = new BigDecimal(options.param(0).toString());
                return lValue.divide(rValue, RoundingMode.HALF_UP);
            }
        });
        return helpers;
    }
}
