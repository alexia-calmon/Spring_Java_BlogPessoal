/* A Classe UsuarioService é responsável por manipular as regras de negócio de usuário no sistema. Esta Classe deve ser
anotada com a anotação @Service, para que o Spring identifique que é uma Classe que serviço. Vale mencionar que alguns
Métodos definidos abaixo são de extrema importância para a Spring Security. */

package com.generation.blogpessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

        if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
            return Optional.empty();

        usuario.setSenha(criptografarSenha(usuario.getSenha()));

        return Optional.of(usuarioRepository.save(usuario));

    }

    public Optional<Usuario> atualizarUsuario(Usuario usuario) {

        if(usuarioRepository.findById(usuario.getId()).isPresent()) {

            Optional<Usuario> buscaUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());

            if ( (buscaUsuario.isPresent()) && ( buscaUsuario.get().getId() != usuario.getId()))
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Usuário já existe!", null);

            usuario.setSenha(criptografarSenha(usuario.getSenha()));

            return Optional.ofNullable(usuarioRepository.save(usuario));

        }

        return Optional.empty();

    }

    public Optional<UsuarioLogin> autenticarUsuario(Optional<UsuarioLogin> usuarioLogin) {

        Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

        if (usuario.isPresent()) {

            if (compararSenhas(usuarioLogin.get().getSenha(), usuario.get().getSenha())) {

                usuarioLogin.get().setId(usuario.get().getId());
                usuarioLogin.get().setNome(usuario.get().getNome());
                usuarioLogin.get().setFoto(usuario.get().getFoto());
                usuarioLogin.get().setToken(gerarBasicToken(usuarioLogin.get().getUsuario(),        usuarioLogin.get().getSenha()));
                usuarioLogin.get().setSenha(usuario.get().getSenha());

                return usuarioLogin;

            }
        }

        return Optional.empty();

    }

    private String criptografarSenha(String senha) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(senha);

    }

    private boolean compararSenhas(String senhaDigitada, String senhaBanco) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(senhaDigitada, senhaBanco);

    }

    private String gerarBasicToken(String usuario, String senha) {

        String token = usuario + ":" + senha;
        byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(tokenBase64);

    }

}

/* Observe que foram criados os Métodos cadastrarUsuario() e atualizarUsuario(), que criptografam a senha e impedem a duplicação do
usuário no Banco de dados. O Método autenticarUsuario(), além de autenticar o usuário no sistema, ele compara a senha digitada pelo
usuário com a senha persistida no Banco de dados e gera o Token do usuário.
Foram criados ainda 3 Métodos auxiliares: criptografarSenha(), compararSenhas() e gerarBasicToken(), para realizar funções
específicas na Classe de Serviço.

PONTOS DE ATENÇÃO:
--> O cadastro de um novo usuário no sistema necessita ser validado no Banco de dados. Caso o usuário já exista,
a aplicação não deve permitir que ele seja criado novamente, pois um usuário duplicado no sistema ocasionará um erro HTTP Status 500.

--> ao atualizar um usuário é importante que seja validado novamente a criptografia da senha e o usuário (e-mail).
Caso não seja validado ocasionará um problema ao tentar autenticar pelo front-end da aplicação.

--> Ao utilizar o Método autenticarUsuario, certifique que todos os Atributos do Objeto usuarioLogin sejam preenchidos com os
dados recuperados do Banco de dados, pois o mesmo será utilizado pelo front-end da aplicação. */