package com.generation.blogpessoal.controller;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;
import com.generation.blogpessoal.service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
/* A anotação @SpringBootTest indica que a Classe UsuarioControllerTest é uma Classe Spring Boot Testing.
A Opção environment indica que caso a porta principal (8080 para uso local) esteja ocupada, o Spring irá atribuir
uma outra porta automaticamente. */

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/* A anotação @TestInstance indica que o Ciclo de vida da Classe de Teste será por Classe. */
public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;
    /* Foi injetado (@Autowired), um objeto da Classe TestRestTemplate para enviar as requisições para a nossa aplicação. */

    @Autowired
    private UsuarioService usuarioService;
    /* foi injetado (@Autowired), um objeto da Classe UsuarioService para persistir os objetos no
    Banco de dados de testes com a senha criptografada. */
    @Autowired
    private UsuarioRepository usuarioRepository;
    /* Foi injetado (@Autowired), um objeto da Interface UsuarioRepository para limpar o Banco de dados de testes. */

    @BeforeAll
    void start() {
        /* o Método start(), anotado com a anotação @BeforeAll, apaga todos os dados da tabela e cria o usuário root@root.com
        para testar os Métodos protegidos por autenticação.  */

        usuarioRepository.deleteAll();

        usuarioService.cadastrarUsuario(new Usuario(0L,
                "Root", "root@root.com", "rootroot", " "));
    }

    @Test
    /* o Método deveCriarUmUsuario() foi antotado com a anotação @Test que indica que este Método executará um teste. */

    @DisplayName("Cadastrar Um Usuário")
    /* a anotação @DisplayName configura uma mensagem que será exibida ao invés do nome do Método. */
    public void deveCriarUmUsuario() {
        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L,
                /* foi criado um objeto da Classe HttpEntity chamado requisicao, recebendo um objeto da Classe Usuario.
                Nesta etapa, o processo é equivalente ao que o Postman faz em uma requisição do tipo POST: Transforma os Atributos
                num objeto da Classe Usuario, que será enviado no corpo da requisição (Request Body). */

                "Paulo Antunes", "paulo_antunes@email.com.br", "13465278", "https://i.imgur.com/JR7kUFU.jpg"));
        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);
        /*A Requisição HTTP será enviada através do Método exchange() da Classe TestRestTemplate e a Resposta da Requisição (Response)
        será recebida pelo objeto resposta do tipo ResponseEntity. Para enviar a requisição, o será necessário passar 4 parâmetros:
        A URI: Endereço do endpoint (/usuarios/cadastrar);
        O Método HTTP: Neste exemplo o Método POST;
        O Objeto HttpEntity: Neste exemplo o objeto requisicao, que contém o objeto da Classe Usuario;
        O conteúdo esperado no Corpo da Resposta (Response Body): Neste exemplo será do tipo Usuario (Usuario.class). */

        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
        /* Através do Método de asserção AssertEquals(), checaremos se a resposta da requisição (Response), é a resposta esperada
        (CREATED 🡪 201). Para obter o status da resposta vamos utilizar o Método getStatusCode() da Classe ResponseEntity. */

        assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
/* Através do Método de asserção AssertEquals(), checaremos se o nome e o usuário(e-mail) enviados na requisição foram
persistidos no Banco de Dados. Através do Método getBody() faremos o acesso aos objetos requisição e resposta, que estão no
corpo (body) tanto da requisição quanto da resposta, e através dos Métodos getNome() e getUsuario() faremos o acesso aos
Atributos que serão comparados. */
    }

    @Test
    @DisplayName("Não deve permitir duplicação do Usuário")
    public void naoDeveDuplicarUsuario() {

        usuarioService.cadastrarUsuario(new Usuario(0L,
                "Maria da Silva", "maria_silva@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg"));
        /* Através do Método cadastrarUsuario() da Classe UsuarioService, foi persistido um Objeto da Classe Usuario
        no Banco de dados (Maria da Silva). */

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L,
                "Maria da Silva", "maria_silva@email.com.br", "13465278", "https://i.imgur.com/T12NIp9.jpg"));
        /* Foi criado um objeto HttpEntity chamado requisicao, recebendo um objeto da Classe Usuario contendo
        os mesmos dados do objeto persistido na linha 60 (Maria da Silva). */

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);
        /* A Requisição HTTP será enviada através do Método exchange() da Classe TestRestTemplate e a Resposta da Requisição
        (Response) será recebida pelo objeto resposta do tipo ResponseEntity. Para enviar a requisição, o será necessário passar 4 parâmetros:
        A URI: Endereço do endpoint (/usuarios/cadastrar);
        O Método HTTP: Neste exemplo o Método POST;
        O Objeto HttpEntity: Neste exemplo o objeto requisicao, que contém o objeto da Classe Usuario;
        O conteúdo esperado no Corpo da Resposta (Response Body): Neste exemplo será do tipo Usuario (Usuario.class). */


        assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
        /* Através do Método de asserção AssertEquals(), checaremos se a resposta da requisição (Response), é a resposta esperada
        (BAD_REQUEST 🡪 400). Para obter o status da resposta vamos utilizar o Método getStatusCode() da Classe ResponseEntity. */
    }
    /* Observe que neste Método temos o objetivo de testar o Erro! (Usuário Duplicado) e não a persistência dos dados.
    Observe que enviamos o mesmo objeto 2 vezes e verificamos se o aplicativo rejeita a persistência do mesmo objeto
    pela segunda vez (BAD REQUEST).
    Como o teste tem por objetivo checar se está duplicando usuários no Banco de dados, ao invés de checarmos se o objeto foi
    persistido (CREATE 🡪 201), checaremos se ele não foi persistido (BAD_REQUEST 🡪 400). Se retornar o Status 400, o teste será aprovado! */

    @Test
    @DisplayName("Atualizar Um Usuário")
    public void deveAtualizarUmUsuario() {

        Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L,
                "Juliana Andrews", "juliana_andrews@email.com.br", "juliana123", "https://i.imgur.com/yDRVeK7.jpg"));
/* foi criado um Objeto Optional, do tipo Usuario, chamado usuarioCreate, para armazenar o resultado da persistência
 de um Objeto da Classe Usuario no Banco de dados, através do Método cadastrarUsuario() da Classe UsuarioService. */

        Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(),
                "Juliana Andrews Ramos", "juliana_ramos@email.com.br", "juliana123", "https://i.imgur.com/yDRVeK7.jpg");
        /* foi criado um Objeto do tipo Usuario, chamado usuarioUpdate, que será utilizado para atualizar os dados persistidos no Objeto usuarioCreate. */

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);
        /* foi criado um objeto HttpEntity chamado requisicao, recebendo o objeto da Classe Usuario chamado usuarioUpdate.
        Nesta etapa, o processo é equivalente ao que o Postman faz em uma requisição do tipo PUT: Transforma os Atributos
        num objeto da Classe Usuario, que será enviado no corpo da requisição (Request Body).  */

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                /* a Requisição HTTP será enviada através do Método exchange() da Classe TestRestTemplate e a
                Resposta da Requisição (Response) será recebida pelo objeto resposta do tipo ResponseEntity. Para enviar a requisição,
                o será necessário passar 4 parâmetros:
                A URI: Endereço do endpoint (/usuarios/atualizar);
                O Método HTTP: Neste exemplo o Método PUT;
                O Objeto HttpEntity: Neste exemplo o objeto requisicao, que contém o objeto da Classe Usuario;
                O conteúdo esperado no Corpo da Resposta (Response Body): Neste exemplo será do tipo Usuario (Usuario.class).  */

                .withBasicAuth("root@root.com", "rootroot")
                /* como o Blog Pessoal está com o Spring Security habilitado com autenticação do tipo Http Basic, o Objeto
                testRestTemplate dos endpoints que exigem autenticação, deverá efetuar o login com um usuário e uma senha válida para realizar
                os testes. Para autenticar o usuário e a senha utilizaremos o Método withBasicAuth(username, password) da Classe TestRestTemplate.
                Vamos utilizar o usuário root@root.com, que foi criado no Método start() para autenticar o nosso teste. */

                .exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
                /* Através do Método de asserção AssertEquals(), checaremos se a resposta da requisição (Response),
                é a resposta esperada (OK 🡪 200). Para obter o status da resposta vamos utilizar o Método getStatusCode()
                da Classe ResponseEntity.*/

        assertEquals(corpoRequisicao.getBody().getNome(), corpoResposta.getBody().getNome());
        assertEquals(corpoRequisicao.getBody().getUsuario(), corpoResposta.getBody().getUsuario());
                /* Através do Método de asserção AssertEquals(), checaremos se o nome e o usuário(e-mail) enviados na
                requisição usuarioUpdate foram persistidos no Banco de Dados. Através do Método getBody() faremos o acesso
                aos objetos usuarioUpdate e resposta que estão no corpo (body) tanto da requisição quanto da resposta, e através dos
                Métodos getNome() e getUsuario() faremos o acesso aos Atributos que serão comparados.

                --> ATENÇÃO: Para que o Método deveAtualizarUmUsuario() seja aprovado, os 3 testes (linhas 90 a 92) devem ser aprovados,
                caso contrário o JUnit indicará que o teste Falhou! */
    }

    @Test
    @DisplayName("Listar todos os Usuários")
    public void deveMostrarTodosOsUsuarios() {

        usuarioService.cadastrarUsuario(new Usuario(0L,
                "Sabrina Sanches", "sabrina_sanches@email.com.br", "sabrina123", "https://i.imgur.com/5M2p5Wb.jpg"));

        usuarioService.cadastrarUsuario(new Usuario(0L,
                "Ricardo Marques", "ricardo_marques@email.com.br", "ricardo123", "https://i.imgur.com/Sk5SjWE.jpg"));
        /* foram persistidos dois Objetos da Classe Usuario no Banco de dados, através do Método cadastrarUsuario() da Classe UsuarioService. */

        ResponseEntity<String> resposta = testRestTemplate
                /* a Requisição HTTP será enviada através do Método exchange() da Classe TestRestTemplate e a Resposta da Requisição (Response)
                será recebida pelo objeto resposta do tipo ResponseEntity. Para enviar a requisição, o será necessário passar 4 parâmetros:
                A URI: Endereço do endpoint (/usuarios/all);
                O Método HTTP: Neste exemplo o Método GET;
                O Objeto HttpEntity: O objeto será nulo (null). Requisições do tipo GET não enviam Objeto no corpo da requisição;
                O conteúdo esperado no Corpo da Resposta (Response Body): Neste exemplo como o objeto da requisição é nulo,
                a resposta esperada será do tipo String (String.class). */

                .withBasicAuth("root@root.com", "rootroot")
                /* Observe que na linha 111, como o Blog Pessoal está com o Spring Security habilitado com autenticação do tipo Http Basic,
                o Objeto testRestTemplate dos endpoints que exigem autenticação, deverá efetuar o login com um usuário e uma senha válida
                para realizar os testes. Para autenticar o usuário e a senha utilizaremos o Método withBasicAuth(username, password) da
                Classe TestRestTemplate. Vamos utilizar o usuário root@root.com, que foi criado no Método start() para autenticar o nosso teste. */

                .exchange("/usuarios/all", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }
}
/* A através do Método de asserção AssertEquals(), checaremos se a resposta da requisição (Response), é a resposta esperada
    (OK 🡪 200). Para obter o status da resposta vamos utilizar o Método getStatusCode() da Classe ResponseEntity. */