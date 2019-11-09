package br.edu.ifpb.iftech.lolcadora.service;

import br.edu.ifpb.iftech.lolcadora.dto.request.MovieRequest;
import br.edu.ifpb.iftech.lolcadora.exceptions.BadRequestAlertException;
import br.edu.ifpb.iftech.lolcadora.exceptions.NotFoundAlertExcepcion;
import br.edu.ifpb.iftech.lolcadora.exceptions.ProblemKey;
import br.edu.ifpb.iftech.lolcadora.model.Movie;
import br.edu.ifpb.iftech.lolcadora.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie cadastrarFilme(MovieRequest request){
        Movie movie = new Movie();

//        if(this.movieRepository.findByTitulo(request.getTitulo())){
//            throw new BadRequestAlertException(ProblemKey.FILME_JA_CADASTRADO);
//        }

//        if(this.movieRepository.findByGenero(request.getGenero())){
//            throw new BadRequestAlertException(ProblemKey.GENERO_NAO_ENCONTRADO);
//        }

        movie.setGenero(request.getGenero());
        movie.setQuantidade(request.getQuantidade());
        movie.setTitulo(request.getTitulo());
        movie.setValor(request.getValor());

        return this.movieRepository.save(movie);
    }

    public Movie atualizarFilme(Long id, MovieRequest request){
        Movie movie = this.movieRepository.findById(id).orElseThrow(
                () -> new NotFoundAlertExcepcion(ProblemKey.FILME_NAO_ENCONTRADO));

//        if(this.movieRepository.findByTitulo(request.getTitulo())){
//            throw new BadRequestAlertException(ProblemKey.FILME_JA_CADASTRADO);
//        }
//
//        if(this.movieRepository.findByGenero(request.getGenero())){
//            throw new BadRequestAlertException(ProblemKey.GENERO_NAO_ENCONTRADO);
//        }

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
                () -> new NotFoundAlertExcepcion(ProblemKey.FILME_NAO_ENCONTRADO));
        return movie;
    }

    public void removerFilme(Long id){
        Movie movie = this.movieRepository.findById(id).orElseThrow(
                () -> new NotFoundAlertExcepcion(ProblemKey.FILME_NAO_ENCONTRADO));
        this.movieRepository.delete(movie);
    }
}
