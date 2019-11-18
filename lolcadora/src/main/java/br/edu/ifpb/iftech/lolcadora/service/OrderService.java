package br.edu.ifpb.iftech.lolcadora.service;

import br.edu.ifpb.iftech.lolcadora.dto.request.OrderRequest;
import br.edu.ifpb.iftech.lolcadora.exceptions.ObjectNotFoundException;
import br.edu.ifpb.iftech.lolcadora.model.Order;
import br.edu.ifpb.iftech.lolcadora.model.OrderItem;
import br.edu.ifpb.iftech.lolcadora.repository.OrderItemRepository;
import br.edu.ifpb.iftech.lolcadora.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private OrderItemRepository orderItemRepository;
    private UserService userService;
    private ProductService productService;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
                        UserService userService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public Order criarPedido(Order order){
        order.setDataPedido(LocalDate.now());
        order.setUser(userService.buscarUsuario(order.getUser().getLogin()));

        for (OrderItem item : order.getItens()){
            item.getId().setProduct(productService.buscarProduto(item.getId().getProduct().getId()));
            item.getId().setOrder(order);
            item.setPreco(item.getId().getProduct().getPreco());
        }
        orderItemRepository.saveAll(order.getItens());
        return orderRepository.save(order);
    }

    public Order buscarPedido(Long id){
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Pedido não encontrado"));

        return order;
    }

    public Page<Order> listarPedidos(Pageable pageable){
        return orderRepository.findAll(pageable);
    }
}
