package com.postapp.postapp.controller;

import com.postapp.postapp.model.Comentario;
import com.postapp.postapp.model.Postagem;
import com.postapp.postapp.model.Usuario;
import com.postapp.postapp.repository.ComentarioRepository;
import com.postapp.postapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {
  @Autowired
  ComentarioRepository repository;
  @PostMapping
  public ResponseEntity<Comentario> save(@RequestBody Comentario comentario) {
    repository.save(comentario);
    return ResponseEntity.ok().body(comentario);
  }
  @PostMapping("/get")
  public ResponseEntity<List<Comentario>> findAll(@RequestBody Postagem post) {
    List<Comentario> list = repository.findBy(post);
    System.out.println(list);
    return ResponseEntity.ok().body(list);
  }
}
