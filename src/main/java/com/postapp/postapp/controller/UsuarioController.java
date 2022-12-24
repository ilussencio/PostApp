package com.postapp.postapp.controller;

import com.postapp.postapp.model.Categoria;
import com.postapp.postapp.model.Postagem;
import com.postapp.postapp.model.Usuario;
import com.postapp.postapp.repository.CategoriaRepository;
import com.postapp.postapp.repository.PostagemRepository;
import com.postapp.postapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PostagemRepository postagemRepository;
    @Autowired
    CategoriaRepository categoriaRepository;

    @GetMapping("/{username}")
    public String postsUser(@PathVariable String username, Model model, HttpServletRequest request) {
        Optional<Usuario> opt = usuarioRepository.findByUsername(username);
        Usuario usuario = opt.get();
        List<Postagem> postagems = postagemRepository.findBy(usuario);
        model.addAttribute("usuario", usuario);
        model.addAttribute("postagems", postagems);

        try {
            Principal principal = request.getUserPrincipal();
            Usuario usr = usuarioRepository.findByUsername(principal.getName()).get();
            model.addAttribute("userlog", usr);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return "post/posts_for_user";
    }

    @GetMapping("/find/{busca}")
    public ResponseEntity<Boolean> findEmail(@PathVariable String busca) {
        Optional<Usuario> optEmail = usuarioRepository.findByEmail(busca);
        Optional<Usuario> optUsername = usuarioRepository.findByUsername(busca);
        if(optEmail.isEmpty() && optUsername.isEmpty()) {
            return ResponseEntity.ok().body(false);
        }
        return ResponseEntity.ok().body(true);
    }

}
