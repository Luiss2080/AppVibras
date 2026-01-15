package com.example.appvibras.controlador;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appvibras.R;
import com.example.appvibras.modelo.entidades.Categoria;
import com.example.appvibras.modelo.entidades.Producto;
import com.example.appvibras.modelo.gestores.GestorCategorias;
import com.example.appvibras.modelo.gestores.GestorProductos;
import com.google.android.material.button.MaterialButton;

/**
 * Activity para mostrar los detalles completos de un producto.
 * Permite visualizar toda la información y acceder a acciones de edición y eliminación.
 */
public class ProductoDetalleActivity extends AppCompatActivity {

    public static final String EXTRA_PRODUCTO_ID = "producto_id";

    private GestorProductos gestorProductos;
    private GestorCategorias gestorCategorias;
    private Producto producto;

    private TextView tvTitulo, tvSubtitulo;
    private TextView tvId, tvNombre, tvDescripcion, tvPrecio, tvStock, tvCategoria, tvMarca, tvIndustria;
    private MaterialButton btnEditar, btnEliminar;
    private ImageButton btnBack, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_detalle);

        // Inicializar gestores
        gestorProductos = new GestorProductos(this);
        gestorCategorias = new GestorCategorias(this);

        // Obtener ID del producto desde el Intent
        int productoId = getIntent().getIntExtra(EXTRA_PRODUCTO_ID, -1);
        if (productoId == -1) {
            Toast.makeText(this, "Error: Producto no encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Cargar producto desde la base de datos
        producto = gestorProductos.obtenerPorId(productoId);
        if (producto == null) {
            Toast.makeText(this, "Error: Producto no existe", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Inicializar vistas
        initializeViews();
        setupToolbar();
        setupListeners();
        displayProductData();
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

        // Información del producto
        tvId = findViewById(R.id.tv_id);
        tvNombre = findViewById(R.id.tv_nombre);
        tvDescripcion = findViewById(R.id.tv_descripcion);
        tvPrecio = findViewById(R.id.tv_precio);
        tvStock = findViewById(R.id.tv_stock);
        tvCategoria = findViewById(R.id.tv_categoria);
        tvMarca = findViewById(R.id.tv_marca);
        tvIndustria = findViewById(R.id.tv_industria);

        // Botones de acción
        btnEditar = findViewById(R.id.btn_editar);
        btnEliminar = findViewById(R.id.btn_eliminar);
    }

    /**
     * Configura el toolbar
     */
    private void setupToolbar() {
        tvTitulo.setText("Detalles del Producto");
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

        btnEditar.setOnClickListener(v -> editarProducto());
        btnEliminar.setOnClickListener(v -> confirmarEliminar());
    }

    /**
     * Muestra la información del producto en la interfaz
     */
    private void displayProductData() {
        tvId.setText("ID: #" + producto.getId());
        tvNombre.setText(producto.getNombre());

        // Descripción
        String descripcion = producto.getDescripcion();
        tvDescripcion.setText(descripcion != null && !descripcion.isEmpty() ?
            descripcion : "Sin descripción");

        // Precio
        tvPrecio.setText(String.format("$%.2f", producto.getPrecio()));

        // Stock
        int stock = producto.getStockActual();
        tvStock.setText(stock + " unidades");

        // Categoría
        Categoria categoria = gestorCategorias.obtenerPorId(producto.getIdCategoria());
        tvCategoria.setText(categoria != null ? categoria.getNombre() : "Sin categoría");

        // Marca
        String marca = producto.getMarca();
        tvMarca.setText(marca != null && !marca.isEmpty() ? marca : "N/A");

        // Industria
        String industria = producto.getIndustria();
        tvIndustria.setText(industria != null && !industria.isEmpty() ? industria : "N/A");
    }

    /**
     * Abre la pantalla de edición del producto
     */
    private void editarProducto() {
        // TODO: Implementar navegación a pantalla de edición
        Toast.makeText(this, "Función de edición en desarrollo", Toast.LENGTH_SHORT).show();
    }

    /**
     * Muestra un diálogo de confirmación para eliminar el producto
     */
    private void confirmarEliminar() {
        new AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage("¿Está seguro que desea eliminar \"" + producto.getNombre() + "\"?\n\n" +
                       "Esta acción no se puede deshacer.")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Eliminar", (dialog, which) -> eliminarProducto())
            .setNegativeButton("Cancelar", null)
            .show();
    }

    /**
     * Elimina el producto de la base de datos
     */
    private void eliminarProducto() {
        try {
            gestorProductos.eliminarProducto(producto);
            Toast.makeText(this, "Producto eliminado exitosamente", Toast.LENGTH_SHORT).show();

            // Volver a la lista de productos
            setResult(RESULT_OK);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Error al eliminar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

