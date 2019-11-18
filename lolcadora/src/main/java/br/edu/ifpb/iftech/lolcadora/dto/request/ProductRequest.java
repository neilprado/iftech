package br.edu.ifpb.iftech.lolcadora.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String nome;
    private BigDecimal preco;
}
