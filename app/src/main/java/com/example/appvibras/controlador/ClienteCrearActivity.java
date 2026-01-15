package com.example.appvibras.controlador;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appvibras.R;
import com.example.appvibras.modelo.gestores.GestorClientes;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Activity para crear un nuevo cliente.
 */
public class ClienteCrearActivity extends AppCompatActivity {

    private GestorClientes gestorClientes;

    private TextInputEditText etNombre, etTelefono, etDireccion;
    private MaterialButton btnGuardar;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_crear);

        // Inicializar gestor
        gestorClientes = new GestorClientes(this);

        // Inicializar vistas
        initializeViews();

        // Configurar listeners
        setupListeners();
    }

    private void initializeViews() {
        etNombre = findViewById(R.id.et_crear_nombre_cliente);
        etTelefono = findViewById(R.id.et_crear_telefono_cliente);
        etDireccion = findViewById(R.id.et_crear_direccion_cliente);
        btnGuardar = findViewById(R.id.btn_guardar);
        btnBack = findViewById(R.id.btn_back);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnGuardar.setOnClickListener(v -> guardarCliente());
    }

    private void guardarCliente() {
        String nombre = etNombre.getText() != null ? etNombre.getText().toString().trim() : "";
        String telefono = etTelefono.getText() != null ? etTelefono.getText().toString().trim() : "";
        String direccion = etDireccion.getText() != null ? etDireccion.getText().toString().trim() : "";

        // Validaciones
        if (nombre.isEmpty()) {
            etNombre.setError("Ingrese el nombre del cliente");
            etNombre.requestFocus();
            return;
        }

        if (telefono.isEmpty()) {
            etTelefono.setError("Ingrese el teléfono");
            etTelefono.requestFocus();
            return;
        }

        if (direccion.isEmpty()) {
            etDireccion.setError("Ingrese la dirección");
            etDireccion.requestFocus();
            return;
        }

        // Guardar cliente
        boolean exito = gestorClientes.agregar(nombre, telefono, direccion);

        if (exito) {
            Toast.makeText(this, "✅ Cliente guardado exitosamente", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(this, "❌ Error al guardar el cliente", Toast.LENGTH_SHORT).show();
        }
    }
}

