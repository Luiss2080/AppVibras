package com.example.appvibras.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appvibras.MainActivity;
import com.example.appvibras.R;

/**
 * Controlador para el Menú Principal.
 */
public class MenuPrincipalActivity extends AppCompatActivity {

    private static final String TAG = "MenuPrincipalActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Log.d(TAG, "MenuPrincipalActivity iniciado");

        // Botón Categorías (usando la card completa)
        findViewById(R.id.card_categorias).setOnClickListener(v -> {
            Log.d(TAG, "Click en card_categorias");
            Toast.makeText(this, "Navegando a Categorías...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, CategoriasActivity.class);
            startActivity(intent);
        });

        // Botón Productos
        findViewById(R.id.card_productos).setOnClickListener(v -> {
            Log.d(TAG, "Click en card_productos");
            Toast.makeText(this, "Navegando a Productos...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ProductosActivity.class);
            startActivity(intent);
        });

        // Botón Entradas (Compras)
        findViewById(R.id.card_entradas).setOnClickListener(v -> {
            Log.d(TAG, "Click en card_entradas");
            Toast.makeText(this, "Navegando a Compras...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ComprasActivity.class);
            startActivity(intent);
        });

        // Botón Salidas (Ventas)
        findViewById(R.id.card_salidas).setOnClickListener(v -> {
            Log.d(TAG, "Click en card_salidas");
            Toast.makeText(this, "Navegando a Ventas...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, VentasActivity.class);
            startActivity(intent);
        });

        // Botón Reportes
        findViewById(R.id.card_reportes).setOnClickListener(v -> {
            Log.d(TAG, "Click en card_reportes");
            Toast.makeText(this, "Navegando a Clientes...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ClientesActivity.class);
            startActivity(intent);
        });

        // Botón Cerrar Sesión
        findViewById(R.id.card_salir).setOnClickListener(v -> {
            Log.d(TAG, "Click en card_salir");
            Toast.makeText(this, "Cerrando sesión...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
