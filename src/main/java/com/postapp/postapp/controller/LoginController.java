package com.postapp.postapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.postapp.postapp.model.Usuario;
import com.postapp.postapp.repository.UsuarioRepository;

@Controller
public class LoginController {

  @GetMapping("/login")
  public String Login() {
    return "acessos/login";
  }
}
