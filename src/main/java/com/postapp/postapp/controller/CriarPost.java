package com.postapp.postapp.controller;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.postapp.postapp.model.Categoria;
import com.postapp.postapp.model.Postagem;
import com.postapp.postapp.model.Usuario;
import com.postapp.postapp.repository.CategoriaRepository;
import com.postapp.postapp.repository.PostagemRepository;
import com.postapp.postapp.repository.UsuarioRepository;

@Controller
public class CriarPost {
    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PostagemRepository postagemRepository;

    @GetMapping("/criarpost")
    public String Criacao(Model model, HttpServletRequest request) {
      List<Categoria> listCategoria = categoriaRepository.findAll();
      model.addAttribute("categorias", listCategoria);

      Postagem postagem = new Postagem();
      Usuario user = new Usuario();
      postagem.setId_usuario(user);
      model.addAttribute("post", postagem);


      try {
        Principal principal = request.getUserPrincipal();
        Usuario usr = usuarioRepository.findByUsername(principal.getName()).get();
        model.addAttribute("userlog", usr);
      } catch (Exception e) {
          System.out.println(e);
      }

      return "post/criarpost";
    }

    @PostMapping("/criarpost")
    public String savePost(Postagem postagem,  Model model, HttpServletRequest request) {
      postagem.setCreate_data(LocalDateTime.now());
      postagemRepository.save(postagem);

      try {
        Principal principal = request.getUserPrincipal();
        Usuario usr = usuarioRepository.findByUsername(principal.getName()).get();
        model.addAttribute("userlog", usr);
      } catch (Exception e) {
          System.out.println(e);
      }

      return "home/home";
    }

    @GetMapping("/delete-post")
    public String excluirPost(@RequestParam("id") int id, Model model, HttpServletRequest request) throws IOException{
      Postagem postagem = postagemRepository.findById(id);
      postagemRepository.delete(postagem);

      try {
        Principal principal = request.getUserPrincipal();
        Usuario usr = usuarioRepository.findByUsername(principal.getName()).get();
        model.addAttribute("userlog", usr);
      } catch (Exception e) {
          System.out.println(e);
      }


      return "home/home";
    }
}
