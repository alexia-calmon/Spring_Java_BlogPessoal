package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    @Autowired
    private TemaRepository temaRepository;
    /* Para termos acesso aos Métodos das Classes Tema e TemaController, precisamos inserir uma uma Injeção de Dependência dio Recurso Tema,
     logo abaixo da uma Injeção de Dependência do Recurso Postagem. */


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

    /*Vamos implementar o Método getById(Long id) na Classe Postagem Controller, que retornará um Objeto específico persistido no Banco de dados, identificado
    pelo id (Identificador único do Objeto). Traçando um paralelo com o MySQL, seria o equivalente a instrução: SELECT * FROM tb_postagens where id = id;
    Para processar o Método findById(Long id), vamos utilizar dois recursos da Linguagem Java, que tornam o código mais limpo e assertivo:
    São os recursos Optional e Expressões Lambda. */
    @GetMapping("/{id}")
    /*A anotação @GetMapping("/{id}") mapeia todas as Requisições HTTP GET, enviadas para um endereço específico (Endpoint), dentro do Recurso Postagem,
    para um Método específico que responderá as requisições, ou seja, ele indica que o Método getById( Long id ), responderá a todas as requisições
    do tipo HTTP GET, enviadas no endereço http://localhost:8080/postagens/id, onde id é uma Variável de Caminho (Path Variable), que receberá o id da
    Postagem que será Consultada. */

    public ResponseEntity<Postagem> getById(@PathVariable Long id) {
        /*O Método getById(@PathVariable Long id) será do tipo ResponseEntity porque ele responderá Requisições HTTP (HTTP Request), com uma Resposta HTTP
        (HTTP Response). Observe que o Método possui um parâmetro do tipo Long, chamado id.
        @PathVariable Long id: Esta anotação insere o valor enviado no endereço do endpoint, na Variável de Caminho {id}, no parâmetro do Método getById( Long id )
        Ex.: http://localhost:8080/postagens/1 (o parâmetro Long id, do Método getById( Long id ), receberá o valor 1 (Id que será procurado em tb_postagens))
        <Postagem> O Método além de retornar um objeto da Classe ResponseEntity (OK🡪200), no parâmetro Body (Corpo da Resposta), será retornado Um Objeto da
        Classe Postagem, apenas e somente se o Objeto procurado for encontrado no Banco de dados, na tabela tb_postagens. Nesta linha também foi utilizado o
        recurso Java Generics para simplificar o retorno do Objeto. */

        return postagemRepository.findById(id)
                /* Retorna a execução do Método findById(id), que é um Método padrão da Interface JpaRepository. O Método retornará um Objeto da Classe Postagem
                persistido no Banco de dados (source: imgur.com), caso ele seja encontrado a partir do parâmetro Long id. Caso contrário, será retornado um Objeto Nulo.*/
                .map(resposta -> ResponseEntity.ok(resposta))
                /* Se o Objeto da Classe Postagem for econtrado, o Método map (Optional), mapeia no Objeto resp o Objeto Postagem retornado pelo Método findById(id)),
                 insere o Objeto mapeado no Corpo da Resposta do Método ResponseEntity.ok(resp); e retorna o HTTP Status OK🡪200. */
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
                /*Se o Objeto Postagem não for encontrado (Nulo), será retornado o HTTP Status NOT FOUND 🡪 404 (Não Encontrado!).
                O Método build() constrói a Resposta com o HTTP Status retornado.
                 */
    }

    @GetMapping("/titulo/{titulo}")
    /*A anotação @GetMapping indica que o Método getAll(), responderá a todas as requisições do tipo HTTP GET, enviadas no endereço
     http://localhost:8080/postagens/titulo/postagem.

     */
    public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
        /*<List<Postagem>>: Além de retornar um objeto da Classe ResponseEntity (OK=200) no body, retornará um Objeto da Classe List (Collection),
        que contém todos os objetos da classe postagem persistidos no BDD na tabela tb_postagem e o atributo tenha a String enviada como parâmetro. */

        return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
        /* Executa o Método findAllByTituloContainingIgnoreCase(String titulo) (Método personalizado, criado na Interface PostagemRepository), e exibe o resultado
        (<list<Postagem>>, no corpo da Resposta. Como a List sempre será gerada sendo vazia ou não, o Método sempre vai retornar Status 200=OK. */
    }

    /*Vamos implementar o Método post(Postagem postagem) na Classe Postagem Controller. Traçando um paralelo com o MySQL, seria o equivalente a
    instrução: INSERT INTO tb_postagens (titulo, texto, data) VALUES ("Título", "Texto", CURRENT_TIMESTAMP()); */
    @PostMapping
    /*a anotação @PostMapping indica que o Método post(Postagem postagem), responderá a todas as requisições do tipo HTTP POST,
    enviadas no endereço http://localhost:8080/postagens*/

    public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem) {
        /*O Método ResponseEntity<Postagem>post(@Valid @RequestBody postagem postagem) será do tipo ResponseEntity porque ele responderá
        Requisições HTTP com uma Resposta HTTP. O Método possui um parâmetro, que é um Objeto da Classe Postagem, chamado postagem.

        @Valid: Valida o Objeto postagem enviado no Corpo da Requisição, conforme as regras definidas no Model postagem (@NotNull, @NotBlank, @Size, etc)

        @RequestBody Postagem postagem: Recebe o Objeto do tipo Postagem enviado no Corpo da Requisição e insere no parâmetro Postagem no método post.*/

        if (temaRepository.existsById(postagem.getTema().getId()))
            /* Através do Método existsById(Long id), da Interface TemaRepository (Herança da Interface JPA), checamos se o id passado no Objeto tema, da Classe Tema, inserido no Objeto postagem, da Classe Postagem, existe.
            Para obter o id do tema, utilizamos os Métodos get das 2 Classes: postagem.getTema().getId() */

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(postagemRepository.save(postagem));
            /* Executa o Método padrão da Interface JpaRepository (save(postagem)), se o Objeto tema existir, e retorna o HTTP Status CREATED🡪201
             se o Objeto foi persistido no Banco de dados. */

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postagemRepository.save(postagem));
        /*Se o Objeto tema não for encontrado pelo Método existsById(Long id), será retornado o HTTP Status BAD REQUEST 🡪 400.
        O Método build() constrói a Resposta com o HTTP Status retornado. */
    }
    /*Vamos implementar o Método put(Postagem postagem) na Classe Postagem Controller. Observe que ele é muito parecido com o Método post.
    Traçando um paralelo com o MySQL, seria o equivalente a instrução:
    UPDATE tb_postagens SET titulo = "titulo", texto = "texto", data = CURRENT_TIMESTAMP() WHERE id = id; */

    @PutMapping
    /* A anotação @PutMapping indica que o Método put(Postagem postagem), responderá a todas as requisições do tipo HTTP PUT,
    enviadas no endereço http://localhost:8080/postagens */

    public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem) {
        if (postagemRepository.existsById(postagem.getId())) {
            /* Através do Método existsById(Long id), da Interface PostagemRepository (Herança da Interface JPA), checamos se o id passado no
            Objeto postagem, da Classe Postagem, existe. Caso o Objeto não exista, não é possível atualizar. */

            if (temaRepository.existsById(postagem.getTema().getId()))
                /* Através do Método existsById(Long id), da Interface TemaRepository (Herança da Interface JPA), checamos se o id passado no
                Objeto tema, da Classe Tema, inserido no Objeto postagem, da Classe Postagem, existe.
                Para obter o id do tema, utilizamos os Métodos get das 2 Classes: postagem.getTema().getId() */

                return ResponseEntity.status(HttpStatus.OK)
                        /* Executa o Método padrão da Interface JpaRepository (save(postagem)), se o Objeto tema existir,
                        e retorna o HTTP Status OK=200 se o Objeto foi atualizado no Banco de dados. */
                        .body(postagemRepository.save(postagem));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            /* Se o Objeto tema não for encontrado pelo Método existsById(Long id), será retornado o HTTP Status BAD REQUEST = 400.
            O Método build() constrói a Resposta com o HTTP Status retornado. */
        }


        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        /* Se o Objeto Postagem não for encontrado pelo Método existsById(Long id), será retornado o HTTP Status NOT FOUND = 404 (Não Encontrado!),
        indicando que a Postagem não existe. O Método build() constrói a Resposta com o HTTP Status retornado. */
    }

    /* Vamos implementar o Método delete(Long id) na Classe Postagem Controller. Traçando um paralelo com o MySQL,
    seria o equivalente a instrução: DELETE FROM tb_postagens WHERE id = id;. */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    /*Indica que o Método delete(Long id), terá uma ResponseStatus específica. Quando a Resposta da Requisição for positiva, será retornado o
    HTTP Status NO_CONTENT = 204, ao invés do HTTP Status OK=200 como resposta padrão. */

    @DeleteMapping("/{id}")
    /* Mapeia todas as Requisições HTTP DELETE enviadas para um endereço específico (EndPoint), dentro do Recurso Postagem, para um Método específico
    que responderá as requisições, ou seja, ele indica que o Método delete (Long id), responderá a todas as requisições do tipo HTTP DELETE,
    enviadas no endereço http://localhost:8080/postagens/id, onde id é uma Variável de Caminho (Path Variable),
     que receberá o id da Postagem que será Deletada. */

    public void delete(@PathVariable Long id) {
        /* O Método void delete(@PathVariable Long id) será do tipo void porque ele responda Requisições HTTP (HTTP Request), ao deletar uma
        Postagem ela deixa de existir, logo não tem nenhum tipo de retorno. Como configuramos a anotação @ResponseStatus, ele devolverá uma
        Resposta HTTP NO_CONTENT 🡪 204, indicando que o Objeto deletado não existe mais. Observe que o Método possui um parâmetro do tipo Long, chamado id.

        @PathVariable Long id: Esta anotação insere o valor enviado no endereço do endpoint, na Variável de Caminho {id},
        no parâmetro do Método delete( Long id ); */

        Optional<Postagem> postagem = postagemRepository.findById(id);
/*Cria um Objeto Optional da Classe Postagem chamado postagem, que receberá o resultado do Método findById(id).
Como o Método pode retornar um Objeto Nulo, utilizaremos o Optional para evitar o erro NullPointerException. Ao ao invés de utilizarmos o map
com as Expressões Lambda, utilizaremos o Optional. */

        if (postagem.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        /* Através da estrutura condicional if, checamos se o Objeto postagem está vazio (postagem.isEmpty()). Se estiver, geramos um
             HTTP Status NOT FOUND 🡪 404 (Não Encontrado!) e como estamos utilizando um Objeto da Classe ResponseStatusException
             (throw new ResponseStatusException(HttpStatus.NOT_FOUND);), as próximas linhas do Método serão ignoradas.  */

        postagemRepository.deleteById(id);
        /*Executa o Método padrão da Interface JpaRepository deleteById(Long id) e retorna o HTTP Status NO_CONTENT 🡪 204, HTTP Status padrão do Método*/
    }
}