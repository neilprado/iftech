package br.edu.ifpb.iftech.lolcadora.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "tb_itens")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @EmbeddedId
    private OrderItemPK id = new OrderItemPK();

    private int quantidade;
    private BigDecimal preco;

    public OrderItem() {
    }

    public OrderItem(Order order, Product product, int quantidade, BigDecimal preco) {
        this.id.setOrder(order);
        this.id.setProduct(product);
        this.quantidade = quantidade;
        this.preco = preco;
    }

    @JsonIgnore
    public Order getOrder(){
        return this.id.getOrder();
    }

    public void setOrder(Order order){
        this.id.setOrder(order);
    }

    public void setProduct(Product product){
        this.id.setProduct(product);
    }

    public Product getProduct(){
        return this.getProduct();
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OrderItem other = (OrderItem) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
