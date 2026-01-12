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

    public static final Migration[] TODAS = {
        // MIGRACION_1_2
    };
}
