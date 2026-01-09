package com.example.appvibras.modelo.validadores;

/**
 * Clase para validar datos de usuario.
 */
public class ValidadorUsuario {

    public static boolean validarCredenciales(String usuario, String contrasena) {
        return usuario != null && !usuario.trim().isEmpty() && 
               contrasena != null && !contrasena.trim().isEmpty();
    }

    public static boolean validarRegistro(String usuario, String contrasena, String nombre) {
        return validarCredenciales(usuario, contrasena) && 
               nombre != null && !nombre.trim().isEmpty();
    }
}
