package com.example.appvibras.controlador;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appvibras.R;
import com.example.appvibras.modelo.entidades.Compra;
import com.example.appvibras.modelo.entidades.Producto;
import com.example.appvibras.modelo.entidades.Proveedor;
import com.example.appvibras.modelo.gestores.GestorInventario;
import com.example.appvibras.modelo.gestores.GestorProductos;
import com.example.appvibras.modelo.base.BaseDatos;
import com.example.appvibras.vistas.compras.AdaptadorCompras;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class ComprasActivity extends AppCompatActivity {
    private ListView lvCompras;
    private FloatingActionButton fabNueva;
    private GestorInventario gestorInventario;
    private GestorProductos gestorProductos;
    private BaseDatos db;
    private List<Compra> listaCompras;
    private AdaptadorCompras adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compra_index);

        // Habilitar botón de regreso en ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        lvCompras = findViewById(R.id.lv_compras_index);
        fabNueva = findViewById(R.id.fab_nueva_compra);
        gestorInventario = new GestorInventario(this);
        gestorProductos = new GestorProductos(this);
        db = BaseDatos.obtenerInstancia(this);

        actualizarLista();
        fabNueva.setOnClickListener(v -> mostrarDialogoNuevaCompra());
    }

    private void actualizarLista() {
        // Corregido: Usar el nuevo CompraDao
        listaCompras = db.compraDao().obtenerTodas();
        adaptador = new AdaptadorCompras(this, listaCompras);
        lvCompras.setAdapter(adaptador);

        // Mostrar/ocultar mensaje de no hay datos
        TextView tvNoHayCompras = findViewById(R.id.tv_no_hay_compras);
        if (listaCompras.isEmpty()) {
            lvCompras.setVisibility(android.view.View.GONE);
            tvNoHayCompras.setVisibility(android.view.View.VISIBLE);
        } else {
            lvCompras.setVisibility(android.view.View.VISIBLE);
            tvNoHayCompras.setVisibility(android.view.View.GONE);
        }
    }

    private void mostrarDialogoNuevaCompra() {
        List<Proveedor> proveedores = db.proveedorDao().obtenerTodos();
        List<Producto> productos = gestorProductos.obtenerTodos();

        if (proveedores.isEmpty() || productos.isEmpty()) {
            Toast.makeText(this, "Registre proveedores y productos primero", Toast.LENGTH_LONG).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View vista = LayoutInflater.from(this).inflate(R.layout.compra_crear, null);
        Spinner spProv = vista.findViewById(R.id.sp_proveedor_compra);
        Spinner spProd = vista.findViewById(R.id.sp_producto_compra);
        TextInputEditText etCantidad = vista.findViewById(R.id.et_cantidad_compra);
        
        List<String> nProv = new ArrayList<>();
        for(Proveedor p : proveedores) nProv.add(p.getNombre());
        spProv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nProv));

        List<String> nProd = new ArrayList<>();
        for(Producto p : productos) nProd.add(p.getNombre());
        spProd.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nProd));

        builder.setView(vista);
        builder.setPositiveButton("Registrar", (dialog, which) -> {
            try {
                int idProveedor = proveedores.get(spProv.getSelectedItemPosition()).getId();
                Producto producto = productos.get(spProd.getSelectedItemPosition());
                int cantidad = Integer.parseInt(etCantidad.getText().toString());

                if (cantidad > 0) {
                    // 1. Registrar entrada en el inventario (Aumenta stock)
                    if (gestorInventario.registrarEntrada(producto.getId(), cantidad)) {
                        // 2. Registrar la compra formalmente
                        double total = producto.getPrecio() * cantidad; // Asumiendo precio de costo = precio de venta para el ejemplo
                        Compra nuevaCompra = new Compra(idProveedor, System.currentTimeMillis(), total);
                        db.compraDao().insertar(nuevaCompra);

                        actualizarLista();
                        Toast.makeText(this, "Entrada de stock registrada correctamente", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }
}
