package com.example.appvibras.controlador;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appvibras.R;
import com.example.appvibras.modelo.entidades.Producto;
import com.example.appvibras.modelo.entidades.Proveedor;
import com.example.appvibras.modelo.gestores.GestorProductos;
import com.example.appvibras.modelo.persistencia.DatabaseHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity para crear una nueva compra/entrada de stock.
 */
public class CompraCrearActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private GestorProductos gestorProductos;
    private List<Proveedor> proveedores;
    private List<Producto> productos;

    private Spinner spProveedor, spProducto;
    private TextInputEditText etCantidad;
    private MaterialButton btnGuardar;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compra_crear);

        // Inicializar gestores
        db = new DatabaseHelper(this);
        gestorProductos = new GestorProductos(this);

        // Inicializar vistas
        initializeViews();

        // Cargar datos
        cargarDatos();

        // Configurar listeners
        setupListeners();
    }

    private void initializeViews() {
        spProveedor = findViewById(R.id.sp_proveedor_compra);
        spProducto = findViewById(R.id.sp_producto_compra);
        etCantidad = findViewById(R.id.et_cantidad_compra);
        btnGuardar = findViewById(R.id.btn_guardar);
        btnBack = findViewById(R.id.btn_back);
    }

    private void cargarDatos() {
        proveedores = db.proveedorDao().obtenerTodos();
        productos = gestorProductos.obtenerTodos();

        if (proveedores.isEmpty() || productos.isEmpty()) {
            Toast.makeText(this, "❌ Registre proveedores y productos primero", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Cargar proveedores
        List<String> nombresProveedores = new ArrayList<>();
        for (Proveedor p : proveedores) {
            nombresProveedores.add(p.getNombre());
        }
        ArrayAdapter<String> adapterProv = new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            nombresProveedores
        );
        spProveedor.setAdapter(adapterProv);

        // Cargar productos
        List<String> nombresProductos = new ArrayList<>();
        for (Producto p : productos) {
            nombresProductos.add(p.getNombre());
        }
        ArrayAdapter<String> adapterProd = new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            nombresProductos
        );
        spProducto.setAdapter(adapterProd);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        btnGuardar.setOnClickListener(v -> guardarCompra());
    }

    private void guardarCompra() {
        try {
            String cantidadStr = etCantidad.getText().toString().trim();

            if (cantidadStr.isEmpty()) {
                etCantidad.setError("Ingrese la cantidad");
                etCantidad.requestFocus();
                return;
            }

            int cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) {
                etCantidad.setError("La cantidad debe ser mayor a 0");
                etCantidad.requestFocus();
                return;
            }

            int proveedorId = proveedores.get(spProveedor.getSelectedItemPosition()).getId();
            int productoId = productos.get(spProducto.getSelectedItemPosition()).getId();

            // Registrar compra
            boolean exito = db.compraDao().insertar(proveedorId, productoId, cantidad);

            if (exito) {
                Toast.makeText(this, "✅ Compra registrada exitosamente", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "❌ Error al registrar la compra", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "❌ La cantidad debe ser un número válido", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "❌ Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

