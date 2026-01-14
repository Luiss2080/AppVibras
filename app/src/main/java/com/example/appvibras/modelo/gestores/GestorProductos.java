package com.example.appvibras.modelo.gestores;

import android.content.Context;
import com.example.appvibras.modelo.base.BaseDatos;
import com.example.appvibras.modelo.entidades.Producto;
import java.util.List;

/**
 * Clase gestora para la lógica de negocio de productos (CRUD).
 */
public class GestorProductos {

    private BaseDatos db;

    public GestorProductos(Context contexto) {
        this.db = BaseDatos.obtenerInstancia(contexto);
    }

    public boolean agregarProducto(String nombre, String descripcion, double precio, int idCategoria, String marca, String industria) {
        if (nombre == null || nombre.trim().isEmpty() || precio < 0) return false;
        
        Producto nuevo = new Producto(nombre, descripcion, precio, 0, idCategoria, marca, industria);
        return db.productoDao().insertar(nuevo) > 0;
    }

    public List<Producto> obtenerTodos() {
        return db.productoDao().obtenerTodos();
    }

    public void actualizarProducto(Producto producto) {
        db.productoDao().actualizar(producto);
    }

    public void eliminarProducto(Producto producto) {
        db.productoDao().eliminar(producto);
    }

    public Producto obtenerPorId(int id) {
        return db.productoDao().obtenerPorId(id);
    }
}
