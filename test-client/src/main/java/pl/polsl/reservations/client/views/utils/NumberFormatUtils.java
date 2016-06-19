package pl.polsl.reservations.client.views.utils;

/**
 *
 * @author matis
 */
public class NumberFormatUtils {

    public static Boolean isInteger(String text) {
        try {
            Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static Boolean isDouble(String text) {
        try {
            Double.parseDouble(text);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static Boolean isShort(String text) {
        try {
            Short.parseShort(text);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static Boolean isLong(String text) {
        try {
            Long.parseLong(text);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static Boolean isBoolean(String text) {
        try {
            Boolean.parseBoolean(text);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }

    public static Boolean isFloat(String text) {
        try {
            Float.parseFloat(text);
        } catch (NumberFormatException ex) {
            return false;
        }
        return true;
    }
}
