package com.example.appvibras.modelo.entidades;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa el detalle de los productos en una venta.
 */
@Entity(tableName = "detalles_venta",
        foreignKeys = {
            @ForeignKey(entity = Venta.class, parentColumns = "id", childColumns = "idVenta", onDelete = ForeignKey.CASCADE),
            @ForeignKey(entity = Producto.class, parentColumns = "id", childColumns = "idProducto", onDelete = ForeignKey.CASCADE)
        })
public class DetalleVenta {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idVenta;
    private int idProducto;
    private int cantidad;
    private double precioUnitario;

    public DetalleVenta(int idVenta, int idProducto, int cantidad, double precioUnitario) {
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
}
