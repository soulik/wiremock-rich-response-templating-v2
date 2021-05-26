package io.github.negokaz.wiremock.responsetemplating.rich;

import wiremock.com.github.jknack.handlebars.Helper;
import wiremock.com.github.jknack.handlebars.Options;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public enum CollectionHelpers implements Helper<Object> {

    seq {
        @Override
        protected List<Long> safeApply(Object context, Options options) {
            final long from = Long.parseLong(options.hash("from").toString());
            final long count = Long.parseLong(options.hash("count").toString());
            return LongStream.range(from, from + count).boxed().collect(Collectors.toList());
        }
    },

    range {
        @Override
        protected List<Long> safeApply(Object context, Options options) {
            final long from = Long.parseLong(options.hash("from").toString());
            final long to = Long.parseLong(options.hash("to").toString());
            return LongStream.range(from, to + 1).boxed().collect(Collectors.toList());
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
