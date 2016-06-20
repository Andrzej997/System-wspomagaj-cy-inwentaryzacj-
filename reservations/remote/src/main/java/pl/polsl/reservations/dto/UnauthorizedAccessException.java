package pl.polsl.reservations.dto;

/**
 * Created by Krzysztof Stręk on 2016-05-21.
 * @version
 * 
 * Exception thrown when user has no access to execute method
 */
public class UnauthorizedAccessException extends Exception {

    private static final long serialVersionUID = 7639354758040199123L;

    public UnauthorizedAccessException(String s) {
        super("Privilege required: " + s);
    }
}
