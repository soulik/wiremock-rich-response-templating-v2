package com.github.negokaz.wiremock.transformer;


import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.tomakehurst.wiremock.extension.responsetemplating.helpers.HandlebarsHelper;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DateHelpersTransformers extends GenericHelper {
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private static final ZoneOffset DEFAULT_DATE_ZONE = ZoneOffset.UTC;

    public static final Map<String, HandlebarsHelper<Object>> Helpers = createHelpers();

    protected static LocalDate parseDate(String date, String datePattern) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(datePattern));
    }

    public static Map<String, HandlebarsHelper<Object>> createHelpers() {
        Map<String, HandlebarsHelper<Object>> helpers = new HashMap<>();

        helpers.put("date-plus-days", new HandlebarsHelper<Object>() {
            @Override
            public Object apply(Object context, Options options) throws IOException {
                final String dateFormat = options.hash("format", DEFAULT_DATE_PATTERN).toString();
                final LocalDate parsedDate = parseDate(getStringValue(options,"date"), dateFormat);
                final long daysToAdd = Long.parseLong(getStringValue(options,"days"));

                return parsedDate.plusDays(daysToAdd).format(DateTimeFormatter.ofPattern(dateFormat));
            }
        });

        helpers.put("date-range", new HandlebarsHelper<Object>() {
            @Override
            public Object apply(Object context, Options options) throws IOException{
                final String dateFormat = options.hash("format", DEFAULT_DATE_PATTERN).toString();

                final LocalDate from = parseDate(getStringValue(options, "from"), dateFormat);
                final LocalDate to = parseDate(getStringValue(options,"to"), dateFormat);

                return from.datesUntil(to).map(date -> date.format(DateTimeFormatter.ofPattern(dateFormat))).collect(Collectors.toList());
            }
        });
        helpers.put("date-parse", new HandlebarsHelper<Object>() {
            @Override
            public Object apply(Object context, Options options) throws IOException {
                final String dateFormat = options.hash("format", DEFAULT_DATE_PATTERN).toString();
                final LocalDate parsedDate = parseDate(getStringValue(options,"date"), dateFormat);
                return new HashMap<>() {
                    {
                        put("year", parsedDate.getYear());
                        put("month", parsedDate.getMonthValue());
                        put("day", parsedDate.getDayOfMonth());
                        put("dayOfWeek", parsedDate.getDayOfWeek().getValue());
                        put("dayOfYear", parsedDate.getDayOfYear());
                    }
                };
            }
        });
        helpers.put("date-parse-to-unix", new HandlebarsHelper<Object>() {
            @Override
            public Object apply(Object context, Options options) throws IOException {
                final String dateFormat = options.hash("format", DEFAULT_DATE_PATTERN).toString();
                final LocalDate parsedDate = parseDate(getStringValue(options,"date"), dateFormat);
                final ZoneOffset zone = ZoneOffset.of(options.hash("zone", DEFAULT_DATE_ZONE.toString()).toString());
                return parsedDate.toEpochSecond(LocalTime.of(0,0,0), zone);
            }
        });
        return helpers;
    }
}
