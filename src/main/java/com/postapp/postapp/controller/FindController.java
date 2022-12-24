package com.postapp.postapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postapp.postapp.model.Postagem;
import com.postapp.postapp.repository.PostagemRepository;

@RestController
@RequestMapping("/busca")
public class FindController {
    @Autowired
    PostagemRepository postagemRepository;

    @GetMapping("/{busca}")
    public List<Postagem> findPost(@PathVariable String busca){
        return postagemRepository.findBy(busca);
    } 
}
