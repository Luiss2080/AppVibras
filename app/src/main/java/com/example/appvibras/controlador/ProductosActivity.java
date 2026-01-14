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
import com.example.appvibras.modelo.entidades.Categoria;
import com.example.appvibras.modelo.entidades.Producto;
import com.example.appvibras.modelo.gestores.GestorCategorias;
import com.example.appvibras.modelo.gestores.GestorProductos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para la gestión de productos.
 */
public class ProductosActivity extends AppCompatActivity {

    private ListView lvProductos;
    private FloatingActionButton fabAgregar;
    private GestorProductos gestorProductos;
    private GestorCategorias gestorCategorias;
    private List<Producto> listaProductos;
    private ArrayAdapter<String> adaptador;
    private List<String> infoProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        // Configurar navegación
        com.example.appvibras.utils.NavigationHelper.setupNavigationButtons(this);

        lvProductos = findViewById(R.id.lv_productos);
        fabAgregar = findViewById(R.id.fab_agregar_producto);

        gestorProductos = new GestorProductos(this);
        gestorCategorias = new GestorCategorias(this);
        infoProductos = new ArrayList<>();
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, infoProductos);
        lvProductos.setAdapter(adaptador);

        actualizarLista();

        fabAgregar.setOnClickListener(v -> mostrarDialogoAgregar());
        
        lvProductos.setOnItemLongClickListener((parent, view, position, id) -> {
            mostrarOpciones(listaProductos.get(position));
            return true;
        });
    }

    private void actualizarLista() {
        listaProductos = gestorProductos.obtenerTodos();
        infoProductos.clear();
        for (Producto p : listaProductos) {
            infoProductos.add(p.getNombre() + " - $" + p.getPrecio() + " (Stock: " + p.getStockActual() + ")");
        }
        adaptador.notifyDataSetChanged();

        // Mostrar/ocultar mensaje de no hay datos
        TextView tvNoHayProductos = findViewById(R.id.tv_no_hay_productos);
        ListView lvProductos = findViewById(R.id.lv_productos);
        if (listaProductos.isEmpty()) {
            lvProductos.setVisibility(android.view.View.GONE);
            tvNoHayProductos.setVisibility(android.view.View.VISIBLE);
        } else {
            lvProductos.setVisibility(android.view.View.VISIBLE);
            tvNoHayProductos.setVisibility(android.view.View.GONE);
        }
    }

    private void mostrarDialogoAgregar() {
        List<Categoria> categorias = gestorCategorias.obtenerTodas();
        if (categorias.isEmpty()) {
            Toast.makeText(this, "Debe crear al menos una categoría primero", Toast.LENGTH_LONG).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nuevo Producto");

        View vista = LayoutInflater.from(this).inflate(R.layout.dialogo_producto, null);
        TextInputEditText etNombre = vista.findViewById(R.id.et_nombre_producto);
        TextInputEditText etDesc = vista.findViewById(R.id.et_descripcion_producto);
        TextInputEditText etPrecio = vista.findViewById(R.id.et_precio_producto);
        Spinner spCategoria = vista.findViewById(R.id.sp_categoria_producto);

        List<String> nombresCat = new ArrayList<>();
        for (Categoria c : categorias) nombresCat.add(c.getNombre());
        ArrayAdapter<String> adapterCat = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresCat);
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapterCat);

        builder.setView(vista);
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            try {
                String nombre = etNombre.getText().toString();
                String desc = etDesc.getText().toString();
                double precio = Double.parseDouble(etPrecio.getText().toString());
                int idCat = categorias.get(spCategoria.getSelectedItemPosition()).getId();

                if (gestorProductos.agregarProducto(nombre, desc, precio, idCat)) {
                    actualizarLista();
                    Toast.makeText(this, "Producto guardado", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Datos inválidos", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void mostrarOpciones(Producto producto) {
        new AlertDialog.Builder(this)
            .setTitle(producto.getNombre())
            .setItems(new String[]{"Eliminar", "Cancelar"}, (dialog, which) -> {
                if (which == 0) {
                    gestorProductos.eliminarProducto(producto);
                    actualizarLista();
                }
            }).show();
    }
}
