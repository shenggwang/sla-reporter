package sla.reporter.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Regex Utils used to validate input String.
 *
 * @author Sheng Wang (shenggwangg@gmail.com)
 */
public final class RegexUtils {
    /**
     * The regex expression to validate email.
     */
    private final static Pattern emailRegex = Pattern.compile("(^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$)");

    /**
     * private constructor.
     */
    private RegexUtils () {}

    /**
     * Checks whether the provided email has valid syntax.
     * @param email The email.
     * @return {@code true} if the email has valid syntax, {@code false} otherwise.
     */
    public static boolean isEmailValid(final String email) {
        final Matcher matcher = emailRegex.matcher(email);
        return matcher.matches();
    }
}
