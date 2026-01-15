package com.example.appvibras.controlador;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appvibras.R;
import com.example.appvibras.modelo.gestores.GestorCategorias;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Activity para crear una nueva categoría.
 */
public class CategoriaCrearActivity extends AppCompatActivity {

    private GestorCategorias gestorCategorias;

    private TextInputEditText etNombre;
    private MaterialButton btnGuardar;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoria_crear);

        // Inicializar gestor
        gestorCategorias = new GestorCategorias(this);

        // Inicializar vistas
        initializeViews();

        // Configurar listeners
        setupListeners();
    }

    private void initializeViews() {
        etNombre = findViewById(R.id.et_crear_nombre);
        btnGuardar = findViewById(R.id.btn_guardar);
        btnBack = findViewById(R.id.btn_back);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnGuardar.setOnClickListener(v -> guardarCategoria());
    }

    private void guardarCategoria() {
        String nombre = etNombre.getText().toString().trim();

        // Validación
        if (nombre.isEmpty()) {
            etNombre.setError("Ingrese el nombre de la categoría");
            etNombre.requestFocus();
            return;
        }

        // Guardar categoría
        boolean exito = gestorCategorias.agregarCategoria(nombre);

        if (exito) {
            Toast.makeText(this, "✅ Categoría guardada exitosamente", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "❌ Error al guardar la categoría", Toast.LENGTH_SHORT).show();
        }
    }
}

