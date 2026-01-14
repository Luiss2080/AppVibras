package com.example.appvibras.modelo.entidades;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Entidad que representa una venta realizada a un cliente.
 */
@Entity(tableName = "ventas",
        foreignKeys = @ForeignKey(entity = Cliente.class,
                parentColumns = "id",
                childColumns = "idCliente",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("idCliente")})
public class Venta {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int idCliente;
    private long fecha;
    private double total;

    public Venta(int idCliente, long fecha, double total) {
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.total = total;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public long getFecha() { return fecha; }
    public void setFecha(long fecha) { this.fecha = fecha; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}
