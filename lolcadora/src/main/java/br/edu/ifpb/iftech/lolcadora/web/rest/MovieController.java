package br.edu.ifpb.iftech.lolcadora.web.rest;

import br.edu.ifpb.iftech.lolcadora.dto.request.MovieRequest;
import br.edu.ifpb.iftech.lolcadora.dto.response.MovieResponse;
import br.edu.ifpb.iftech.lolcadora.model.Movie;
import br.edu.ifpb.iftech.lolcadora.service.MovieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/movies")
public class MovieController {
    private MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<MovieResponse> cadastrarFilme(@Valid @RequestBody MovieRequest request){
        Movie movie = this.service.cadastrarFilme(request);
        return ResponseEntity.ok(MovieResponse.from(movie));
    }

    @GetMapping
    public ResponseEntity<Page<MovieResponse>> listarFilmes(Pageable pageable){
        Page<Movie> movies = this.service.listarFilmes(pageable);
        return ResponseEntity.ok(MovieResponse.from(movies));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> buscarFilme(@Valid @PathVariable(value = "id") Long id){
        Movie movie = this.service.buscarFilme(id);
        return ResponseEntity.ok(MovieResponse.from(movie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> atualizarFilme(@Valid @PathVariable(value = "id")Long id,
                                                        @Valid @RequestBody MovieRequest request){
        Movie movie = this.service.atualizarFilme(id, request);
        return ResponseEntity.ok(MovieResponse.from(movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerFilme(@Valid @PathVariable(value = "id") Long id){
        this.service.removerFilme(id);
        return ResponseEntity.ok().build();
    }
}
