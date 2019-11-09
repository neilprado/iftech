package br.edu.ifpb.iftech.lolcadora.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_usuario")
@Entity
@Data
public class User {

    @Id
    @Column(name = "login")
    private String login;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "nomeCivil", nullable = false)
    private String nome;

    @Column(name = "endereco", nullable = false)
    private String endereco;

    @Column(name = "dtNascimento", nullable = false)
    private Date dataNascimento;
}
