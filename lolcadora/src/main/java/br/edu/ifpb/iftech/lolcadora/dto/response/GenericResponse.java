package br.edu.ifpb.iftech.lolcadora.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericResponse implements Serializable {
    private static final long serialVersionUID = 2249998276951457017L;

    @Getter
    private HttpStatus httpStatus;

    @Getter
    private List<String> mensagens = new ArrayList<>();

    public GenericResponse(HttpStatus httpStatus, List<String> mensagens) {
        this(httpStatus);
        this.mensagens = mensagens;
    }

    public GenericResponse(HttpStatus httpStatus) {
        super();
        this.httpStatus = httpStatus;
    }

    public void addMensagem(String mensagem){
        this.mensagens.add(mensagem);
    }

    public static GenericResponse create(HttpStatus httpStatus, String...mensagensArgs){
        List<String> mensagens = Arrays.asList(mensagensArgs);
        return GenericResponse.create(httpStatus, mensagens);
    }

    public static GenericResponse create(HttpStatus httpStatus, List<String>mensagens){
        GenericResponse genericResponse = new GenericResponse(httpStatus, mensagens);
        return genericResponse;
    }

    public ResponseEntity<GenericResponse>  buildResponseEntity(){
        return ResponseEntity.status(this.getHttpStatus()).body(this);
    }


}
