package com.generation.blogpessoal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.blogpessoal.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

    public Optional<Usuario> findByUsuario(String usuario);

    public List<Usuario> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);
}
/* Instrução SQL equivalente: SELECT * FROM tb_usuario WHERE usuario = "usuario"; */


/* A instrução FROM tb_usuario será inserida pelo JPA ao checar o nome da tabela gerada pela Classe Usuario. */


