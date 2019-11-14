package br.edu.ifpb.iftech.lolcadora.exceptions;

public class NotAdultException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public NotAdultException(String message) {
        super(message);
    }

    public NotAdultException(String message, Throwable cause) {
        super(message, cause);
    }
}
