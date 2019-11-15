package br.edu.ifpb.iftech.lolcadora.service;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RentServiceTest {
    @Test
    public void testarListagens(){
        List<String> filmes = new ArrayList<>();

        filmes.add("Olar");
        filmes.add("turu");
        filmes.add("bain");
        filmes.add("?");

        for (String titulo : filmes){
            System.out.println(titulo);
        }
    }
}
