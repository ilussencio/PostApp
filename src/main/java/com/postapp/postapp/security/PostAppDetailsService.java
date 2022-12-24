package com.postapp.postapp.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.postapp.postapp.model.Usuario;
import com.postapp.postapp.repository.UsuarioRepository;

public class PostAppDetailsService implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> opt =  usuarioRepository.findByUsername(username);
        if (opt.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não autenticado!");
        }
        System.out.println(opt.get().getPerfil());

        return new PostAppUserDetails(opt.get());
    }
}
