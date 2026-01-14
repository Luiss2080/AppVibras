package com.example.appvibras.modelo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.appvibras.modelo.entidades.Cliente;
import java.util.List;

@Dao
public interface ClienteDao {
    @Insert
    long insertar(Cliente cliente);

    @Update
    void actualizar(Cliente cliente);

    @Delete
    void eliminar(Cliente cliente);

    @Query("SELECT * FROM clientes ORDER BY nombre ASC")
    List<Cliente> obtenerTodos();
}
