package com.example.appvibras.modelo.gestores;

import android.content.Context;
import com.example.appvibras.modelo.base.BaseDatos;
import com.example.appvibras.modelo.entidades.Cliente;
import java.util.List;

public class GestorClientes {
    private BaseDatos db;

    public GestorClientes(Context contexto) {
        this.db = BaseDatos.obtenerInstancia(contexto);
    }

    public boolean agregar(String nombre, String telefono, String direccion) {
        if (nombre.isEmpty()) return false;
        return db.clienteDao().insertar(new Cliente(nombre, telefono, direccion)) > 0;
    }

    public List<Cliente> obtenerTodos() {
        return db.clienteDao().obtenerTodos();
    }

    public void actualizar(Cliente cliente) {
        db.clienteDao().actualizar(cliente);
    }

    public void eliminar(Cliente cliente) {
        db.clienteDao().eliminar(cliente);
    }
}
