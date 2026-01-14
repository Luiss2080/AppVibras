package com.example.appvibras.modelo.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.appvibras.modelo.entidades.DetalleVenta;
import com.example.appvibras.modelo.entidades.Venta;
import java.util.List;

@Dao
public interface VentaDao {
    @Insert
    long insertar(Venta venta);

    @Insert
    void insertarDetalles(List<DetalleVenta> detalles);

    @Query("SELECT * FROM ventas ORDER BY fecha DESC")
    List<Venta> obtenerTodas();
}
