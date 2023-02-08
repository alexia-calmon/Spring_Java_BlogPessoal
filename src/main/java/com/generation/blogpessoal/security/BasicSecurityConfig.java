/* A Classe BasicSecurityConfig é utilizada para sobrescrever a configuração padrão da Spring Security.
A Classe BasicSecurityConfig possui 2 anotações:
@Configuration: indica que a Classe é do tipo configuração, ou seja, define uma Classe como fonte de definições de Beans,
além de ser uma das anotações essenciais ao utilizar uma configuração baseada em Java.
@EnableWebSecurity: habilita a segurança de forma Global (toda a aplicação) e sobrescreve os Métodos que irão redefinir
as regras de Segurança da sua aplicação. Beans definidos como @Bean podem ser utilizados para definir as regras de segurança.
Beans são do que Classes que você irá escrever as regras de funcionamento da sua aplicação, que poderão ser utilizadas em qualquer
Classe, diferente da Injeção de Dependência criada pela anotação @Autowired, que só permite o uso dentro da Classe em que foi criada. */

package com.generation.blogpessoal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class BasicSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable()
                .cors();

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/usuarios/logar").permitAll()
                        .requestMatchers("/usuarios/cadastrar").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .anyRequest().authenticated())
                .httpBasic();

        return http.build();

    }

}
/* O Método passwordEncoder() , indica ao Spring Security que a aplicação está baseada em um modelo de criptografia.
Para esta aplicação estamos utilizando o modelo BCryptPasswordEncoder(), que se trata de um algoritmo de criptografia do tipo hash.
Vale ressaltar que esta criptografia está sendo esperada para analisar a senha que estamos guardando no Banco de dados.
Hash é um algoritmo que transforma dados de comprimento variável em dados de comprimento fixo codificados.

O Método authenticationManager(AuthenticationConfiguration authenticationConfiguration), implementa a confguração de autenticação.
Este Método utiliza o Método authenticationConfiguration.getAuthenticationManager() para procurar uma implementação da Interface
UserDetailsService e utilizá-la para identificar se o usuário é válido ou não. Em nosso projeto Blog Pessoal, será utilizada a
Classe UserDetailsServiceImpl, que valida o usuário no Banco de dados.

O Método SecurityFilterChain filterChain(HttpSecurity http), estamos informando ao Spring que a configuração padrão da Spring Security
será substituída por uma nova configuração. Nesta configuração iremos customizar a autenticação da aplicação desabilitando o
formulário de login e habilitando a autenticação via http. */