package br.edu.ifpb.iftech.lolcadora.repository;

import br.edu.ifpb.iftech.lolcadora.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
