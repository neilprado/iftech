package br.edu.ifpb.iftech.lolcadora.exceptions;

import br.edu.ifpb.iftech.lolcadora.dto.response.GenericResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(value = Exceptions.class)
    public ResponseEntity<GenericResponse> globalExceptionHandler(HttpServletRequest request, Exceptions e){
        return GenericResponse.create(e.getHttpStatus(), e.getMessage()).buildResponseEntity();
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<GenericResponse> exceptionHandler(HttpServletRequest request, Exception e){
        return GenericResponse.create(HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro Interno do sistema.").buildResponseEntity();
    }

    @ExceptionHandler(value = ServletRequestBindingException.class)
    public ResponseEntity<GenericResponse> servletRequestBindingExceptionHandler(HttpServletRequest request, ServletRequestBindingException e){
        return GenericResponse.create(HttpStatus.BAD_REQUEST, e.getMessage()).buildResponseEntity();
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<GenericResponse> userNotAuthorizedExceptionHandler(HttpServletRequest request,
                                                                             ServletRequestBindingException e){
        return GenericResponse.create(HttpStatus.UNAUTHORIZED,
                "Usuário não autenticado").buildResponseEntity();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse> methodArgumentNotValidExceptionHandler(HttpServletRequest req, MethodArgumentNotValidException exception) {
        List<String> erros = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> erros.add(new String(e.getField() + ": " + e.getDefaultMessage())));
        return GenericResponse.create(HttpStatus.BAD_REQUEST, erros).buildResponseEntity();
    }

    @ExceptionHandler(value = HttpMessageConversionException.class)
    public ResponseEntity<GenericResponse> servletRequestBindingExceptionHandler(HttpServletRequest req, HttpMessageConversionException exception) {
        GenericResponse genericResponse = new GenericResponse(HttpStatus.UNPROCESSABLE_ENTITY);
        Throwable cause = exception.getCause();
        if(cause == null) {
            genericResponse.addMensagem(exception.getMessage());
        } else if(cause instanceof InvalidFormatException) {
            InvalidFormatException ex = (InvalidFormatException) cause;
            if(ex.getPath() != null && !ex.getPath().isEmpty()) {
                genericResponse.addMensagem(ex.getPath().get(0).getFieldName() + ": " + "valor " +  ex.getValue() + " é invalido.");
            } else {
                genericResponse.addMensagem(ex.getOriginalMessage());
            }

        } else {
            genericResponse.addMensagem("Json invalido.");
        }

        return genericResponse.buildResponseEntity();
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<GenericResponse> MethodArgumentTypeMismatchExceptionHandler(HttpServletRequest req, MethodArgumentTypeMismatchException exception) {
        String mensagem = "Valor '" + exception.getValue() + "' não pode ser convertido para o formato " + exception.getRequiredType() + ".";
        return GenericResponse.create(HttpStatus.UNPROCESSABLE_ENTITY, mensagem).buildResponseEntity();
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<GenericResponse> MethodArgumentTypeMismatchExceptionHandler(HttpServletRequest req, AccessDeniedException exception) {
        return GenericResponse.create(HttpStatus.UNAUTHORIZED, "O usuário não possui permissão de acesso para esse endpoint.").buildResponseEntity();
    }

    @ExceptionHandler(value = CannotCreateTransactionException.class)
    public ResponseEntity<GenericResponse> transactionExceptionHandler(HttpServletRequest req, CannotCreateTransactionException exception) {
        if (exception.getCause() instanceof Exceptions) {
            Exceptions exceptionCardsApi = (Exceptions) exception.getCause();
            return GenericResponse.create(exceptionCardsApi.getHttpStatus(), exceptionCardsApi.getMessage()).buildResponseEntity();
        }
        return GenericResponse.create(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao tentar conexão com a base de dados").buildResponseEntity();
    }
}
