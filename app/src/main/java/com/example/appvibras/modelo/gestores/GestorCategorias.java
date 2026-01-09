package com.example.appvibras.modelo.gestores;

import android.content.Context;
import com.example.appvibras.modelo.base.BaseDatos;
import com.example.appvibras.modelo.entidades.Categoria;
import java.util.List;

/**
 * Clase gestora para la lógica de negocio de las categorías.
 */
public class GestorCategorias {

    private BaseDatos db;

    public GestorCategorias(Context contexto) {
        this.db = BaseDatos.obtenerInstancia(contexto);
    }

    public boolean agregarCategoria(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        Categoria nueva = new Categoria(nombre.trim());
        return db.categoriaDao().insertar(nueva) > 0;
    }

    public List<Categoria> obtenerTodas() {
        return db.categoriaDao().obtenerTodas();
    }

    public void eliminarCategoria(Categoria categoria) {
        db.categoriaDao().eliminar(categoria);
    }

    public void actualizarCategoria(Categoria categoria) {
        if (categoria.getNombre() != null && !categoria.getNombre().trim().isEmpty()) {
            db.categoriaDao().actualizar(categoria);
        }
    }
}
