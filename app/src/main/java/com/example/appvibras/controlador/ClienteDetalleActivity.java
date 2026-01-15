package com.example.appvibras.controlador;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appvibras.R;
import com.example.appvibras.modelo.entidades.Cliente;
import com.example.appvibras.modelo.gestores.GestorClientes;
import com.google.android.material.button.MaterialButton;

/**
 * Activity para mostrar los detalles completos de un cliente.
 * Permite visualizar toda la información y acceder a acciones de edición y eliminación.
 */
public class ClienteDetalleActivity extends AppCompatActivity {

    public static final String EXTRA_CLIENTE_ID = "cliente_id";

    private GestorClientes gestorClientes;
    private Cliente cliente;

    private TextView tvTitulo, tvSubtitulo;
    private TextView tvId, tvNombre, tvTelefono, tvDireccion;
    private MaterialButton btnEditar, btnEliminar;
    private ImageButton btnBack, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detalle);

        // Inicializar gestor
        gestorClientes = new GestorClientes(this);

        // Obtener ID del cliente desde el Intent
        int clienteId = getIntent().getIntExtra(EXTRA_CLIENTE_ID, -1);
        if (clienteId == -1) {
            Toast.makeText(this, "Error: Cliente no encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Cargar cliente desde la base de datos
        cliente = gestorClientes.obtenerPorId(clienteId);
        if (cliente == null) {
            Toast.makeText(this, "Error: Cliente no existe", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Inicializar vistas
        initializeViews();
        setupToolbar();
        setupListeners();
        displayClientData();
    }

    /**
     * Inicializa todas las vistas
     */
    private void initializeViews() {
        // Toolbar
        tvTitulo = findViewById(R.id.tv_titulo);
        tvSubtitulo = findViewById(R.id.tv_subtitulo);
        btnBack = findViewById(R.id.btn_back);
        btnHome = findViewById(R.id.btn_home);

        // Información del cliente
        tvId = findViewById(R.id.tv_id);
        tvNombre = findViewById(R.id.tv_nombre);
        tvTelefono = findViewById(R.id.tv_telefono);
        tvDireccion = findViewById(R.id.tv_direccion);

        // Botones de acción
        btnEditar = findViewById(R.id.btn_editar);
        btnEliminar = findViewById(R.id.btn_eliminar);
    }

    /**
     * Configura el toolbar
     */
    private void setupToolbar() {
        tvTitulo.setText("Detalles del Cliente");
        tvSubtitulo.setText("Información completa");
    }

    /**
     * Configura los listeners de los botones
     */
    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MenuPrincipalActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        btnEditar.setOnClickListener(v -> editarCliente());
        btnEliminar.setOnClickListener(v -> confirmarEliminar());
    }

    /**
     * Muestra la información del cliente en la interfaz
     */
    private void displayClientData() {
        tvId.setText("ID: #" + cliente.getId());
        tvNombre.setText(cliente.getNombre());

        String telefono = cliente.getTelefono();
        tvTelefono.setText(telefono != null && !telefono.isEmpty() ? telefono : "N/A");

        String direccion = cliente.getDireccion();
        tvDireccion.setText(direccion != null && !direccion.isEmpty() ? direccion : "N/A");
    }

    /**
     * Abre un diálogo para editar el cliente
     */
    private void editarCliente() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View vista = LayoutInflater.from(this).inflate(R.layout.cliente_editar, null);

        TextInputEditText etNombre = vista.findViewById(R.id.et_editar_nombre_cliente);
        TextInputEditText etTelefono = vista.findViewById(R.id.et_editar_telefono_cliente);
        TextInputEditText etDireccion = vista.findViewById(R.id.et_editar_direccion_cliente);

        etNombre.setText(cliente.getNombre());
        etTelefono.setText(cliente.getTelefono());
        etDireccion.setText(cliente.getDireccion());

        builder.setView(vista);
        builder.setPositiveButton("Actualizar", (dialog, which) -> {
            String nuevoNombre = etNombre.getText() != null ? etNombre.getText().toString().trim() : "";
            if (nuevoNombre.isEmpty()) {
                Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            cliente.setNombre(nuevoNombre);
            cliente.setTelefono(etTelefono.getText() != null ? etTelefono.getText().toString().trim() : "");
            cliente.setDireccion(etDireccion.getText() != null ? etDireccion.getText().toString().trim() : "");

            gestorClientes.actualizar(cliente);
            displayClientData();
            Toast.makeText(this, "Cliente actualizado exitosamente", Toast.LENGTH_SHORT).show();

            // Notificar que hubo cambios
            setResult(RESULT_OK);
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    /**
     * Muestra un diálogo de confirmación para eliminar el cliente
     */
    private void confirmarEliminar() {
        new AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage("¿Está seguro que desea eliminar a \"" + cliente.getNombre() + "\"?\n\n" +
                       "Esta acción no se puede deshacer.")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Eliminar", (dialog, which) -> eliminarCliente())
            .setNegativeButton("Cancelar", null)
            .show();
    }

    /**
     * Elimina el cliente de la base de datos
     */
    private void eliminarCliente() {
        try {
            gestorClientes.eliminar(cliente);
            Toast.makeText(this, "Cliente eliminado exitosamente", Toast.LENGTH_SHORT).show();

            // Volver a la lista de clientes
            setResult(RESULT_OK);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Error al eliminar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

