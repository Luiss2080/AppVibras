package com.example.appvibras.modelo.entidades;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa a un proveedor de mercaderia.
 */
@Entity(tableName = "proveedores")
public class Proveedor {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nombre;
    private String contacto;
    private String empresa;

    public Proveedor(String nombre, String contacto, String empresa) {
        this.nombre = nombre;
        this.contacto = contacto;
        this.empresa = empresa;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public String getEmpresa() { return empresa; }
    public void setEmpresa(String empresa) { this.empresa = empresa; }
}
