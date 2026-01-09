package com.example.appvibras.modelo.entidades;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa un movimiento de stock (entrada o salida/venta).
 */
@Entity(tableName = "movimientos_stock",
        foreignKeys = @ForeignKey(entity = Producto.class,
                parentColumns = "id",
                childColumns = "idProducto",
                onDelete = ForeignKey.CASCADE))
public class MovimientoStock {
    @PrimaryKey(autoGenerate = true)
    private int id;
    
    private int idProducto;
    private int cantidad;
    private String tipo; // "ENTRADA" o "SALIDA"
    private long fecha; // Almacenado como timestamp

    public MovimientoStock(int idProducto, int cantidad, String tipo, long fecha) {
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.tipo = tipo;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public long getFecha() { return fecha; }
    public void setFecha(long fecha) { this.fecha = fecha; }
}
