package com.postapp.postapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import com.postapp.postapp.repository.UsuarioRepository;

import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class PostAppSecurity {
    @Bean
    public UserDetailsService userDetailsService() {
        return new PostAppDetailsService();
    }
  
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }
  
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
      authProvider.setUserDetailsService(userDetailsService());
      authProvider.setPasswordEncoder(passwordEncoder());
      return authProvider;
    }
  
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.authorizeHttpRequests(authorize -> authorize
          .antMatchers("/","/home","/post", "/post/*","/css","/css/*","/js","/js/*","/img","/img/*","/comentario/get","/get","/get/*","/comentario/get/*","/cadastro","/cadastro/*","/usuario","/usuario/*", "/usuario/find/*","/busca","/busca/*").permitAll()
          .anyRequest().authenticated())
          .formLogin(form -> form.loginPage("/login").permitAll())
          .logout().logoutSuccessUrl("/home");
          
      return http.build();
    }
}
