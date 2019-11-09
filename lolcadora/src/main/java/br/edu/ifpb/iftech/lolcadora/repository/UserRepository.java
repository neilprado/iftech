package br.edu.ifpb.iftech.lolcadora.repository;

import br.edu.ifpb.iftech.lolcadora.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
