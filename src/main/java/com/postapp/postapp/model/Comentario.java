package com.postapp.postapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comentario {
    private Integer id ;
    private Postagem id_postagem;
    private Usuario id_usuario;
    private String comentario;
    private LocalDateTime create_data;
}
