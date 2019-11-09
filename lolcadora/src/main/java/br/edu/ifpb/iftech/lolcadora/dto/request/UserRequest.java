package br.edu.ifpb.iftech.lolcadora.dto.request;

import lombok.Data;

import java.util.Date;

@Data
public class UserRequest {
    private String login;
    private String senha;
    private String nome;
    private String endereco;
    private Date dataNascimento;
}
