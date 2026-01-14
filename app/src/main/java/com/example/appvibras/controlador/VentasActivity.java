package com.example.appvibras.controlador;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appvibras.R;
import com.example.appvibras.modelo.entidades.Cliente;
import com.example.appvibras.modelo.entidades.DetalleVenta;
import com.example.appvibras.modelo.entidades.Producto;
import com.example.appvibras.modelo.entidades.Venta;
import com.example.appvibras.modelo.gestores.GestorClientes;
import com.example.appvibras.modelo.gestores.GestorProductos;
import com.example.appvibras.modelo.gestores.GestorVentas;
import com.example.appvibras.vistas.ventas.AdaptadorVentas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class VentasActivity extends AppCompatActivity {
    private ListView lvVentas;
    private FloatingActionButton fabNueva;
    private GestorVentas gestorVentas;
    private GestorClientes gestorClientes;
    private GestorProductos gestorProductos;
    private List<Venta> listaVentas;
    private AdaptadorVentas adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.venta_index);

        // Habilitar botón de regreso en ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        lvVentas = findViewById(R.id.lv_ventas_index);
        fabNueva = findViewById(R.id.fab_nueva_venta);
        gestorVentas = new GestorVentas(this);
        gestorClientes = new GestorClientes(this);
        gestorProductos = new GestorProductos(this);

        actualizarLista();

        fabNueva.setOnClickListener(v -> mostrarDialogoNuevaVenta());
    }

    private void actualizarLista() {
        listaVentas = gestorVentas.obtenerTodas();
        adaptador = new AdaptadorVentas(this, listaVentas);
        lvVentas.setAdapter(adaptador);

        // Mostrar/ocultar mensaje de no hay datos
        TextView tvNoHayVentas = findViewById(R.id.tv_no_hay_ventas);
        if (listaVentas.isEmpty()) {
            lvVentas.setVisibility(android.view.View.GONE);
            tvNoHayVentas.setVisibility(android.view.View.VISIBLE);
        } else {
            lvVentas.setVisibility(android.view.View.VISIBLE);
            tvNoHayVentas.setVisibility(android.view.View.GONE);
        }
    }

    private void mostrarDialogoNuevaVenta() {
        List<Cliente> clientes = gestorClientes.obtenerTodos();
        List<Producto> productos = gestorProductos.obtenerTodos();

        if (clientes.isEmpty() || productos.isEmpty()) {
            Toast.makeText(this, "Debe tener clientes y productos registrados", Toast.LENGTH_LONG).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View vista = LayoutInflater.from(this).inflate(R.layout.venta_crear, null);
        
        Spinner spCliente = vista.findViewById(R.id.sp_cliente_venta);
        Spinner spProducto = vista.findViewById(R.id.sp_producto_venta);
        TextInputEditText etCantidad = vista.findViewById(R.id.et_cantidad_venta);

        // Llenar spinners
        List<String> nombresClientes = new ArrayList<>();
        for (Cliente c : clientes) nombresClientes.add(c.getNombre());
        spCliente.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombresClientes));

        List<String> nombresProds = new ArrayList<>();
        for (Producto p : productos) nombresProds.add(p.getNombre() + " (Stock: " + p.getStockActual() + ")");
        spProducto.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombresProds));

        builder.setView(vista);
        builder.setPositiveButton("Vender", (dialog, which) -> {
            try {
                int idCliente = clientes.get(spCliente.getSelectedItemPosition()).getId();
                Producto prodSel = productos.get(spProducto.getSelectedItemPosition());
                int cantidad = Integer.parseInt(etCantidad.getText().toString());

                List<DetalleVenta> detalles = new ArrayList<>();
                detalles.add(new DetalleVenta(0, prodSel.getId(), cantidad, prodSel.getPrecio()));

                if (gestorVentas.realizarVenta(idCliente, detalles)) {
                    actualizarLista();
                    Toast.makeText(this, "Venta realizada con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error: Stock insuficiente", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
