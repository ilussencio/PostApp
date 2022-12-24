package com.postapp.postapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    private Integer id;
    private String nome;
    private String telefone;
    private String email;
    private String senha;
    private String username;
    private String perfil;
    private String foto;
}
