package com.example.appvibras.controlador;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.appvibras.R;
import com.example.appvibras.controlador.base.BaseCrudActivity;
import com.example.appvibras.modelo.entidades.Categoria;
import com.example.appvibras.modelo.entidades.Producto;
import com.example.appvibras.modelo.gestores.GestorCategorias;
import com.example.appvibras.modelo.gestores.GestorProductos;
import com.example.appvibras.vistas.productos.AdaptadorProductos;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para la gestión de productos (CRUD).
 * Hereda de BaseCrudActivity para reutilizar funcionalidad común.
 */
public class ProductosActivity extends BaseCrudActivity<Producto> {

    private GestorProductos gestorProductos;
    private GestorCategorias gestorCategorias;
    private List<Producto> listaProductos;
    private AdaptadorProductos adaptador;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_base_crud_index;
    }

    @Override
    protected String getPageTitle() {
        return getString(R.string.titulo_productos);
    }

    @Override
    protected String getPageSubtitle() {
        return getString(R.string.subtitulo_productos);
    }

    @Override
    protected void initializeCrudViews() {
        gestorProductos = new GestorProductos(this);
        gestorCategorias = new GestorCategorias(this);
    }

    @Override
    protected void setupCrudListeners() {
        // Listeners adicionales si es necesario
    }

    @Override
    protected List<Producto> getItems() {
        return gestorProductos.obtenerTodos();
    }

    @Override
    protected void updateListView(List<Producto> items) {
        listaProductos = items;
        adaptador = new AdaptadorProductos(this, listaProductos);
        listView.setAdapter(adaptador);
    }

    @Override
    protected void onAddClick() {
        mostrarDialogoAgregar();
    }

    @Override
    protected void onItemClick(int position) {
        // Ver detalles si es necesario
    }

    @Override
    protected void onItemLongClick(int position) {
        mostrarOpciones(listaProductos.get(position));
    }

    private void mostrarDialogoAgregar() {
        List<Categoria> categorias = gestorCategorias.obtenerTodas();
        if (categorias.isEmpty()) {
            Toast.makeText(this, "Debe crear al menos una categoría primero", Toast.LENGTH_LONG).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View vista = LayoutInflater.from(this).inflate(R.layout.dialogo_producto, null);

        TextInputEditText etNombre = vista.findViewById(R.id.et_nombre_producto);
        TextInputEditText etDesc = vista.findViewById(R.id.et_descripcion_producto);
        TextInputEditText etPrecio = vista.findViewById(R.id.et_precio_producto);
        TextInputEditText etMarca = vista.findViewById(R.id.et_marca_producto);
        TextInputEditText etIndustria = vista.findViewById(R.id.et_industria_producto);
        Spinner spCategoria = vista.findViewById(R.id.sp_categoria_producto);

        List<String> nombresCat = new ArrayList<>();
        for (Categoria c : categorias) nombresCat.add(c.getNombre());
        ArrayAdapter<String> adapterCat = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresCat);
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategoria.setAdapter(adapterCat);

        builder.setView(vista);
        AlertDialog dialog = builder.create();

        // Configurar botones del layout
        vista.findViewById(R.id.btn_cancelar).setOnClickListener(v -> dialog.dismiss());
        vista.findViewById(R.id.btn_guardar).setOnClickListener(v -> {
            try {
                String nombre = etNombre.getText().toString().trim();
                String desc = etDesc.getText().toString().trim();
                String precioStr = etPrecio.getText().toString().trim();
                String marca = etMarca.getText().toString().trim();
                String industria = etIndustria.getText().toString().trim();

                if (nombre.isEmpty()) {
                    Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (precioStr.isEmpty()) {
                    Toast.makeText(this, "El precio es obligatorio", Toast.LENGTH_SHORT).show();
                    return;
                }

                double precio = Double.parseDouble(precioStr);
                int idCat = categorias.get(spCategoria.getSelectedItemPosition()).getId();

                if (gestorProductos.agregarProducto(nombre, desc, precio, idCat, marca, industria)) {
                    refreshList();
                    Toast.makeText(this, "Producto guardado exitosamente", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "El precio debe ser un número válido", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    private void mostrarOpciones(Producto producto) {
        String[] opciones = {"Editar", "Eliminar", "Cancelar"};
        new AlertDialog.Builder(this)
            .setTitle(producto.getNombre())
            .setItems(opciones, (dialog, which) -> {
                if (which == 0) mostrarDialogoEditar(producto);
                else if (which == 1) confirmarEliminar(producto);
            }).show();
    }

    private void mostrarDialogoEditar(Producto producto) {
        // TODO: Implementar edición de productos
        Toast.makeText(this, "Edición de productos - En desarrollo", Toast.LENGTH_SHORT).show();
    }

    private void confirmarEliminar(Producto producto) {
        new AlertDialog.Builder(this)
            .setTitle("Confirmar")
            .setMessage("¿Desea eliminar " + producto.getNombre() + "?")
            .setPositiveButton("Eliminar", (dialog, which) -> {
                gestorProductos.eliminarProducto(producto);
                refreshList();
                Toast.makeText(this, "Producto eliminado", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }
}
