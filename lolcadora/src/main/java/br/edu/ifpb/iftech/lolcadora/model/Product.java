package br.edu.ifpb.iftech.lolcadora.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "tb_produto")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private BigDecimal preco;

    @JsonIgnore
    @OneToMany(mappedBy = "id.produto")
    private Set<OrderItem> itens = new HashSet<>();
}
