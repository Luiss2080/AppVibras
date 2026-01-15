package com.example.appvibras.controlador;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appvibras.R;
import com.example.appvibras.modelo.base.BaseDatos;
import com.example.appvibras.modelo.entidades.Cliente;
import com.example.appvibras.modelo.entidades.Producto;
import com.example.appvibras.modelo.entidades.Venta;
import com.example.appvibras.modelo.gestores.GestorClientes;
import com.example.appvibras.modelo.gestores.GestorInventario;
import com.example.appvibras.modelo.gestores.GestorProductos;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity para crear una nueva venta.
 */
public class VentaCrearActivity extends AppCompatActivity {

    private BaseDatos db;
    private GestorInventario gestorInventario;
    private GestorClientes gestorClientes;
    private GestorProductos gestorProductos;
    private List<Cliente> clientes;
    private List<Producto> productos;

    private Spinner spCliente, spProducto;
    private TextInputEditText etCantidad;
    private MaterialButton btnGuardar;
    private ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venta_crear);

        // Inicializar gestores
        db = BaseDatos.obtenerInstancia(this);
        gestorInventario = new GestorInventario(this);
        gestorClientes = new GestorClientes(this);
        gestorProductos = new GestorProductos(this);

        // Inicializar vistas
        initializeViews();

        // Cargar datos
        cargarDatos();

        // Configurar listeners
        setupListeners();
    }

    private void initializeViews() {
        spCliente = findViewById(R.id.sp_cliente_venta);
        spProducto = findViewById(R.id.sp_producto_venta);
        etCantidad = findViewById(R.id.et_cantidad_venta);
        btnGuardar = findViewById(R.id.btn_guardar);
        btnBack = findViewById(R.id.btn_back);
    }

    private void cargarDatos() {
        clientes = gestorClientes.obtenerTodos();
        productos = gestorProductos.obtenerTodos();

        if (clientes.isEmpty() || productos.isEmpty()) {
            Toast.makeText(this, "❌ Registre clientes y productos primero", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Cargar clientes
        List<String> nombresClientes = new ArrayList<>();
        for (Cliente c : clientes) {
            nombresClientes.add(c.getNombre());
        }
        ArrayAdapter<String> adapterCliente = new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            nombresClientes
        );
        spCliente.setAdapter(adapterCliente);

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

        btnGuardar.setOnClickListener(v -> guardarVenta());
    }

    private void guardarVenta() {
        try {
            String cantidadStr = etCantidad.getText() != null ? etCantidad.getText().toString().trim() : "";

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

            int clienteId = clientes.get(spCliente.getSelectedItemPosition()).getId();
            Producto producto = productos.get(spProducto.getSelectedItemPosition());

            // Verificar stock disponible
            if (producto.getStockActual() < cantidad) {
                Toast.makeText(this, "❌ Stock insuficiente. Disponible: " + producto.getStockActual(), Toast.LENGTH_LONG).show();
                return;
            }

            // Registrar salida en el inventario
            boolean exitoInventario = gestorInventario.registrarSalida(producto.getId(), cantidad);

            if (exitoInventario) {
                // Registrar la venta
                double total = producto.getPrecio() * cantidad;
                Venta nuevaVenta = new Venta(clienteId, System.currentTimeMillis(), total);
                db.ventaDao().insertar(nuevaVenta);

                Toast.makeText(this, "✅ Venta registrada exitosamente", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(this, "❌ Error al actualizar el inventario", Toast.LENGTH_SHORT).show();
            }

        } catch (NumberFormatException e) {
            Toast.makeText(this, "❌ La cantidad debe ser un número válido", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "❌ Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}

