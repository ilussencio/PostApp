package com.postapp.postapp.repository;

import com.postapp.postapp.model.Comentario;
import com.postapp.postapp.model.Postagem;
import com.postapp.postapp.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class ComentarioRepository {
    @Autowired
    JdbcTemplate db;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PostagemRepository postagemRepository;

    public List<Comentario> findBy(Postagem postagem){
        List<Comentario> list = db.query(
                "select * from comentario where id_postagem = ? order by create_data desc ;",
                (rs, rowNum) -> {
                   Comentario comentario = new Comentario();
                   comentario.setId(rs.getInt("id"));
                   comentario.setComentario(rs.getString("comentario"));
                   comentario.setId_usuario(usuarioRepository.findById(rs.getInt("id_usuario")));
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime localDateTime = LocalDateTime.parse(rs.getString("create_data"), dateTimeFormatter);
                    comentario.setCreate_data(localDateTime);
                   return comentario;
                },postagem.getId());
        return list;
    }

    

    public void save(Comentario comentario){
        db.update("insert into comentario(id_postagem, id_usuario, comentario, create_data) values(?,?,?,?)",
            comentario.getId_postagem().getId(),
            comentario.getId_usuario().getId(),
            comentario.getComentario(),
            LocalDateTime.now()
        );
    }
}
