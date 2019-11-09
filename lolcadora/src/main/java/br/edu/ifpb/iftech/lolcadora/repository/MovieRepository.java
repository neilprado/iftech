package br.edu.ifpb.iftech.lolcadora.repository;

import br.edu.ifpb.iftech.lolcadora.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean findByTitulo(String titulo);
    boolean findByGenero(String genero);
}
