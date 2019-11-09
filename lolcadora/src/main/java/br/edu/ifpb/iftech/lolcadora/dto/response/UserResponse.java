package br.edu.ifpb.iftech.lolcadora.dto.response;

import br.edu.ifpb.iftech.lolcadora.model.User;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

@Data
public class UserResponse {
    private String login;
    private String senha;
    private String nome;
    private String endereco;
    private LocalDate dataNascimento;

    public static UserResponse from(User user){
        UserResponse response = new UserResponse();

        response.setDataNascimento(user.getDataNascimento());
        response.setEndereco(user.getEndereco());
        response.setLogin(user.getLogin());
        response.setNome(user.getNome());
        response.setSenha(user.getSenha());

        return response;
    }

    public static Page<UserResponse> from(Page<User> users){
        Page<UserResponse> responses = users.map(user -> {
            UserResponse response = new UserResponse();

            response.setDataNascimento(user.getDataNascimento());
            response.setEndereco(user.getEndereco());
            response.setLogin(user.getLogin());
            response.setNome(user.getNome());
            response.setSenha(user.getSenha());

            return response;
        });
        return responses;
    }
}
