/* A Classe UsuarioLogin é responsável por definir que o cliente ao tentar autenticar (fazer login) no sistema,
forneça apenas o usuário (e-mail) e a senha. Essa Classe também pode ser definida como uma DTO (Data trasfer object),
que é uma Classe que é utilizada para transitar dados do sistema sem revelar sua Classe Model para o cliente. */

package com.generation.blogpessoal.model;

public class UsuarioLogin {

    private Long id;
    private String nome;
    private String usuario;
    private String senha;
    private String foto;
    private String token;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFoto() {
        return this.foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
