package com.example.appvibras.modelo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.appvibras.modelo.entidades.Usuario;
import java.util.List;

@Dao
public interface UsuarioDao {
    @Insert
    long insertar(Usuario usuario);

    @Update
    void actualizar(Usuario usuario);

    @Delete
    void eliminar(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE nombreUsuario = :nombreUsuario AND contrasena = :contrasena LIMIT 1")
    Usuario login(String nombreUsuario, String contrasena);

    @Query("SELECT * FROM usuarios")
    List<Usuario> obtenerTodos();
}
