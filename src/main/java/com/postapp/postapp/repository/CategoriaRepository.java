package com.postapp.postapp.repository;

import com.postapp.postapp.model.Categoria;
import com.postapp.postapp.model.Postagem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoriaRepository {
    @Autowired
    JdbcTemplate db;

    public List<Categoria> findAll(){
        List<Categoria> list = db.query(
                "select * from categorias;",
                (rs, rowNum) -> {
                    Categoria cat = new Categoria();
                    cat.setId(rs.getInt("id"));
                    cat.setNome(rs.getString("nome"));
                    return cat;
                }
        );
        return list;
    }

    public Categoria findById(int id){
        Categoria categoria = db.queryForObject(
                "select * from categorias where id = ?;",
                (rs, rowNum) -> {
                    Categoria cat = new Categoria();
                    cat.setId(rs.getInt("id"));
                    cat.setNome(rs.getString("nome"));
                    return cat;
                },
                id);
        return categoria;
    }

    public List<Categoria> findBy(Postagem postagem){
        List<Categoria> list = db.query(
                "select * from postagem_categorias where id_postagem = ?;",
                (rs, rowNum) -> {
                    return this.findById(rs.getInt("id_categoria"));
                }, postagem.getId());
        return list;
    }

}
