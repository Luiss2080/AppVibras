package com.example.appvibras.modelo.base;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.appvibras.modelo.entidades.Categoria;
import com.example.appvibras.modelo.entidades.MovimientoStock;
import com.example.appvibras.modelo.entidades.Producto;
import com.example.appvibras.modelo.entidades.Usuario;
import com.example.appvibras.modelo.dao.CategoriaDao;
import com.example.appvibras.modelo.dao.MovimientoStockDao;
import com.example.appvibras.modelo.dao.ProductoDao;
import com.example.appvibras.modelo.dao.UsuarioDao;

/**
 * Base de datos centralizada de la aplicación usando ROOM.
 */
@Database(entities = {Usuario.class, Categoria.class, Producto.class, MovimientoStock.class}, version = 1)
public abstract class BaseDatos extends RoomDatabase {

    private static BaseDatos instancia;

    public abstract UsuarioDao usuarioDao();
    public abstract CategoriaDao categoriaDao();
    public abstract ProductoDao productoDao();
    public abstract MovimientoStockDao movimientoStockDao();

    /**
     * Obtiene la instancia única de la base de datos (Singleton).
     */
    public static synchronized BaseDatos obtenerInstancia(Context contexto) {
        if (instancia == null) {
            instancia = Room.databaseBuilder(contexto.getApplicationContext(),
                            BaseDatos.class, "vibras_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // Nota: En producción usar hilos secundarios, pero para simplificar el modelo inicial lo habilitamos.
                    .build();
        }
        return instancia;
    }
}
