package com.generation.blogpessoal.repository;

import com.generation.blogpessoal.model.Usuario;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
/* indica que a Classe UsuarioRepositoryTest é uma Classe Spring Boot Testing. A Opção environment indica que caso a porta
principal (8080 para uso local) esteja ocupada, o Spring irá atribuir uma outra porta automaticamente. */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/* indica que o Ciclo de vida da Classe de Teste será por Classe. */
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /*  Foi injetado (@Autowired), um objeto da Interface UsuarioRepository para persistir os objetos no Banco de dados de testes. */
    @BeforeAll
    /* o Método start(), anotado com a anotação @BeforeAll, apaga todos os dados da tabela (linha 26),
    inicializa 4 objetos do tipo Usuario e insere no Banco de dados de testes através do Método .save() uma única vez
    (Lifecycle.PER_CLASS). Observe que em todos os Objetos, o Atributo id está com o valor 0L, indicando que o Atributo será preenchido
    automaticamente pelo Banco de dados (substituindo o zero pela Chave primária) e o L informa que o Atributo é um Objeto da Classe Long.  */
    void start() {

        usuarioRepository.deleteAll();

        usuarioRepository.save(new Usuario(0L, "João da Silva", "joao@email.com.br", "13465278", "https://i.imgur.com/FETvs20.jpg"));
        usuarioRepository.save(new Usuario(0L, "Manuela da Silva", "manuela@email.com.br", "13465278", "https://i.imgur.com/NtyGneo"));
        usuarioRepository.save(new Usuario(0L, "Adriana da Silva", "adriana@email.com.br", "13465278", "https://i.imgur.com/mB3VM2N.jpg"));
        usuarioRepository.save(new Usuario(0L, "Paulo Antunes", "paulo@email.com.br", "13465278", "https://i.imgur.com/JR7kUFU.jpg"));
    }

    @Test
    /* O Método deveRetornarUmUsuario() foi antotado com a anotação @Test que indica que este Método executará um teste. */

    @DisplayName("Retorna 1 usuario")
    /* A anotação @DisplayName configura uma mensagem que será exibida ao invés do nome do Método. */
    public void deveRetornarUmUsuario() {

        Optional<Usuario> usuario = usuarioRepository.findByUsuario("joao@email.com.br");
        /* o objeto usuario recebe o resultado do Método findByUsuario(). */

        assertTrue(usuario.get().getUsuario().equals("joao@email.com.br"));
        /* Através do Método de asserção assertTrue(), verifica se o usuário cujo e-mail é “joao@email.com.br” foi encontrado.
        Se o e-mail for encontrado o resultado do teste será: Aprovado!. Caso não encontre, o resultado do teste será : Falhou! */
    }

    @Test
    /* O Método deveRetornarTresUsuarios() foi antotado com a anotação @Test que indica que este Método executará um teste. */

    @DisplayName("Retorna 3 usuarios")
    /* A anotação @DisplayName configura uma mensagem que será exibida ao invés do nome do Método. */

    public void deveRetornarTresUsuarios() {
        List<Usuario> listaDeUsuarios = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
        /* O objeto listaDeUsuarios recebe o resultado do Método findAllByNomeContainingIgnoreCase(). */
        assertEquals(3, listaDeUsuarios.size());
            /* Através do Método de asserção assertEquals(), verifica se o tamanho da List é igual a 3 (quantidade de usuários
            cadastrados no Método start() cujo sobrenome é "Silva"). O Método size(), (java.util.List), retorna o tamanho da List.
            Se o tamanho da List for igual 3, o 1° teste será Aprovado! */

        assertTrue(listaDeUsuarios.get(0).getNome().equals("João da Silva"));
        assertTrue(listaDeUsuarios.get(1).getNome().equals("Manuela da Silva"));
        assertTrue(listaDeUsuarios.get(2).getNome().equals("Adriana da Silva"));

    }
        /* Nas linhas 57 a 63, através do Método de asserção AssertTrue(), verifica em cada posição da Collection List
        listaDeUsuarios se os usuários, que foram inseridos no Banco de dados através no Método start(), foram gravados na mesma sequência.
O Teste da linha 60 checará se o primeiro usuário inserido (João da Silva) está na posição 0 da List listaDeUsuarios (1ª posição da List),
O Teste da linha 61 checará se o segundo usuário (Manuela da Silva) está na posição 1 da List listaDeUsuarios (2ª posição da List).
O Teste da linha 62 checará se o terceiro usuário (Adriana da Silva) está na posição 2 da List listaDeUsuarios (3ª posição da List).
A posição na List é obtida através do Método get(int index) (java.util.List), passando como parâmetro a posição desejada. O nome do
usuário é obtido através do Método getNome() da Classe Usuario. Se os três usuários foram gravados na mesma sequência do Método start(),
os três testes serão Aprovados!

--> ATENÇÃO: Para que o Método deveRetornarTresUsuarios() seja aprovado, as 4 asserções (linhas 67 a 74)
devem ser aprovadas, caso contrário o JUnit indicará que o teste Falhou!. */

    @AfterAll
    public void end() {
        usuarioRepository.deleteAll();
    }
    /* o Método end(), anotado com a anotação @AfterAll, apaga todos os dados da tabela (linha 88),
    depois que todos os testes foram executados.  */
}


