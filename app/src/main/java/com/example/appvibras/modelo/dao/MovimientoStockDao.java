package com.example.appvibras.modelo.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.appvibras.modelo.entidades.MovimientoStock;
import java.util.List;

@Dao
public interface MovimientoStockDao {
    @Insert
    long insertar(MovimientoStock movimiento);

    @Query("SELECT * FROM movimientos_stock WHERE idProducto = :idProducto ORDER BY fecha DESC")
    List<MovimientoStock> obtenerPorProducto(int idProducto);

    @Query("SELECT * FROM movimientos_stock ORDER BY fecha DESC")
    List<MovimientoStock> obtenerTodos();
}
