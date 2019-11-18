package br.edu.ifpb.iftech.lolcadora.repository;

import br.edu.ifpb.iftech.lolcadora.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
