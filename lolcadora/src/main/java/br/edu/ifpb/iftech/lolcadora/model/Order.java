package br.edu.ifpb.iftech.lolcadora.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "tb_pedido")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataPedido;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User user;

    @OneToMany(mappedBy = "id.order")
    private Set<OrderItem> itens = new HashSet<>();


}
