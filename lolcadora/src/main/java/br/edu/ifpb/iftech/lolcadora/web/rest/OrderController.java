package br.edu.ifpb.iftech.lolcadora.web.rest;

import br.edu.ifpb.iftech.lolcadora.model.Order;
import br.edu.ifpb.iftech.lolcadora.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderController {
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> buscarPedido(@PathVariable Long id){
        Order order = orderService.buscarPedido(id);

        return ResponseEntity.ok().body(order);
    }

    @PostMapping
    public ResponseEntity<Order> inserirPedido(@Valid @RequestBody Order order){
        return ResponseEntity.ok().body(orderService.criarPedido(order));
    }

    @GetMapping
    public ResponseEntity<Page<Order>> listarPedidos(Pageable pageable){
        Page<Order> orders = orderService.listarPedidos(pageable);
        return ResponseEntity.ok().body(orders);
    }
}
