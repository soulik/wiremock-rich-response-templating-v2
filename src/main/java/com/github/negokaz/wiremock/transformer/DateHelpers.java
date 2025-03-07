package com.github.negokaz.wiremock.transformer;


import com.github.jknack.handlebars.Options;
import com.github.tomakehurst.wiremock.extension.responsetemplating.helpers.HandlebarsHelper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class DateHelpers {
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    private static final ZoneOffset DEFAULT_DATE_ZONE = ZoneOffset.UTC;

    public static final Map<String, HandlebarsHelper<Object>> Helpers = createHelpers();

    protected static LocalDate parseDate(String date, String datePattern) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(datePattern));
    }

    protected static Map<String, HandlebarsHelper<Object>> createHelpers() {
        Map<String, HandlebarsHelper<Object>> helpers = new HashMap<>();

        helpers.put("date-plus-days", new RichResponseTemplateHelper() {
            @Override
            protected Object safeApply(Object context, Options options) {
                final String dateFormat = options.hash("format", DEFAULT_DATE_PATTERN).toString();
                final LocalDate parsedDate = parseDate(options.hash("date").toString(), dateFormat);
                final long daysToAdd = Long.parseLong(options.hash("days").toString());

                return parsedDate.plusDays(daysToAdd).format(DateTimeFormatter.ofPattern(dateFormat));
            }
        });

        helpers.put("date-range", new RichResponseTemplateHelper() {
            @Override
            protected List<String> safeApply(Object context, Options options) {
                final String dateFormat = options.hash("format", DEFAULT_DATE_PATTERN).toString();

                final LocalDate from = parseDate(options.hash("from").toString(), dateFormat);
                final LocalDate to = parseDate(options.hash("to").toString(), dateFormat);

                return from.datesUntil(to).map(date -> date.format(DateTimeFormatter.ofPattern(dateFormat))).collect(Collectors.toList());
            }
        });
        helpers.put("date-parse", new RichResponseTemplateHelper() {
            @Override
            protected Object safeApply(Object context, Options options) {
                final String dateFormat = options.hash("format", DEFAULT_DATE_PATTERN).toString();
                final LocalDate parsedDate = parseDate(options.hash("date").toString(), dateFormat);
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
        helpers.put("date-parse-to-unix", new RichResponseTemplateHelper() {
            @Override
            protected Object safeApply(Object context, Options options) {
                final String dateFormat = options.hash("format", DEFAULT_DATE_PATTERN).toString();
                final LocalDate parsedDate = parseDate(options.hash("date").toString(), dateFormat);
                final ZoneOffset zone = ZoneOffset.of(options.hash("zone", DEFAULT_DATE_ZONE.toString()).toString());
                return parsedDate.toEpochSecond(LocalTime.of(0,0,0), zone);
            }
        });
        return helpers;
    }
}
