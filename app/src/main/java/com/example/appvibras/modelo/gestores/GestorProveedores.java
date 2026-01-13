package com.example.appvibras.modelo.gestores;

import android.content.Context;
import com.example.appvibras.modelo.base.BaseDatos;
import com.example.appvibras.modelo.entidades.Proveedor;
import java.util.List;

public class GestorProveedores {
    private BaseDatos db;

    public GestorProveedores(Context contexto) {
        this.db = BaseDatos.obtenerInstancia(contexto);
    }

    public boolean agregar(String nombre, String contacto, String empresa) {
        if (nombre.isEmpty()) return false;
        return db.proveedorDao().insertar(new Proveedor(nombre, contacto, empresa)) > 0;
    }

    public List<Proveedor> obtenerTodos() {
        return db.proveedorDao().obtenerTodos();
    }

    public void actualizar(Proveedor proveedor) {
        db.proveedorDao().actualizar(proveedor);
    }

    public void eliminar(Proveedor proveedor) {
        db.proveedorDao().eliminar(proveedor);
    }
}
