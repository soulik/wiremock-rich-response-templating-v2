package com.github.negokaz.wiremock.transformer;

import wiremock.com.github.jknack.handlebars.Helper;
import wiremock.com.github.jknack.handlebars.Options;

import java.io.IOException;
import java.math.BigDecimal;

public enum ComparisonHelpers implements Helper<Object> {

    compare {
        @Override
        protected Object safeApply(Object context, Options options) {
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
    };

    @Override
    public Object apply(Object context, Options options) throws IOException {
        if (options.isFalsy(context)) {
            Object param = options.param(0, null);
            return param == null ? null : param.toString();
        } else {
            return safeApply(context, options);
        }
    }

    protected abstract Object safeApply(Object context, Options options);
}
