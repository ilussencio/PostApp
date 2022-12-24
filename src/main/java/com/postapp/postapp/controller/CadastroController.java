package com.postapp.postapp.controller;

import com.postapp.postapp.model.Usuario;
import com.postapp.postapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cadastro")
public class CadastroController {
  @Autowired
  UsuarioRepository repository;
  @GetMapping()
  public String Cadastro(Model model) {
    model.addAttribute("user", new Usuario());
    return "acessos/cadastro";
  }
  @PostMapping()
  public String novoCadastro(Usuario usuario){
    System.out.println(usuario);
    repository.save(usuario);
    return "acessos/login";
  }

}
