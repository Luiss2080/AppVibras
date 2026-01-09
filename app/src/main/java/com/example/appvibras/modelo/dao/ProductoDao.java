package com.example.appvibras.modelo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.appvibras.modelo.entidades.Producto;
import java.util.List;

@Dao
public interface ProductoDao {
    @Insert
    long insertar(Producto producto);

    @Update
    void actualizar(Producto producto);

    @Delete
    void eliminar(Producto producto);

    @Query("SELECT * FROM productos ORDER BY nombre ASC")
    List<Producto> obtenerTodos();

    @Query("SELECT * FROM productos WHERE id = :id")
    Producto obtenerPorId(int id);

    @Query("SELECT * FROM productos WHERE idCategoria = :idCategoria")
    List<Producto> obtenerPorCategoria(int idCategoria);
}
