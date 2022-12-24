package com.postapp.postapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Postagem {
    private Integer id;
    private Usuario id_usuario;
    private String titulo;
    private String subtitulo;
    private String corpo;
    private LocalDateTime create_data;
}
