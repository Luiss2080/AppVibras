package com.example.appvibras.modelo.base;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.appvibras.modelo.entidades.*;
import com.example.appvibras.modelo.dao.*;

/**
 * Base de datos centralizada de la aplicación usando ROOM.
 * Incluye tablas para Ventas, Compras, Clientes y Proveedores.
 */
@Database(entities = {
        Usuario.class, 
        Categoria.class, 
        Producto.class, 
        MovimientoStock.class,
        Cliente.class,
        Proveedor.class,
        Venta.class,
        DetalleVenta.class,
        Compra.class
}, version = 2) // Incrementamos la versión para activar la migración
public abstract class BaseDatos extends RoomDatabase {

    private static BaseDatos instancia;

    public abstract UsuarioDao usuarioDao();
    public abstract CategoriaDao categoriaDao();
    public abstract ProductoDao productoDao();
    public abstract MovimientoStockDao movimientoStockDao();
    // Nota: Aquí se deben agregar los nuevos DAOs para Cliente, Proveedor, Venta, etc.

    public static synchronized BaseDatos obtenerInstancia(Context contexto) {
        if (instancia == null) {
            instancia = Room.databaseBuilder(contexto.getApplicationContext(),
                            BaseDatos.class, "vibras_db")
                    .addMigrations(Migraciones.TODAS)
                    .fallbackToDestructiveMigration() // Para desarrollo rápido, borra y recrea si hay cambios de esquema
                    .addCallback(callbackBaseDatos)
                    .allowMainThreadQueries()
                    .build();
        }
        return instancia;
    }

    private static final RoomDatabase.Callback callbackBaseDatos = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            SembradorBaseDatos.sembrar(instancia);
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            SembradorBaseDatos.sembrar(instancia);
        }
    };
}
