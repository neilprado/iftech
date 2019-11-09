package br.edu.ifpb.iftech.lolcadora.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MovieRequest {
    private Long id;
    private String titulo;
    private int quantidade;
    private BigDecimal valor;
    private String genero;
}
