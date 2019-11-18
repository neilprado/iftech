package br.edu.ifpb.iftech.lolcadora.repository;

import br.edu.ifpb.iftech.lolcadora.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
