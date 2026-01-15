package com.example.appvibras.controlador;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import com.example.appvibras.R;
import com.example.appvibras.controlador.base.BaseCrudActivity;
import com.example.appvibras.modelo.entidades.Cliente;
import com.example.appvibras.modelo.entidades.DetalleVenta;
import com.example.appvibras.modelo.entidades.Producto;
import com.example.appvibras.modelo.entidades.Venta;
import com.example.appvibras.modelo.gestores.GestorClientes;
import com.example.appvibras.modelo.gestores.GestorProductos;
import com.example.appvibras.modelo.gestores.GestorVentas;
import com.example.appvibras.vistas.ventas.AdaptadorVentas;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class VentasActivity extends BaseCrudActivity<Venta> {
    private GestorVentas gestorVentas;
    private GestorClientes gestorClientes;
    private GestorProductos gestorProductos;
    private List<Venta> listaVentas;
    private AdaptadorVentas adaptador;
    private ActivityResultLauncher<Intent> crearVentaLauncher;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_base_crud_index;
    }

    @Override
    protected String getPageTitle() {
        return getString(R.string.titulo_ventas);
    }

    @Override
    protected String getPageSubtitle() {
        return getString(R.string.subtitulo_ventas);
    }

    @Override
    protected void initializeCrudViews() {
        gestorVentas = new GestorVentas(this);
        gestorClientes = new GestorClientes(this);
        gestorProductos = new GestorProductos(this);

        crearVentaLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    refreshList();
                }
            }
        );
    }

    @Override
    protected void setupCrudListeners() {
    }

    @Override
    protected List<Venta> getItems() {
        return gestorVentas.obtenerTodas();
    }

    @Override
    protected void updateListView(List<Venta> items) {
        listaVentas = items;
        adaptador = new AdaptadorVentas(this, listaVentas);
        listView.setAdapter(adaptador);
    }

    @Override
    protected void onAddClick() {
        Intent intent = new Intent(this, VentaCrearActivity.class);
        crearVentaLauncher.launch(intent);
    }

    @Override
    protected void onItemClick(int position) {
    }

    @Override
    protected void onItemLongClick(int position) {
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
                    refreshList();
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
}
