package com.github.negokaz.wiremock.transformer;

import com.github.jknack.handlebars.Options;
import com.github.tomakehurst.wiremock.extension.responsetemplating.helpers.HandlebarsHelper;

import java.io.IOException;

public abstract class RichResponseTemplateHelper extends HandlebarsHelper<Object> {
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
