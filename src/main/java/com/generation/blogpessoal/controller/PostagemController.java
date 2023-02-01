package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
/* Define que a Classe é do tipo RestController e receberá requisições compostas por URL (endpoint), Verbo (método HTTP) e Corpo da Requisição (Request Body), objeto
que contém os dados que serão persistidos no banco de dados. Nem toda requisição enviará dados no Request Body.
Ao receber a requisição, a classe Controladora responserá com: um código de status HTTP pertinente a operação realizada e o resultado do processamento
((Objetos de uma Classe, por exemplo) inserido diretamente no corpo da resposta (Response Body) */

@RequestMapping("/postagens")
/* É usada para mapear as solicitações para os métodos da classe controladora PostagemController (definir a URL padrão do Recurso (/postagens)).
Ao digitar a URL do servidor seguida da URL do Recurso (http://localhost:8080/postagens),
o Spring envia a requisição para a Classe responsável pelo Recurso associado à este endereço. */

@CrossOrigin(origins = "*", allowedHeaders = "*")
/* Indica que a classe controladora permitirá o recebimento de requsições realizadas de fora do domínio (localhost ou heroku) ao qual ela pertence.
É essencial para que o front-end tenha acesso à aplicação (construir a API). Além de liberar todas as origens das requisições (parâmetro origins), a anotação
libera também os Cabeçalhos das Requisições (allowedHeaders), que trazem infos essenciais para o correto funcionamento da aplicação.
Em produção, recomenda-se substituir o * pelo endereço do deploy do front. */
public class PostagemController {
    @Autowired
    /* Injeção de Dependência - É a implementação utilizada pelo Spring para aplicar a Inversão de Controle (IoC) quando necessário. Ela define quais Classes
    serão instanciadas e em quais lugares serão Injetadas quando necessário. */
    private PostagemRepository postagemRepository;

/*A classe Controladora cria um ponto de injeção da Interface PostagemRepository e quando houver necessidade, o Spring cria um novo Objeto da Classe Postagem
a partir da Interface PostagemRepository, permitindo o uso de todos os Métodos da Interface (Padrão ou Personalizados), sem a necessidade de criar
Métodos Construtores na Classe Model ou Criar/Instaciar Objetos de forma manual. Transferimos a responsabilidade disso, para o Spring e focamos nas Requisições. */


    /*Vamos implementar o Método getAll() na Classe Postagem Controller, que retornará todos os Objetos da Classe Postagem persistidos no Banco de dados.
    Traçando um paralelo com o MySQL, seria o equivalente a instrução: SELECT * FROM tb_postagens; */
    @GetMapping
    /* A anotação @GetMapping mapeia todas as Requisições HTTP GET, enviadas para um endereço específico, chamado endpoint, dentro do Recurso Postagem,
    para um Método específico que responderá a requisição, ou seja, ele indica que o Método getAll(), responderá a todas as requisições do tipo HTTP GET,
    enviadas no endereço http://localhost:8080/postagens/. */

    public ResponseEntity<List<Postagem>> getAll() {
        /* O Método getAll() será do tipo ResponseEntity pq ele responderá a Requisição HTTP (HTTP Request), com uma Resposta HTTP (HTTP Response).
        <list<Postagem>>: O Método além de retornar um Objeto da Classe ResponseEntity (OK=200), no parâmetro body (Corpo da Resposta),
        será retornado um Objeto da Classe List (Collection), contendo todos os Objetos da Classe postagem persistidos no Banco de Dados, na tabela tb_postagens.
        Usamos aqui nessa linha 46, o recurso Java Generics, que além de simplificar o retorno do Objeto da Classe List, dispensa o uso do casting (mudança de tipos).
        Na definição do Método, foram usados os símbolos, onde T é o tipo do objeto que será retornado no Corpo da Resposta. */

        return ResponseEntity.ok(postagemRepository.findAll());
        /* Executa o Método findAll() (Método padrão da Interface JpaRepository), que retornará todos os Objetos da Classe Postagem persistidos no Banco de dados
        (<List<Postagem>>. Como a List sempre será gerada (vazia ou não), o Método sempre retornará o Status 200=OK */
    }
}