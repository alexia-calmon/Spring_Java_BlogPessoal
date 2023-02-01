package com.generation.blogpessoal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
/*Criar a entidade da tabela

/*Setando o nome da tabela*/
@Table(name = "tb_postagem")
public class Postagem {
    @Id

    /*GenerationType.(...) identifica que é uma chave primária (mesma coisa que autoincrement)*/
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    //Não pode ser vazia e nem nula -- obrigatório ser preenchido
    @Size(min = 4, max = 100, message = "O atributo título deve ter no mínimo 5 e no máximo 100 caracteres!")
    //seta o tamanho com os parâmetros mín e máx e caso não atenda ao requisito, aparece a mensagem configurada

    private String titulo;
    @NotBlank
    @Size(min = 10, max = 1000, message = "O atributo título deve ter no mínimo 10 e no máximo 1000 caracteres!")

    private String texto;

    @UpdateTimestamp
    //Seta a hora exata de acordo com o computador e coloca na postagem - tipo Facebook
    private LocalDateTime data;


    /*Getters and setters - Acessar às variáveis de qualquer lugar do projeto, pois são privadas*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}
