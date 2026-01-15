package com.example.appvibras.modelo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.appvibras.modelo.entidades.Usuario;
import java.util.List;

/**
 * DAO de Usuario - Capa de Acceso a Datos (Modelo Tres Capas)
 */
@Dao
public interface UsuarioDao {
    @Insert
    long insertar(Usuario usuario);

    @Update
    void actualizar(Usuario usuario);

    @Delete
    void eliminar(Usuario usuario);

    /**
     * Autenticar usuario con nombre de usuario y contraseña
     */
    @Query("SELECT * FROM usuarios WHERE nombreUsuario = :nombreUsuario AND contrasena = :contrasena LIMIT 1")
    Usuario login(String nombreUsuario, String contrasena);

    /**
     * Buscar usuario solo por nombre de usuario (para verificar existencia)
     */
    @Query("SELECT * FROM usuarios WHERE nombreUsuario = :nombreUsuario LIMIT 1")
    Usuario buscarPorNombreUsuario(String nombreUsuario);

    @Query("SELECT * FROM usuarios")
    List<Usuario> obtenerTodos();
}
