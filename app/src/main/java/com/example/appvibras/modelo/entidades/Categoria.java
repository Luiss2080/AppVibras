package com.example.appvibras.modelo.entidades;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa una categoría de productos.
 */
@Entity(tableName = "categorias")
public class Categoria {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
