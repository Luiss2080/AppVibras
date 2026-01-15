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
import com.example.appvibras.modelo.entidades.Compra;
import com.example.appvibras.modelo.entidades.Producto;
import com.example.appvibras.modelo.entidades.Proveedor;
import com.example.appvibras.modelo.gestores.GestorInventario;
import com.example.appvibras.modelo.gestores.GestorProductos;
import com.example.appvibras.modelo.base.BaseDatos;
import com.example.appvibras.vistas.compras.AdaptadorCompras;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class ComprasActivity extends BaseCrudActivity<Compra> {
    private GestorInventario gestorInventario;
    private GestorProductos gestorProductos;
    private BaseDatos db;
    private List<Compra> listaCompras;
    private AdaptadorCompras adaptador;
    private ActivityResultLauncher<Intent> crearCompraLauncher;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_base_crud_index;
    }

    @Override
    protected String getPageTitle() {
        return getString(R.string.titulo_compras);
    }

    @Override
    protected String getPageSubtitle() {
        return getString(R.string.subtitulo_compras);
    }

    @Override
    protected void initializeCrudViews() {
        gestorInventario = new GestorInventario(this);
        gestorProductos = new GestorProductos(this);
        db = BaseDatos.obtenerInstancia(this);

        crearCompraLauncher = registerForActivityResult(
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
    protected List<Compra> getItems() {
        return db.compraDao().obtenerTodas();
    }

    @Override
    protected void updateListView(List<Compra> items) {
        listaCompras = items;
        adaptador = new AdaptadorCompras(this, listaCompras);
        listView.setAdapter(adaptador);
    }

    @Override
    protected void onAddClick() {
        Intent intent = new Intent(this, CompraCrearActivity.class);
        crearCompraLauncher.launch(intent);
    }

    @Override
    protected void onItemClick(int position) {
    }

    @Override
    protected void onItemLongClick(int position) {
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
                    if (gestorInventario.registrarEntrada(producto.getId(), cantidad)) {
                        double total = producto.getPrecio() * cantidad;
                        Compra nuevaCompra = new Compra(idProveedor, System.currentTimeMillis(), total);
                        db.compraDao().insertar(nuevaCompra);

                        refreshList();
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
