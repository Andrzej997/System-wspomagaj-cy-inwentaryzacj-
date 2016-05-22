package pl.polsl.reservations.dto;

/**
 * Created by Krzysztof Stręk on 2016-05-21.
 */
public class UnauthorizedAccessException extends Exception {
    public UnauthorizedAccessException(String s) {
        super("Privilege required: " + s);
    }
}
