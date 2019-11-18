package br.edu.ifpb.iftech.lolcadora.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "tb_itens")
public class OrderItem implements Serializable
{
    @JsonIgnore
    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();

    private int quantidade;
    private BigDecimal preco;
}
