package com.example.appvibras.modelo.base;

import android.content.Context;
import com.example.appvibras.modelo.entidades.Categoria;
import com.example.appvibras.modelo.entidades.Usuario;
import java.util.concurrent.Executors;

/**
 * Clase encargada de insertar datos iniciales en la base de datos (Seeders).
 */
public class SembradorBaseDatos {

    public static void sembrar(BaseDatos db) {
        Executors.newSingleThreadExecutor().execute(() -> {
            // Sembrar Usuario Administrador si no existe
            if (db.usuarioDao().obtenerTodos().isEmpty()) {
                db.usuarioDao().insertar(new Usuario(
                    "Administrador del Sistema",  // nombres
                    "admin@appvibras.com",         // correoElectronico
                    "0999999999",                  // celular
                    "admin",                       // nombreUsuario
                    "admin123"                     // contrasena
                ));
            }

            // Sembrar Categorías de ropa iniciales si no existen
            if (db.categoriaDao().obtenerTodas().isEmpty()) {
                db.categoriaDao().insertar(new Categoria("Remeras"));
                db.categoriaDao().insertar(new Categoria("Pantalones"));
                db.categoriaDao().insertar(new Categoria("Abrigos"));
                db.categoriaDao().insertar(new Categoria("Accesorios"));
            }
        });
    }
}
