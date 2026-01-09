package com.example.appvibras.modelo.gestores;

import android.content.Context;
import com.example.appvibras.modelo.base.BaseDatos;
import com.example.appvibras.modelo.entidades.Usuario;
import com.example.appvibras.modelo.validadores.ValidadorUsuario;

/**
 * Clase gestora para la lógica de negocio de usuarios.
 */
public class GestorUsuarios {

    private BaseDatos db;

    public GestorUsuarios(Context contexto) {
        this.db = BaseDatos.obtenerInstancia(contexto);
    }

    public Usuario iniciarSesion(String nombreUsuario, String contrasena) {
        if (ValidadorUsuario.validarCredenciales(nombreUsuario, contrasena)) {
            return db.usuarioDao().login(nombreUsuario, contrasena);
        }
        return null;
    }

    public boolean registrarUsuario(String nombreUsuario, String contrasena, String nombreCompleto) {
        if (ValidadorUsuario.validarRegistro(nombreUsuario, contrasena, nombreCompleto)) {
            Usuario nuevo = new Usuario(nombreUsuario, contrasena, nombreCompleto);
            return db.usuarioDao().insertar(nuevo) > 0;
        }
        return false;
    }

    /**
     * Crea un usuario administrador por defecto si no existen usuarios.
     */
    public void crearUsuarioDefecto() {
        if (db.usuarioDao().obtenerTodos().isEmpty()) {
            registrarUsuario("admin", "admin123", "Administrador del Sistema");
        }
    }
}
