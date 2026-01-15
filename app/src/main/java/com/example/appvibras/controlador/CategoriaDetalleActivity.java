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
import com.example.appvibras.modelo.entidades.Categoria;
import com.example.appvibras.modelo.gestores.GestorCategorias;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Activity para mostrar los detalles completos de una categoría.
 * Permite visualizar toda la información y acceder a acciones de edición y eliminación.
 */
public class CategoriaDetalleActivity extends AppCompatActivity {

    public static final String EXTRA_CATEGORIA_ID = "categoria_id";

    private GestorCategorias gestorCategorias;
    private Categoria categoria;

    private TextView tvTitulo, tvSubtitulo;
    private TextView tvId, tvNombre;
    private MaterialButton btnEditar, btnEliminar;
    private ImageButton btnBack, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria_detalle);

        // Inicializar gestor
        gestorCategorias = new GestorCategorias(this);

        // Obtener ID de la categoría desde el Intent
        int categoriaId = getIntent().getIntExtra(EXTRA_CATEGORIA_ID, -1);
        if (categoriaId == -1) {
            Toast.makeText(this, "Error: Categoría no encontrada", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Cargar categoría desde la base de datos
        categoria = gestorCategorias.obtenerPorId(categoriaId);
        if (categoria == null) {
            Toast.makeText(this, "Error: Categoría no existe", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Inicializar vistas
        initializeViews();
        setupToolbar();
        setupListeners();
        displayCategoryData();
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

        // Información de la categoría
        tvId = findViewById(R.id.tv_id);
        tvNombre = findViewById(R.id.tv_nombre);

        // Botones de acción
        btnEditar = findViewById(R.id.btn_editar);
        btnEliminar = findViewById(R.id.btn_eliminar);
    }

    /**
     * Configura el toolbar
     */
    private void setupToolbar() {
        tvTitulo.setText("Detalles de Categoría");
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

        btnEditar.setOnClickListener(v -> editarCategoria());
        btnEliminar.setOnClickListener(v -> confirmarEliminar());
    }

    /**
     * Muestra la información de la categoría en la interfaz
     */
    private void displayCategoryData() {
        tvId.setText("ID: #" + categoria.getId());
        tvNombre.setText(categoria.getNombre());
    }

    /**
     * Abre un diálogo para editar la categoría
     */
    private void editarCategoria() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View vista = LayoutInflater.from(this).inflate(R.layout.categoria_editar, null);
        TextInputEditText etNombre = vista.findViewById(R.id.et_editar_nombre);
        etNombre.setText(categoria.getNombre());

        builder.setView(vista);
        AlertDialog dialog = builder.create();

        vista.findViewById(R.id.btn_cancelar_edicion).setOnClickListener(v -> dialog.dismiss());
        vista.findViewById(R.id.btn_guardar_edicion).setOnClickListener(v -> {
            String nuevoNombre = etNombre.getText().toString().trim();
            if (nuevoNombre.isEmpty()) {
                Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show();
                return;
            }

            categoria.setNombre(nuevoNombre);
            gestorCategorias.actualizarCategoria(categoria);
            displayCategoryData();
            Toast.makeText(this, "Categoría actualizada exitosamente", Toast.LENGTH_SHORT).show();
            dialog.dismiss();

            // Notificar que hubo cambios
            setResult(RESULT_OK);
        });

        dialog.show();
    }

    /**
     * Muestra un diálogo de confirmación para eliminar la categoría
     */
    private void confirmarEliminar() {
        new AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage("¿Está seguro que desea eliminar \"" + categoria.getNombre() + "\"?\n\n" +
                       "Esta acción eliminará también todos los productos asociados.")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Eliminar", (dialog, which) -> eliminarCategoria())
            .setNegativeButton("Cancelar", null)
            .show();
    }

    /**
     * Elimina la categoría de la base de datos
     */
    private void eliminarCategoria() {
        try {
            gestorCategorias.eliminarCategoria(categoria);
            Toast.makeText(this, "Categoría eliminada exitosamente", Toast.LENGTH_SHORT).show();

            // Volver a la lista de categorías
            setResult(RESULT_OK);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Error al eliminar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

