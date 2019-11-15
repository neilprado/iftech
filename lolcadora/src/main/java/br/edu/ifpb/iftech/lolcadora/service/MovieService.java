package br.edu.ifpb.iftech.lolcadora.service;

import br.edu.ifpb.iftech.lolcadora.dto.request.MovieRequest;
import br.edu.ifpb.iftech.lolcadora.exceptions.DataIntegrityException;
import br.edu.ifpb.iftech.lolcadora.exceptions.ObjectNotFoundException;
import br.edu.ifpb.iftech.lolcadora.exceptions.OutOfStockException;
import br.edu.ifpb.iftech.lolcadora.model.Movie;
import br.edu.ifpb.iftech.lolcadora.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie cadastrarFilme(MovieRequest request){

        if(this.movieRepository.findOneByTitulo(request.getTitulo()) != null){
            throw new DataIntegrityException("Título já cadastrado");
        }

        Movie movie = new Movie();

        movie.setGenero(request.getGenero());
        movie.setQuantidade(request.getQuantidade());
        movie.setTitulo(request.getTitulo());
        movie.setValor(request.getValor());

        return this.movieRepository.save(movie);
    }

    public Movie atualizarFilme(Long id, MovieRequest request){
        Movie movie = this.movieRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Filme não encontrado"));


        movie.setGenero(request.getGenero());
        movie.setQuantidade(request.getQuantidade());
        movie.setTitulo(request.getTitulo());
        movie.setValor(request.getValor());

        return this.movieRepository.save(movie);
    }

    public Page<Movie> listarFilmes(Pageable pageable){
        return this.movieRepository.findAll(pageable);
    }

    public Movie buscarFilme(Long id){
        Movie movie = this.movieRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Filme não encontrado"));

        return movie;
    }

    public void removerFilme(Long id){
        Movie movie = this.movieRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Filme não encontrado"));
        this.movieRepository.delete(movie);
    }

    public void temEstoque(int quantidade){
        if(quantidade == 0){
            throw new OutOfStockException("Não temos cópias disponíveis para este filme");
        }
    }
}
