package br.edu.ifpb.iftech.lolcadora.service;

import br.edu.ifpb.iftech.lolcadora.dto.request.RentRequest;
import br.edu.ifpb.iftech.lolcadora.exceptions.ObjectNotFoundException;
import br.edu.ifpb.iftech.lolcadora.model.Movie;
import br.edu.ifpb.iftech.lolcadora.model.Rent;
import br.edu.ifpb.iftech.lolcadora.model.RentPK;
import br.edu.ifpb.iftech.lolcadora.model.User;
import br.edu.ifpb.iftech.lolcadora.repository.MovieRepository;
import br.edu.ifpb.iftech.lolcadora.repository.RentRepository;
import br.edu.ifpb.iftech.lolcadora.repository.UserRepository;
import br.edu.ifpb.iftech.lolcadora.utils.DataUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class RentService {
    private final UserService userService;
    private final MovieService movieService;
    private final RentRepository rentRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;


    public RentService(UserService userService, MovieService movieService, RentRepository rentRepository,
                       UserRepository userRepository, MovieRepository movieRepository) {
        this.userService = userService;
        this.movieService = movieService;
        this.rentRepository = rentRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public Rent alugarFilme(String login, List<String> movies) {

        User user = this.userService.buscarUsuario(login);
        userService.isNegativado(login);

        Rent rent = new Rent();
        RentPK pk = new RentPK();
        BigDecimal total = new BigDecimal("0");

        pk.setUser(user);
        rent.setDataLocacao(LocalDate.now());

        List<Movie> filmes = new ArrayList<>();

        for(String titulo : movies){
            Movie mv = this.movieRepository.findOneByTitulo(titulo);
            filmes.add(mv);
        }

        for(Movie m: filmes){
            BigDecimal valor = m.getValor();
            movieService.temEstoque(m.getQuantidade());
            pk.setMovie(m);
            switch (filmes.size()){
                case 2: valor.multiply(new BigDecimal("0.75")); break;
                case 3: valor.multiply(new BigDecimal("0.5")); break;
                case 4: valor.multiply(new BigDecimal("0.25")); break;
                case 5: valor.multiply(new BigDecimal("0"));
            }
            valor.add(total);
            m.setQuantidade(m.getQuantidade() - 1);
        }
        rent.setValor(total);
        LocalDate dataEntrega = LocalDate.now();
        dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);
        if(DataUtils.verificarDia(dataEntrega, Calendar.SUNDAY)){
            dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);
        }

        rent.setDataRetorno(dataEntrega);

        return this.rentRepository.save(rent);
    }

    public Rent devolverFilme(String login){
        User user = this.userService.buscarUsuario(login);

        Rent rent = this.rentRepository.findRentByUser(login).orElseThrow(
                () -> new ObjectNotFoundException("Este usuário não possui locações em aberto"));

        verificarAtraso(login);
        rent.setPago(true);
        user.setNegativado(false);

        userRepository.save(user);

        return this.rentRepository.save(rent);
    }

    public void verificarAtraso(String login){
        User user = this.userService.buscarUsuario(login);

        Rent rent = this.rentRepository.findRentByUser(login).orElseThrow(
                () -> new ObjectNotFoundException("Este usuário não possui locações em aberto"));

        if(rent.getDataRetorno().compareTo(LocalDate.now()) > 0){
            rent.setValor(rent.getValor().add(new BigDecimal("5")));
            user.setNegativado(true);
        }
    }

    public void prorrogarLocacao(Rent rent, int dias){
        Rent newRent = this.rentRepository.findById(rent.getId()).orElseThrow(
                () -> new ObjectNotFoundException("Locação não encontrada"));

        newRent.setDataLocacao(LocalDate.now());
        newRent.setDataRetorno(DataUtils.obterDataFutura(dias));
        newRent.setValor(rent.getValor().multiply(BigDecimal.valueOf(dias)));
        rentRepository.save(newRent);
    }
}
