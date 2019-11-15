package br.edu.ifpb.iftech.lolcadora.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

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

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "dtNascimento", nullable = false)
    private LocalDate dataNascimento;

    private boolean isNegativado = false;
}
