package br.edu.ifpb.iftech.lolcadora.dto.response;

import br.edu.ifpb.iftech.lolcadora.model.Movie;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

@Data
public class MovieResponse {
    private Long id;
    private String titulo;
    private int quantidade;
    private BigDecimal valor;
    private String genero;

    public static MovieResponse from(Movie movie){
        MovieResponse response = new MovieResponse();

        response.setId(movie.getId());
        response.setTitulo(movie.getTitulo());
        response.setQuantidade(movie.getQuantidade());
        response.setValor(movie.getValor());
        response.setGenero(movie.getGenero());

        return response;
    }

    public static Page<MovieResponse> from(Page<Movie> movies){
        Page<MovieResponse> responses = movies.map(movie -> {
            MovieResponse response = new MovieResponse();

            response.setId(movie.getId());
            response.setTitulo(movie.getTitulo());
            response.setQuantidade(movie.getQuantidade());
            response.setValor(movie.getValor());
            response.setGenero(movie.getGenero());

            return response;
        });
        return responses;
    }
}
