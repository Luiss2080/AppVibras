package com.example.appvibras.modelo.entidades;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa a un cliente.
 */
@Entity(tableName = "clientes")
public class Cliente {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private String telefono;
    private String direccion;

    public Cliente(String nombre, String telefono, String direccion) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
