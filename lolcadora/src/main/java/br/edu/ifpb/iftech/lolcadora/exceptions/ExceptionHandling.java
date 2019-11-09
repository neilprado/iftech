package br.edu.ifpb.iftech.lolcadora.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.DefaultProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.ProblemBuilder;
import org.zalando.problem.Status;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.spring.web.advice.validation.ConstraintViolationProblem;
import org.springframework.security.access.AccessDeniedException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class ExceptionHandling implements ProblemHandling, SecurityAdviceTrait {

    @Autowired
    private MessageSource messageSource;

    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, NativeWebRequest request) {
        if(entity == null || entity.getBody() == null){
            return entity;
        }

        Problem problem = entity.getBody();

        if(!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)){
            return entity;
        }
        ProblemBuilder builder = Problem.builder()
                .withStatus(problem.getStatus())
                .withTitle(problem.getTitle())
                .with("path", request.getNativeRequest(HttpServletRequest.class).getRequestURI());
        if(problem instanceof ConstraintViolationProblem){
            builder.with("violations", ((ConstraintViolationProblem)problem).getViolations())
                    .withDetail(messageSource.getMessage(ProblemKey.REQUISICAO_INVALIDA.name(), null, null))
                    .with("message", ProblemKey.REQUISICAO_INVALIDA);
            return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
        }else{
            builder.withCause(((DefaultProblem) problem).getCause())
                    .withDetail(problem.getDetail())
                    .withInstance(problem.getInstance());
            problem.getParameters().forEach(builder::with);
            if(!problem.getParameters().containsKey("message") && problem.getStatus() != null){
                builder.with("message", problem.getStatus().getStatusCode() != 500 ?
                        problem.getStatus() + "_ERROR" : problem.getStatus().toString());
            }
            return new ResponseEntity<>(builder.build(), entity.getHeaders(), entity.getStatusCode());
        }
    }

    @Override
    public ResponseEntity<Problem> handleAccessDenied(AccessDeniedException e, NativeWebRequest request) {
        Problem problem = createProblem(Status.UNAUTHORIZED, ProblemKey.USUARIO_NAO_AUTORIZADO);
        return create(e, problem, request);
    }

    @Override
    public ResponseEntity<Problem> handleAuthentication(AuthenticationException e, NativeWebRequest request) {
        Problem problem = createProblem(Status.UNAUTHORIZED, ProblemKey.USUARIO_NAO_AUTENTICADO);
        return create(e, problem, request);
    }

    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @Nonnull
            NativeWebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors().stream()
                .map(f -> new FieldError(f.getObjectName(), f.getField(), f.getDefaultMessage()))
                .collect(Collectors.toList());

        Problem problem = Problem.builder()
                .withTitle("Argumento inválido")
                .withDetail(messageSource.getMessage(ProblemKey.ARGUMENTO_INVALIDO.name(),
                        null, "Argumento inválido", null))
                .withStatus(defaultConstraintViolationStatus())
                .with("message", ProblemKey.ARGUMENTO_INVALIDO)
                .with("fieldErrors", fieldErrors)
                .build();
        return create(ex, problem, request);
    }

    @ExceptionHandler(AlertException.class)
    public ResponseEntity<Problem> handleAlertException(AlertException ex, NativeWebRequest request){
        Problem problem = Problem.builder()
                .withTitle(ex.getStatus().getReasonPhrase())
                .withDetail(messageSource.getMessage(ex.getProblemKey().name(), ex.getArgs(),
                        String.format("Um erro '%s' ocorreu com a chave '%s.'",
                                ex.getStatus().getReasonPhrase(), ex.getProblemKey().name()), null))
                .withStatus(ex.getStatus())
                .with("message", ex.getProblemKey())
                .build();

        return create(ex, problem, request);
    }

    @Override
    public ResponseEntity<Problem> handleMessageNotReadableException(
            HttpMessageNotReadableException exception, NativeWebRequest request) {
        Problem problem = createProblem(Status.BAD_REQUEST, ProblemKey.REQUISICAO_ILEGIVEL);
        return create(exception, problem, request);
    }

    @ExceptionHandler(CannotCreateTransactionException.class)
    public ResponseEntity<Problem> handleAlertException(CannotCreateTransactionException ex, NativeWebRequest request){
        Problem problem = createProblem(Status.INTERNAL_SERVER_ERROR, ProblemKey.ERRO_DESCONHECIDO);
        return create(ex, problem, request);
    }

    public ResponseEntity<Problem> handleConcurrencyFailure(ConcurrencyFailureException ex, NativeWebRequest request){
        Problem problem = createProblem(Status.CONFLICT, ProblemKey.ERRO_CONCORRENCIA);
        return create(ex, problem, request);
    }

    private Problem createProblem(Status status, ProblemKey problemKey){
        return Problem.builder().withStatus(status)
                .withTitle(status.getReasonPhrase())
                .withDetail(messageSource.getMessage(problemKey.name(), null, status.getReasonPhrase(), null))
                .with("message", problemKey)
                .build();
    }

    public void log(Throwable throwable, Problem problem, NativeWebRequest request, HttpStatus status){
        if(throwable instanceof AlertException){
            AlertException alertException = (AlertException) throwable;
            if(status.is4xxClientError()){
                LOG.warn("{} ({}): {}", alertException.getProblemKey(), status.name(), problem.getDetail());
            } else if(status.is5xxServerError()){
                LOG.error("{} ({}): {}", alertException.getProblemKey(), status.name(), problem.getDetail());
            }
        } else {
            if(status.is4xxClientError()){
                LOG.warn("{} ({}): {}", status.getReasonPhrase(), throwable.getMessage());
            } else if(status.is5xxServerError()){
                LOG.error(status.getReasonPhrase(), throwable);
            }
        }
    }
}
