/* A Classe UserDetailsImpl implementa a Interface UserDetails, que tem como principal funcionalidade fornecer as informações básicas
 do usuário para o Spring Security (Usuário, Senha, Direitos de acesso e as Restrições da conta).
    Após a autenticação (login) do usuário no sistema, a Classe de Serviço UserDetailsServiceImpl instanciará um novo Objeto da Classe
    UserDetailsImpl com os respectivos Atributos preenchidos com os dados do usuário autenticado (usuario e senha).
    Este Objeto também conterá todos os Métodos herdados da Interface UserDetails, que consultam os Direitos de acesso (roles) e implementam as restrições da conta do usuário.



 */

package com.generation.blogpessoal.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generation.blogpessoal.model.Usuario;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private String userName;
    private String password;

    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(Usuario user) {
        this.userName = user.getUsuario();
        this.password = user.getSenha();
    }

    public UserDetailsImpl() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;
    }

    @Override
    public String getPassword() {

        return password;
    }

    @Override
    public String getUsername() {

        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }
    /* isAccountNonExpired(): Indica se a conta do usuário expirou. Uma conta expirada não pode ser autenticada (return false). */

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }
    /* isAccountNonLocked(): Indica se o usuário está bloqueado ou desbloqueado. Um usuário bloqueado não pode ser autenticado (return false). */

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }
/* isCredentialsNonExpired(): Indica se a senha do usuário expirou. Senha expirada não pode ser autenticada (return false). */

    @Override
    public boolean isEnabled() {

        return true;
    }
/* isEnabled(): Indica se o usuário está ativo. Um usuário ativo não pode ser autenticado (return false). */
}

/* Observe que o Método getAuthorities(), que retorna a lista com os direitos de acesso do usuário, sempre retornará uma List vazia,
porquê este Atributo não pode ser Nulo. Com o objetivo de simplificar a nossa implementação,
todo o Usuário autenticado terá todos os direitos de acesso sobre a aplicação. */