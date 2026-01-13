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
import com.example.appvibras.modelo.entidades.Compra;
import com.example.appvibras.modelo.entidades.Producto;
import com.example.appvibras.modelo.entidades.Proveedor;
import com.example.appvibras.modelo.gestores.GestorInventario;
import com.example.appvibras.modelo.gestores.GestorProductos;
import com.example.appvibras.modelo.base.BaseDatos;
import com.example.appvibras.vistas.compras.AdaptadorCompras;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

        lvCompras = findViewById(R.id.lv_compras_index);
        fabNueva = findViewById(R.id.fab_nueva_compra);
        gestorInventario = new GestorInventario(this);
        gestorProductos = new GestorProductos(this);
        db = BaseDatos.obtenerInstancia(this);

        actualizarLista();
        fabNueva.setOnClickListener(v -> mostrarDialogoNuevaCompra());
    }

    private void actualizarLista() {
        listaCompras = db.movimientoStockDao().obtenerTodasCompras(); // Requiere agregar este método al DAO
        adaptador = new AdaptadorCompras(this, listaCompras);
        lvCompras.setAdapter(adaptador);
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
        
        List<String> nProv = new ArrayList<>();
        for(Proveedor p : proveedores) nProv.add(p.getNombre());
        spProv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nProv));

        List<String> nProd = new ArrayList<>();
        for(Producto p : productos) nProd.add(p.getNombre());
        spProd.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nProd));

        builder.setView(vista);
        builder.setPositiveButton("Registrar", (dialog, which) -> {
            // Lógica de registro de compra y aumento de stock
        });
        builder.show();
    }
}
