package br.edu.ifpb.iftech.lolcadora.exceptions;

import org.zalando.problem.Status;
import org.zalando.problem.StatusType;

public class NotFoundAlertExcepcion extends AlertException {
    private static final StatusType STATUS = Status.NOT_FOUND;

    public NotFoundAlertExcepcion(ProblemKey problemKey) {
        super(STATUS, problemKey);
    }

    public NotFoundAlertExcepcion(StatusType status, ProblemKey problemKey, Object... args) {
        super(status, problemKey, args);
    }
}
