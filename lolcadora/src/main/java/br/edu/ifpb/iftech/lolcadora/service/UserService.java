package br.edu.ifpb.iftech.lolcadora.service;

import br.edu.ifpb.iftech.lolcadora.dto.request.UserRequest;
import br.edu.ifpb.iftech.lolcadora.exceptions.BadRequestAlertException;
import br.edu.ifpb.iftech.lolcadora.exceptions.NotFoundAlertExcepcion;
import br.edu.ifpb.iftech.lolcadora.exceptions.ProblemKey;
import br.edu.ifpb.iftech.lolcadora.model.User;
import br.edu.ifpb.iftech.lolcadora.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User cadastrarUsuario(UserRequest request){
        User user = new User();

        if(this.userRepository.existsById(request.getLogin())){
            throw new BadRequestAlertException(ProblemKey.LOGIN_EXISTENTE);
        }

        user.setDataNascimento(request.getDataNascimento());
        user.setEndereco(request.getEndereco());
        user.setLogin(request.getLogin());
        user.setNome(request.getNome());
        user.setSenha(request.getSenha());

        return this.userRepository.save(user);
    }

    public User atualizarUsuario(String login, UserRequest request){
        User user = this.userRepository.findById(login).orElseThrow(
                () -> new NotFoundAlertExcepcion(ProblemKey.USUARIO_NAO_ENCONTRADO));

        user.setSenha(request.getSenha());
        user.setNome(request.getNome());
        user.setLogin(request.getLogin());
        user.setEndereco(request.getEndereco());
        user.setDataNascimento(request.getDataNascimento());

        return this.userRepository.save(user);
    }

    public Page<User> listarUsuarios(Pageable pageable){
        return this.userRepository.findAll(pageable);
    }

    public User buscarUsuario(String login){
        User user = this.userRepository.findById(login).orElseThrow(
                () -> new NotFoundAlertExcepcion(ProblemKey.USUARIO_NAO_ENCONTRADO));
        return user;
    }

    public void removerUsuario(String login){
        User user = this.userRepository.findById(login).orElseThrow(
                () -> new NotFoundAlertExcepcion(ProblemKey.USUARIO_NAO_ENCONTRADO));
        this.userRepository.delete(user);
    }
}
