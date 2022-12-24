package com.postapp.postapp.repository;

import com.postapp.postapp.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class UsuarioRepository {
    @Autowired
    JdbcTemplate db;

    //Buscar todos os usuario
    public List<Usuario> findAll(){
        List<Usuario> list = db.query(
                "select * from usuario;",
                (rs, rowNum) -> {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setFoto(rs.getString("foto"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setPerfil(rs.getString("perfil"));
                    usuario.setTelefone(rs.getString("telefone"));
                    usuario.setUsername(rs.getString("username"));
                    return  usuario;
                }
        );
        return list;
    }
    public Usuario findById(int id){
        Usuario user = db.queryForObject(
            "select * from usuario where id = ?;",
                (rs, rowNum) -> {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setFoto(rs.getString("foto"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setPerfil(rs.getString("perfil"));
                    usuario.setTelefone(rs.getString("telefone"));
                    usuario.setUsername(rs.getString("username"));
                    return  usuario;
                },
                id);
        return user;
    }
    public Optional<Usuario> findByUsername(String username){
        try {
            Usuario user = db.queryForObject(
                    "select * from usuario where username = ?;",
                    (rs, rowNum) -> {
                        Usuario usuario = new Usuario();
                        usuario.setId(rs.getInt("id"));
                        usuario.setNome(rs.getString("nome"));
                        usuario.setFoto(rs.getString("foto"));
                        usuario.setEmail(rs.getString("email"));
                        usuario.setSenha(rs.getString("senha"));
                        usuario.setPerfil(rs.getString("perfil"));
                        usuario.setTelefone(rs.getString("telefone"));
                        usuario.setUsername(rs.getString("username"));
                        return usuario;
                    },
                    username);
            return Optional.of(user);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }
    public Optional<Usuario> findByEmail(String email){
        try {
            Usuario user = db.queryForObject(
                    "select * from usuario where email = ?;",
                    (rs, rowNum) -> {
                        Usuario usuario = new Usuario();
                        usuario.setId(rs.getInt("id"));
                        usuario.setNome(rs.getString("nome"));
                        usuario.setFoto(rs.getString("foto"));
                        usuario.setEmail(rs.getString("email"));
                        usuario.setSenha(rs.getString("senha"));
                        usuario.setPerfil(rs.getString("perfil"));
                        usuario.setTelefone(rs.getString("telefone"));
                        usuario.setUsername(rs.getString("username"));
                        return  usuario;
                    },
                    email);
            return Optional.of(user);

        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public Optional<Usuario> auth(String username, String password){
        try {
            Usuario user = db.queryForObject(
                    "select * from usuario where username = ? or email = ? and senha = ?;",
                    (rs, rowNum) -> {
                        Usuario usuario = new Usuario();
                        usuario.setId(rs.getInt("id"));
                        usuario.setNome(rs.getString("nome"));
                        usuario.setFoto(rs.getString("foto"));
                        usuario.setEmail(rs.getString("email"));
                        usuario.setSenha(rs.getString("senha"));
                        usuario.setPerfil(rs.getString("perfil"));
                        usuario.setTelefone(rs.getString("telefone"));
                        usuario.setUsername(rs.getString("username"));
                        return  usuario;
                    },
                    username,username,password);
            return Optional.of(user);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }


    public void save(Usuario usuario) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(encodePassword);
        db.update("insert into usuario(nome, telefone, email, senha, username, foto, perfil) values (?, ?, ?,?,?, ?,?);",
                usuario.getNome(),
                usuario.getTelefone(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getUsername(),
                "/img/user.png",
                "USER"
                );
    }
    public void update(Usuario usuario){
        db.update(
                "update usuario set nome=?, telefone=?, email=?, senha=?, username=?, foto=?, perfil=? where id = ?",
                usuario.getNome(),
                usuario.getTelefone(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getUsername(),
                usuario.getFoto(),
                usuario.getPerfil(),
                usuario.getId()
        );
    }

}
