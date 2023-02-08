/* A Classe UsuarioController, é responsável por fornecer o acesso aos recursos do sistema. Uma das suas funcionalidades
abaixo é promover o CRUD do usuário. Além de permitir total manipulação do usuário, consumindo os serviços cadastrar usuário,
atualizar usuário e autenticar da Classe UsuarioService. */

package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/all")
    public ResponseEntity <List<Usuario>> getAll(){
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/logar")
    public ResponseEntity<UsuarioLogin> login(@RequestBody Optional<UsuarioLogin> usuarioLogin) {
        return usuarioService.autenticarUsuario(usuarioLogin)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> postUsuario(@Valid @RequestBody Usuario usuario) {

        return usuarioService.cadastrarUsuario(usuario)
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED).body(resposta))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());

    }

    @PutMapping("/atualizar")
    public ResponseEntity<Usuario> putUsuario(@Valid @RequestBody Usuario usuario) {
        return usuarioService.atualizarUsuario(usuario)
                .map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
/* Observe que nos Métodos Cadastrar, Atualizar e Autenticar, ao invés de usarmos a injeção de dependência da Interface
UsuarioRepository, estamos utilizando a injeção de dependência da Classe de Serviço UsuarioService porquê os 3 Métodos
foram implementados nela.

--> nos Métodos postUsuario e putUsuario para não esquecer a notação @Valid.
Caso ela não seja inserida, ao fornecer parâmetros inválidos, o servidor retornará o HTTP Status 500, ao invés do HTTP Status 400. */