package br.edu.ifpb.iftech.lolcadora.exceptions;

import lombok.Getter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.StatusType;


public class AlertException extends AbstractThrowableProblem {

    @Getter
    private final ProblemKey problemKey;

    @Getter
    private Object[] args;

    public AlertException(StatusType status, ProblemKey problemKey) {
        super(null, null, status);
        this.problemKey = problemKey;
    }

    public AlertException(StatusType status, ProblemKey problemKey, Object... args) {
        super(null, null, status);
        this.problemKey = problemKey;
        this.args = args;
    }
}
