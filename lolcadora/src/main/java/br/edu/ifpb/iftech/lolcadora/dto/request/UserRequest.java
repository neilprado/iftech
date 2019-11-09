package br.edu.ifpb.iftech.lolcadora.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequest {
    private String login;
    private String senha;
    private String nome;
    private String endereco;
    private LocalDate dataNascimento;
}
