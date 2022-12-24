package com.postapp.postapp.controller;

import com.postapp.postapp.model.Categoria;
import com.postapp.postapp.model.Comentario;
import com.postapp.postapp.model.Postagem;
import com.postapp.postapp.model.Usuario;
import com.postapp.postapp.repository.CategoriaRepository;
import com.postapp.postapp.repository.ComentarioRepository;
import com.postapp.postapp.repository.PostagemRepository;
import com.postapp.postapp.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/post")
public class PostagemController {
    @Autowired
    PostagemRepository postagemRepository;

    @Autowired
    ComentarioRepository comentarioRepository;

    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/{id}")
    public String ListOnePost(@PathVariable int id, Model model, HttpServletRequest request) {
        Postagem post = postagemRepository.findById(id);
        List<Comentario> list = comentarioRepository.findBy(post);
        List<Categoria> categorias = categoriaRepository.findBy(post);
        List<Postagem> postagems = postagemRepository.findBy(post.getId_usuario(),  3, post.getId());
        List<Postagem> sugestao = postagemRepository.findBy(categorias, 3);

        model.addAttribute("post", post);
        model.addAttribute("usuario", post.getId_usuario());
        model.addAttribute("comentario", list);
        model.addAttribute("categorias", categorias);
        model.addAttribute("postagems", postagems);
        model.addAttribute("sugestao", sugestao);
        try {
            Principal principal = request.getUserPrincipal();
            Usuario usuario = usuarioRepository.findByUsername(principal.getName()).get();
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
            System.out.println(usuario.getFoto());
            model.addAttribute("userlog", usuario);
        } catch (Exception e) {
           System.out.println(e);
        }
        return "post/post";
    }
}
