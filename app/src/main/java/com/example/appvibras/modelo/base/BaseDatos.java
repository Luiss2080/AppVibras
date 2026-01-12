package com.example.appvibras.modelo.base;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
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
 * Sigue el patrón Singleton y maneja migraciones y datos iniciales.
 */
@Database(entities = {Usuario.class, Categoria.class, Producto.class, MovimientoStock.class}, version = 1)
public abstract class BaseDatos extends RoomDatabase {

    private static BaseDatos instancia;

    public abstract UsuarioDao usuarioDao();
    public abstract CategoriaDao categoriaDao();
    public abstract ProductoDao productoDao();
    public abstract MovimientoStockDao movimientoStockDao();

    /**
     * Obtiene la instancia única de la base de datos.
     */
    public static synchronized BaseDatos obtenerInstancia(Context contexto) {
        if (instancia == null) {
            instancia = Room.databaseBuilder(contexto.getApplicationContext(),
                            BaseDatos.class, "vibras_db")
                    .addMigrations(Migraciones.TODAS)
                    .addCallback(callbackBaseDatos)
                    .allowMainThreadQueries() // Se permite para simplificar, pero idealmente usar hilos.
                    .build();
        }
        return instancia;
    }

    /**
     * Callback para ejecutar acciones al crear o abrir la base de datos.
     */
    private static final RoomDatabase.Callback callbackBaseDatos = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Cuando la BD se crea por primera vez, sembramos datos iniciales.
            SembradorBaseDatos.sembrar(instancia);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // Aseguramos que haya datos mínimos cada vez que se abre.
            SembradorBaseDatos.sembrar(instancia);
        }
    };
}
