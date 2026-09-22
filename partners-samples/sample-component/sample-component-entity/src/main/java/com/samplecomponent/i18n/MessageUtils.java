package com.samplecomponent.i18n;

import javax.ws.rs.core.HttpHeaders;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Resolves localized API messages from the client's Accept-Language header.
 */
public final class MessageUtils {

    private static final String BUNDLE_NAME = "messages";
    private static final Locale DEFAULT_LOCALE = new Locale("pt", "BR");

    private MessageUtils() {
    }

    public static Locale resolveLocale(HttpHeaders headers) {
        if (headers != null) {
            List<Locale> acceptableLanguages = headers.getAcceptableLanguages();
            if (acceptableLanguages != null && !acceptableLanguages.isEmpty()) {
                return acceptableLanguages.get(0);
            }
        }
        return DEFAULT_LOCALE;
    }

    public static String getMessage(Locale locale, String key, Object... arguments) {
        Locale resolvedLocale = locale == null ? DEFAULT_LOCALE : locale;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME, resolvedLocale);
            return MessageFormat.format(bundle.getString(key), arguments);
        } catch (MissingResourceException exception) {
            ResourceBundle defaultBundle = ResourceBundle.getBundle(BUNDLE_NAME, DEFAULT_LOCALE);
            return MessageFormat.format(defaultBundle.getString(key), arguments);
        }
    }
}
