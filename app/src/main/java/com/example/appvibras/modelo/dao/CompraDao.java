package com.example.appvibras.modelo.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.appvibras.modelo.entidades.Compra;
import java.util.List;

@Dao
public interface CompraDao {
    @Insert
    long insertar(Compra compra);

    @Query("SELECT * FROM compras ORDER BY fecha DESC")
    List<Compra> obtenerTodas();
}
