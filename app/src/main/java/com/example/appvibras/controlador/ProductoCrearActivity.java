package com.example.appvibras.controlador;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appvibras.R;
import com.example.appvibras.modelo.entidades.Categoria;
import com.example.appvibras.modelo.gestores.GestorCategorias;
import com.example.appvibras.modelo.gestores.GestorProductos;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity para crear un nuevo producto.
 */
public class ProductoCrearActivity extends AppCompatActivity {

    private GestorProductos gestorProductos;
    private GestorCategorias gestorCategorias;
    private List<Categoria> categorias;

    private TextInputEditText etNombre, etDescripcion, etPrecio, etMarca, etIndustria;
    private Spinner spCategoria;
    private MaterialButton btnGuardar;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.producto_crear);

        // Inicializar gestores
        gestorProductos = new GestorProductos(this);
        gestorCategorias = new GestorCategorias(this);

        // Inicializar vistas
        initializeViews();

        // Cargar categorías
        cargarCategorias();

        // Configurar listeners
        setupListeners();
    }

    private void initializeViews() {
        etNombre = findViewById(R.id.et_nombre_producto);
        etDescripcion = findViewById(R.id.et_descripcion_producto);
        etPrecio = findViewById(R.id.et_precio_producto);
        etMarca = findViewById(R.id.et_marca_producto);
        etIndustria = findViewById(R.id.et_industria_producto);
        spCategoria = findViewById(R.id.sp_categoria_producto);
        btnGuardar = findViewById(R.id.btn_guardar);
        btnBack = findViewById(R.id.btn_back);
    }

    private void cargarCategorias() {
        categorias = gestorCategorias.obtenerTodas();

        if (categorias.isEmpty()) {
            Toast.makeText(this, "No hay categorías. Por favor, cree una primero.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        List<String> nombresCategorias = new ArrayList<>();
        for (Categoria c : categorias) {
            nombresCategorias.add(c.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_item,
            nombresCategorias
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapter);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnGuardar.setOnClickListener(v -> guardarProducto());
    }

    private void guardarProducto() {
        try {
            String nombre = etNombre.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();
            String precioStr = etPrecio.getText().toString().trim();
            String marca = etMarca.getText().toString().trim();
            String industria = etIndustria.getText().toString().trim();

            // Validaciones
            if (nombre.isEmpty()) {
                etNombre.setError("Ingrese el nombre");
                etNombre.requestFocus();
                return;
            }

            if (descripcion.isEmpty()) {
                etDescripcion.setError("Ingrese la descripción");
                etDescripcion.requestFocus();
                return;
            }

            if (precioStr.isEmpty()) {
                etPrecio.setError("Ingrese el precio");
                etPrecio.requestFocus();
                return;
            }

            double precio = Double.parseDouble(precioStr);
            if (precio < 0) {
                etPrecio.setError("El precio debe ser positivo");
                etPrecio.requestFocus();
                return;
            }

            int categoriaIndex = spCategoria.getSelectedItemPosition();
            int categoriaId = categorias.get(categoriaIndex).getId();

            // Guardar producto
            boolean exito = gestorProductos.agregarProducto(
                nombre,
                descripcion,
                precio,
                categoriaId,
                marca,
                industria
            );

            if (exito) {
                Toast.makeText(this, "✅ Producto guardado exitosamente", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "❌ Error al guardar el producto", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "❌ El precio debe ser un número válido", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "❌ Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

