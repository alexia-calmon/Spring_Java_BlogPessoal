package com.generation.blogpessoal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne
     /* A anotação @ManyToOne indica que a Classe Postagem será o lado N:1 e terá um Objeto da Classe Tema, que no modelo Relacional será a
    Chave Estrangeira na Tabela tb_postagens (tema_id). */
    @JsonIgnoreProperties("postagem")
    /* indica que uma parte do JSON será ignorado, ou seja, como a Relação entre as Classes será do tipo Bidirecional, ao listar o
    Objeto Postagem numa consulta, por exemplo, o Objeto Tema, que será criado na linha 39, será exibido como um "Sub Objeto" do Objeto
    Postagem, como mostra a figura abaixo, devido ao Relacionamento que foi criado. */
    private Tema tema;
    /*Será criado um Objeto da Classe Tema, que receberá os dados do Tema associado ao Objeto da Classe Postagem.
    Este Objeto representa a Chave Estrangeira da Tabela tb_postagens (tema_id). */


    /*Getters and setters - Acessar às variáveis de qualquer lugar do projeto, pois são privadas*/
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getData() {
        return this.data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

}
