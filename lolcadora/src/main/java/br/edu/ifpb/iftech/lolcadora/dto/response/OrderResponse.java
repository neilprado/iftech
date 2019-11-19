package br.edu.ifpb.iftech.lolcadora.dto.response;

import br.edu.ifpb.iftech.lolcadora.model.Order;
import br.edu.ifpb.iftech.lolcadora.model.OrderItem;
import br.edu.ifpb.iftech.lolcadora.model.User;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Set;

@Data
public class OrderResponse {
    private Long id;
    private LocalDate dataPedido;
    private User user;
    private Set<OrderItem> itens;

    public static OrderResponse from(Order order){
       OrderResponse orderResponse = new OrderResponse();
       orderResponse.setId(order.getId());
       orderResponse.setDataPedido(order.getDataPedido());
       orderResponse.setItens(order.getItens());
       orderResponse.setUser(order.getUser());
       return orderResponse;
    }

    public static Page<OrderResponse> from(Page<Order> orders){
        Page<OrderResponse> orderResponses = orders.map(order -> {

            OrderResponse orderResponse = new OrderResponse();

            orderResponse.setId(order.getId());
            orderResponse.setDataPedido(order.getDataPedido());
            orderResponse.setItens(order.getItens());
            orderResponse.setUser(order.getUser());
            return orderResponse;
        });
        return orderResponses;
    }
}
