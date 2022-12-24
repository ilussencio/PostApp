package com.postapp.postapp.repository;

import com.postapp.postapp.model.Categoria;
import com.postapp.postapp.model.Postagem;
import com.postapp.postapp.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Repository
public class PostagemRepository {
    @Autowired
    JdbcTemplate db;
    @Autowired
    UsuarioRepository usuarioRepository;

    

    public List<Postagem> findAll(){
        List<Postagem> list = db.query(
                "select * from postagem;",
                (rs, rowNum) -> {
                    Postagem postagem = new Postagem();
                    postagem.setId(rs.getInt("id"));
                    postagem.setTitulo(rs.getString("titulo"));
                    postagem.setCorpo(rs.getString("corpo"));
                    postagem.setSubtitulo(rs.getString("subtitulo"));
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime localDateTime = LocalDateTime.parse(rs.getString("create_data"), dateTimeFormatter);
                    postagem.setCreate_data(localDateTime);
                    postagem.setId_usuario(usuarioRepository.findById(rs.getInt("id_usuario")));
                    return postagem;
                }
        );
        return list;
    }

    public List<Postagem> findBy(Usuario usuario){
        List<Postagem> list = db.query(
                "select * from postagem where id_usuario = ?;",
                (rs, rowNum) -> {
                    Postagem postagem = new Postagem();
                    postagem.setId(rs.getInt("id"));
                    postagem.setTitulo(rs.getString("titulo"));
                    postagem.setCorpo(rs.getString("corpo"));
                    postagem.setSubtitulo(rs.getString("subtitulo"));
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime localDateTime = LocalDateTime.parse(rs.getString("create_data"), dateTimeFormatter);
                    postagem.setCreate_data(localDateTime);
                    postagem.setId_usuario(usuarioRepository.findById(rs.getInt("id_usuario")));
                    return postagem;
                },usuario.getId());
        return list;
    }
    

    public List<Postagem> findBy(Usuario usuario, int limit, int current){
        List<Postagem> list = db.query(
                "select id, titulo, subtitulo from postagem where id_usuario = ? and id != ? order by rand() limit ?;",
                (rs, rowNum) -> {
                    Postagem postagem = new Postagem();
                    postagem.setId(rs.getInt("id"));
                    postagem.setTitulo(rs.getString("titulo"));
                    postagem.setSubtitulo(rs.getString("subtitulo"));
                    return postagem;
                },usuario.getId(),current, limit);
        return list;
    }

    public List<Postagem> findBy(List<Categoria> cat, int limit){
        String inSql = String.join(",", Collections.nCopies(cat.size(), "?"));

        List<Postagem> list = db.query(
                String.format("select  p.id, p.titulo, p.subtitulo, p.create_data from postagem_categorias pc inner join postagem p on pc.id_postagem = p.id inner join categorias c on pc.id_categoria = c.id where c.nome in (%s) order by p.create_data desc limit 3;", inSql),
                (rs, rowNum) -> {
                    Postagem postagem = new Postagem();
                    postagem.setId(rs.getInt("id"));
                    postagem.setTitulo(rs.getString("titulo"));
                    postagem.setSubtitulo(rs.getString("subtitulo"));
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime localDateTime = LocalDateTime.parse(rs.getString("create_data"), dateTimeFormatter);
                    postagem.setCreate_data(localDateTime);
                    return postagem;
                },
                cat.stream().map(x -> x.getNome()).toArray()
            );
        return list;
    }

    public Postagem findById(int id){
       Postagem post = db.queryForObject(
                "select * from postagem where id = ?;",
                (rs, rowNum) -> {
                    Postagem postagem = new Postagem();
                    postagem.setId(rs.getInt("id"));
                    postagem.setTitulo(rs.getString("titulo"));
                    postagem.setCorpo(rs.getString("corpo"));
                    postagem.setSubtitulo(rs.getString("subtitulo"));
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime localDateTime = LocalDateTime.parse(rs.getString("create_data"), dateTimeFormatter);
                    postagem.setCreate_data(localDateTime);
                    postagem.setId_usuario(usuarioRepository.findById(rs.getInt("id_usuario")));
                    return postagem;
                },id);
        return post;
    }

    public List<Postagem> findBy(String busca){
        List<Postagem> list = db.query(
                "select distinct p.id, p.titulo, p.subtitulo, p.id_usuario from postagem_categorias pc inner join postagem p on pc.id_postagem = p.id inner join categorias c ON pc.id_categoria = c.id  where UPPER(p.titulo) like UPPER(?) or UPPER(c.nome) like UPPER(?);",
                (rs, rowNum) -> {
                    Postagem postagem = new Postagem();
                    postagem.setId(rs.getInt("id"));
                    postagem.setTitulo(rs.getString("titulo"));
                    postagem.setSubtitulo(rs.getString("subtitulo"));
                    postagem.setId_usuario(usuarioRepository.findById(rs.getInt("id_usuario")));
                    return postagem;
                },
                "%"+busca+"%","%"+busca+"%");
        return list;
    }

    public void save(Postagem postagem) {
        //insert into postagem(id_usuario, titulo, subtitulo, corpo, create_data) values (2,'ARTIGO TESTE', 'TESTE','msg', '2022-11-19T13:00:00');

        db.update(
            "insert into postagem(id_usuario, titulo, subtitulo, corpo, create_data) values (?,?,?,?,?);",
            postagem.getId_usuario().getId(),
            postagem.getTitulo(),
            postagem.getSubtitulo(),
            postagem.getCorpo(),
            postagem.getCreate_data()
        );

        int idPost = db.queryForObject(
                "select id from postagem where id_usuario = ? order by create_data desc limit 1",
                (rs, rowNum) -> {
                    return rs.getInt("id");
                },
                postagem.getId_usuario().getId());
        
        String[] categorias = postagem.getId_usuario().getNome().split(",");

        for(int i = 0; i < categorias.length; i ++){
            db.update(
                "insert into postagem_categorias(id_postagem,id_categoria) values (?,?);",
                idPost,
                categorias[i]
            );
        }
    }

    public void delete(Postagem postagem) {
        db.update("DELETE FROM postagem_categorias WHERE id_postagem = ?;", postagem.getId());
        db.update("DELETE from comentario WHERE id_postagem = ?;", postagem.getId());
        db.update("DELETE from postagem where id = ?;", postagem.getId());
       
    }
}