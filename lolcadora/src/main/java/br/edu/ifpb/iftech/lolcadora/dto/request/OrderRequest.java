package br.edu.ifpb.iftech.lolcadora.dto.request;

import br.edu.ifpb.iftech.lolcadora.model.OrderItem;
import br.edu.ifpb.iftech.lolcadora.model.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class OrderRequest {
    private LocalDate dataPedido;
    private User userLogin;
    private Set<OrderItem> itens;

}
