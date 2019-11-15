package br.edu.ifpb.iftech.lolcadora.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RentRequest {
    private List<String> movies;
}
