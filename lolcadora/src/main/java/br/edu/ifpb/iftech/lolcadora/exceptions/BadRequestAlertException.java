package br.edu.ifpb.iftech.lolcadora.exceptions;

import org.zalando.problem.Status;
import org.zalando.problem.StatusType;

public class BadRequestAlertException extends AlertException {
    private static final StatusType STATUS = Status.BAD_REQUEST;

    public BadRequestAlertException(ProblemKey problemKey){
        super(STATUS, problemKey);
    }

    public BadRequestAlertException(StatusType status, ProblemKey problemKey, Object... args) {
        super(status, problemKey, args);
    }
}
