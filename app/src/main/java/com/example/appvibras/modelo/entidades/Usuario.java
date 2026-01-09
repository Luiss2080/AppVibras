package com.example.appvibras.modelo.entidades;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa a un usuario del sistema para el login.
 */
@Entity(tableName = "usuarios")
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String nombreUsuario;
    private String contrasena;
    private String nombreCompleto;

    public Usuario(String nombreUsuario, String contrasena, String nombreCompleto) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.nombreCompleto = nombreCompleto;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
}
