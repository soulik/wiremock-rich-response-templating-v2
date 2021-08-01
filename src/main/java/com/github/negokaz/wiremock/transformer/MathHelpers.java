package io.github.negokaz.wiremock.responsetemplating.rich;

import wiremock.com.github.jknack.handlebars.Helper;
import wiremock.com.github.jknack.handlebars.Options;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public enum MathHelpers implements Helper<Object> {

    add {
        @Override
        protected Object safeApply(Object context, Options options) {
            final BigDecimal lValue = new BigDecimal(context.toString());
            final BigDecimal rValue = new BigDecimal(options.param(0).toString());
            return lValue.add(rValue);
        }
    },

    sub {
        @Override
        protected Object safeApply(Object context, Options options) {
            final BigDecimal lValue = new BigDecimal(context.toString());
            final BigDecimal rValue = new BigDecimal(options.param(0).toString());
            return lValue.subtract(rValue);
        }
    },

    multiply {
        @Override
        protected Object safeApply(Object context, Options options) {
            final BigDecimal lValue = new BigDecimal(context.toString());
            final BigDecimal rValue = new BigDecimal(options.param(0).toString());
            return lValue.multiply(rValue);
        }
    },

    divide {
        @Override
        protected Object safeApply(Object context, Options options) {
            final BigDecimal lValue = new BigDecimal(context.toString());
            final BigDecimal rValue = new BigDecimal(options.param(0).toString());
            return lValue.divide(rValue, RoundingMode.HALF_UP);
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
