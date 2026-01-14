package com.example.appvibras.modelo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.appvibras.modelo.entidades.Proveedor;
import java.util.List;

@Dao
public interface ProveedorDao {
    @Insert
    long insertar(Proveedor proveedor);

    @Update
    void actualizar(Proveedor proveedor);

    @Delete
    void eliminar(Proveedor proveedor);

    @Query("SELECT * FROM proveedores ORDER BY nombre ASC")
    List<Proveedor> obtenerTodos();
}
