package com.example.appvibras.modelo.gestores;

import android.content.Context;
import com.example.appvibras.modelo.base.BaseDatos;
import com.example.appvibras.modelo.entidades.Usuario;
import com.example.appvibras.modelo.validadores.ValidadorUsuario;

/**
 * Gestor de Usuarios - Capa de Negocio (Modelo Tres Capas)
 * Maneja la lógica de autenticación y registro de usuarios
 */
public class GestorUsuarios {

    private BaseDatos db;
    private String ultimoError; // Para almacenar el mensaje de error específico

    public GestorUsuarios(Context contexto) {
        this.db = BaseDatos.obtenerInstancia(contexto);
        this.ultimoError = "";
    }

    /**
     * Intenta iniciar sesión con las credenciales proporcionadas
     * @return Usuario si las credenciales son correctas, null si son incorrectas
     */
    public Usuario iniciarSesion(String nombreUsuario, String contrasena) {
        ultimoError = "";

        // Validar que los campos no estén vacíos
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            ultimoError = "El nombre de usuario no puede estar vacío";
            return null;
        }

        if (contrasena == null || contrasena.trim().isEmpty()) {
            ultimoError = "La contraseña no puede estar vacía";
            return null;
        }

        // Buscar usuario en la base de datos
        Usuario usuario = db.usuarioDao().login(nombreUsuario.trim(), contrasena);

        if (usuario == null) {
            // Verificar si el usuario existe
            Usuario usuarioExistente = db.usuarioDao().buscarPorNombreUsuario(nombreUsuario.trim());

            if (usuarioExistente == null) {
                ultimoError = "Usuario no encontrado";
            } else {
                ultimoError = "Contraseña incorrecta"; // ⭐ Mensaje específico de contraseña incorrecta
            }
            return null;
        }

        return usuario;
    }

    /**
     * Registra un nuevo usuario en el sistema
     */
    public boolean registrarUsuario(String nombres, String correoElectronico, String celular,
                                    String nombreUsuario, String contrasena) {
        ultimoError = "";

        // Validar campos obligatorios
        if (nombres == null || nombres.trim().isEmpty()) {
            ultimoError = "El nombre es obligatorio";
            return false;
        }

        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            ultimoError = "El nombre de usuario es obligatorio";
            return false;
        }

        if (contrasena == null || contrasena.length() < 4) {
            ultimoError = "La contraseña debe tener al menos 4 caracteres";
            return false;
        }

        // Verificar si el usuario ya existe
        if (db.usuarioDao().buscarPorNombreUsuario(nombreUsuario.trim()) != null) {
            ultimoError = "El nombre de usuario ya está en uso";
            return false;
        }

        // Crear y guardar el nuevo usuario
        Usuario nuevo = new Usuario(nombres.trim(), correoElectronico, celular,
                                    nombreUsuario.trim(), contrasena);
        boolean resultado = db.usuarioDao().insertar(nuevo) > 0;

        if (!resultado) {
            ultimoError = "Error al guardar el usuario en la base de datos";
        }

        return resultado;
    }

    /**
     * Crea un usuario administrador por defecto si no existen usuarios
     */
    public void crearUsuarioDefecto() {
        if (db.usuarioDao().obtenerTodos().isEmpty()) {
            registrarUsuario(
                "Administrador del Sistema",
                "admin@appvibras.com",
                "0999999999",
                "admin",
                "admin123"
            );
        }
    }

    /**
     * Obtiene el último mensaje de error generado
     */
    public String getUltimoError() {
        return ultimoError;
    }
}
