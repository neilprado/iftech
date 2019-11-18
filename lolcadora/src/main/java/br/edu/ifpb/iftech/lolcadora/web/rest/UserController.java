package br.edu.ifpb.iftech.lolcadora.web.rest;

import br.edu.ifpb.iftech.lolcadora.dto.request.UserRequest;
import br.edu.ifpb.iftech.lolcadora.dto.response.UserResponse;
import br.edu.ifpb.iftech.lolcadora.model.User;
import br.edu.ifpb.iftech.lolcadora.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserResponse> cadastrarUsuario(@Valid @RequestBody UserRequest request){
        User user = this.service.cadastrarUsuario(request);
        return ResponseEntity.ok(UserResponse.from(user));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> listarUsuarios(Pageable pageable){
        Page<User> users = this.service.listarUsuarios(pageable);
        return ResponseEntity.ok(UserResponse.from(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> buscarUsuario(@Valid @PathVariable(value = "id") String login){
        User user = this.service.buscarUsuario(login);
        return ResponseEntity.ok(UserResponse.from(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> atualizarUsuario(@Valid @PathVariable(value = "id") String login,
                                                         @Valid @RequestBody UserRequest request){
        User user = this.service.atualizarUsuario(login, request);
        return ResponseEntity.ok(UserResponse.from(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUsuario(@Valid @PathVariable(value = "id") String login){
        this.service.removerUsuario(login);
        return ResponseEntity.ok().build();
    }


}
