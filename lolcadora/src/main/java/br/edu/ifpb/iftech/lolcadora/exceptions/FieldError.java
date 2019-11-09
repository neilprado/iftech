package br.edu.ifpb.iftech.lolcadora.exceptions;

import lombok.Getter;

import java.io.Serializable;

public class FieldError implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Getter
    private final String objectName;

    @Getter
    private final String field;

    @Getter
    private final String message;

    public FieldError(String objectName, String field, String message) {
        this.objectName = objectName;
        this.field = field;
        this.message = message;
    }
}
