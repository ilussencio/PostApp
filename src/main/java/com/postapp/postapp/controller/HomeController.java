package com.postapp.postapp.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import com.postapp.postapp.model.Usuario;
import com.postapp.postapp.repository.UsuarioRepository;

@Controller
public class HomeController {
  @Autowired
  UsuarioRepository usuarioRepository;
  
  @Autowired
  JdbcTemplate db;

  @GetMapping("/")
  public String HomeController(Model model, HttpServletRequest request) {

    try {
      Principal principal = request.getUserPrincipal();
      Usuario usuario = usuarioRepository.findByUsername(principal.getName()).get();
      System.out.println(usuario.getFoto());
      model.addAttribute("userlog", usuario);
    } catch (Exception e) {
      System.out.println(e);
    }

    return "home/home";
  }
  
  @GetMapping("/home")
  public String Home(Model model, HttpServletRequest request) {

    try {
      Principal principal = request.getUserPrincipal();
      Usuario usuario = usuarioRepository.findByUsername(principal.getName()).get();
      System.out.println(usuario.getFoto());
      model.addAttribute("userlog", usuario);
    } catch (Exception e) {
      System.out.println(e);
    }

    return "home/home";
  }

  @GetMapping("/home-logoff")
  public String LogOff() {
    return "home/home-logoff";
  }


  @GetMapping("/perfil")
  public String Perfil(Model model, HttpServletRequest request) {

    try {
      Principal principal = request.getUserPrincipal();
      Usuario usr = usuarioRepository.findByUsername(principal.getName()).get();
      model.addAttribute("userlog", usr);
    } catch (Exception e) {
        System.out.println(e);
    }
    return "perfil";
  }


  
}
