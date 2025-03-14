package com.github.negokaz.wiremock.transformer;

import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.tomakehurst.wiremock.extension.responsetemplating.helpers.HandlebarsHelper;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class MathHelpersTransformers extends GenericHelper {
    public static final Map<String, HandlebarsHelper<Object>> Helpers = createHelpers();
    public static Map<String, HandlebarsHelper<Object>> createHelpers() {
        Map<String, HandlebarsHelper<Object>> helpers = new HashMap<>();
        helpers.put("add", new HandlebarsHelper<Object>() {
            @Override
            public Object apply(Object context, Options options) throws IOException{
                final BigDecimal lValue = new BigDecimal(context.toString());
                final BigDecimal rValue = new BigDecimal(options.param(0).toString());
                return lValue.add(rValue);
            }
        });
        helpers.put("sub", new HandlebarsHelper<Object>() {
            @Override
            public Object apply(Object context, Options options) throws IOException{
                final BigDecimal lValue = new BigDecimal(context.toString());
                final BigDecimal rValue = new BigDecimal(options.param(0).toString());
                return lValue.subtract(rValue);
            }
        });
        helpers.put("multiply", new HandlebarsHelper<Object>() {
            @Override
            public Object apply(Object context, Options options) throws IOException{
                final BigDecimal lValue = new BigDecimal(context.toString());
                final BigDecimal rValue = new BigDecimal(options.param(0).toString());
                return lValue.multiply(rValue);
            }
        });
        helpers.put("divide", new HandlebarsHelper<Object>() {
            @Override
            public Object apply(Object context, Options options) throws IOException{
                final BigDecimal lValue = new BigDecimal(context.toString());
                final BigDecimal rValue = new BigDecimal(options.param(0).toString());
                return lValue.divide(rValue, RoundingMode.HALF_UP);
            }
        });
        return helpers;
    }
}
