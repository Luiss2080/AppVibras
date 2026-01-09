package com.example.appvibras.modelo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.appvibras.modelo.entidades.Categoria;
import java.util.List;

@Dao
public interface CategoriaDao {
    @Insert
    long insertar(Categoria categoria);

    @Update
    void actualizar(Categoria categoria);

    @Delete
    void eliminar(Categoria categoria);

    @Query("SELECT * FROM categorias ORDER BY nombre ASC")
    List<Categoria> obtenerTodas();

    @Query("SELECT * FROM categorias WHERE id = :id")
    Categoria obtenerPorId(int id);
}
