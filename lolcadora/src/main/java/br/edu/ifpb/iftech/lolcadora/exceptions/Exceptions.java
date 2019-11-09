package br.edu.ifpb.iftech.lolcadora.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class Exceptions extends RuntimeException {

    private static final Long serialVersionUID = -601362897548358062L;

    @Getter
    private HttpStatus httpStatus;

    public Exceptions(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public Exceptions(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public static Exceptions criarBadRequestException(String descricao){
        return new Exceptions(HttpStatus.BAD_REQUEST, descricao);
    }
}
