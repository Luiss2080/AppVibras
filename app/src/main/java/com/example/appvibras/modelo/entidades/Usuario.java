package com.example.appvibras.modelo.entidades;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa a un usuario del sistema para el login.
 * Campos: nombres, correo electrónico, celular, usuario, contraseña
 */
@Entity(tableName = "usuarios")
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String nombres;           // Nombres completos del usuario
    private String correoElectronico; // Email del usuario
    private String celular;           // Número de celular
    private String nombreUsuario;     // Usuario para login
    private String contrasena;        // Contraseña para login

    public Usuario(String nombres, String correoElectronico, String celular,
                   String nombreUsuario, String contrasena) {
        this.nombres = nombres;
        this.correoElectronico = correoElectronico;
        this.celular = celular;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    // Método de compatibilidad con código existente
    public String getNombreCompleto() { return nombres; }
    public void setNombreCompleto(String nombreCompleto) { this.nombres = nombreCompleto; }
}
