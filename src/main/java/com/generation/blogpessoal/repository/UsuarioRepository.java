package com.generation.blogpessoal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.generation.blogpessoal.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    public Optional<Usuario> findByUsuario(String usuario);
/* Instrução SQL equivalente: SELECT * FROM tb_usuario WHERE usuario = "usuario"; */


/* A instrução FROM tb_usuario será inserida pelo JPA ao checar o nome da tabela gerada pela Classe Usuario. */


}