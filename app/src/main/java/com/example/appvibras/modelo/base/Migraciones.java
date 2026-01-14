package com.example.appvibras.modelo.base;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Clase encargada de gestionar los cambios en la estructura de la base de datos.
 */
public class Migraciones {

    // Ejemplo de migración de versión 1 a 2
    public static final Migration MIGRACION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Aquí irían los comandos SQL para alterar tablas en el futuro
        }
    };

    // Migración de versión 2 a 3: Agregar campos marca e industria a productos
    public static final Migration MIGRACION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE productos ADD COLUMN marca TEXT");
            database.execSQL("ALTER TABLE productos ADD COLUMN industria TEXT");
        }
    };

    public static final Migration[] TODAS = {
        MIGRACION_2_3
    };
}
