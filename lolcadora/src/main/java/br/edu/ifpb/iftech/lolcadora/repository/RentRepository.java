package br.edu.ifpb.iftech.lolcadora.repository;

import br.edu.ifpb.iftech.lolcadora.model.Rent;
import br.edu.ifpb.iftech.lolcadora.model.RentPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RentRepository extends JpaRepository<Rent, RentPK> {
    @Query(value = "SELECT r from Rent r JOIN User u where u.login LIKE ?1")
    Optional<Rent> findRentByUser(String login);
}
