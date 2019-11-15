package br.edu.ifpb.iftech.lolcadora.dto.response;

import br.edu.ifpb.iftech.lolcadora.model.Movie;
import br.edu.ifpb.iftech.lolcadora.model.Rent;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class RentResponse {
    private String userLogin;
    private String movieTitle;
    private LocalDate dataLocacao;
    private LocalDate dataRetorno;
    private BigDecimal valor;
    private Boolean isPago;

    public static RentResponse from(Rent rent){
        RentResponse rentResponse = new RentResponse();
        rentResponse.setUserLogin(rent.getId().getUser().getLogin());
        rentResponse.setMovieTitle(rent.getId().getMovie().getTitulo());
        rentResponse.setDataLocacao(rent.getDataLocacao());
        rentResponse.setDataRetorno(rent.getDataRetorno());
        rentResponse.setValor(rent.getValor());
        rentResponse.setIsPago(rent.isPago());

        return rentResponse;
    }
}
