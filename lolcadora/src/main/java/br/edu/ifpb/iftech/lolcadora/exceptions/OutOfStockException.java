package br.edu.ifpb.iftech.lolcadora.exceptions;

public class OutOfStockException extends RuntimeException {
    private static final Long serialVersionUID = 1L;

    public OutOfStockException(String message) {
        super(message);
    }

    public OutOfStockException(String message, Throwable cause) {
        super(message, cause);
    }
}
