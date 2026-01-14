package com.example.appvibras.modelo.entidades;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa una compra realizada a un proveedor para reponer stock.
 */
@Entity(tableName = "compras",
        foreignKeys = @ForeignKey(entity = Proveedor.class,
                parentColumns = "id",
                childColumns = "idProveedor",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("idProveedor")})
public class Compra {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idProveedor;
    private long fecha;
    private double total;

    public Compra(int idProveedor, long fecha, double total) {
        this.idProveedor = idProveedor;
        this.fecha = fecha;
        this.total = total;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }
    public long getFecha() { return fecha; }
    public void setFecha(long fecha) { this.fecha = fecha; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
