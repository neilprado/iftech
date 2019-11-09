package br.edu.ifpb.iftech.lolcadora.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "tb_filme")
@Entity
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "filme_id")
    private Long id;

    @Column(name = "nome_filme", nullable = false)
    private String titulo;

    @Column(name = "quantidade", nullable = false)
    private int quantidade;

    @Column(name = "precoLocacao", nullable = false)
    private BigDecimal valor;

    @Column(name = "genero", nullable = false)
    private String genero;
}
