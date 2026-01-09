package com.example.appvibras.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appvibras.MainActivity;
import com.example.appvibras.R;

/**
 * Controlador para el Menú Principal.
 */
public class MenuPrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        findViewById(R.id.btn_salir).setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Los demás botones se implementarán en los siguientes pasos
        // btn_categorias, btn_productos, btn_entradas, btn_salidas, btn_reportes
    }
}
