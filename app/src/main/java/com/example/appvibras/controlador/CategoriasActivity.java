package com.example.appvibras.controlador;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.example.appvibras.R;
import com.example.appvibras.controlador.base.BaseCrudActivity;
import com.example.appvibras.modelo.entidades.Categoria;
import com.example.appvibras.modelo.gestores.GestorCategorias;
import com.example.appvibras.vistas.categorias.AdaptadorCategorias;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

/**
 * Controlador para la gestión de categorías (CRUD).
 * Hereda de BaseCrudActivity para reutilizar funcionalidad común.
 */
public class CategoriasActivity extends BaseCrudActivity<Categoria> {

    private GestorCategorias gestorCategorias;
    private List<Categoria> listaCategorias;
    private AdaptadorCategorias adaptador;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_base_crud_index;
    }

    @Override
    protected String getPageTitle() {
        return getString(R.string.titulo_categorias);
    }

    @Override
    protected String getPageSubtitle() {
        return getString(R.string.subtitulo_categorias);
    }

    @Override
    protected void initializeCrudViews() {
        gestorCategorias = new GestorCategorias(this);
    }

    @Override
    protected void setupCrudListeners() {
        // Listeners adicionales si es necesario
    }

    @Override
    protected List<Categoria> getItems() {
        return gestorCategorias.obtenerTodas();
    }

    @Override
    protected void updateListView(List<Categoria> items) {
        listaCategorias = items;
        adaptador = new AdaptadorCategorias(this, listaCategorias);
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
        mostrarOpciones(listaCategorias.get(position));
    }

    private void mostrarDialogoAgregar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View vista = LayoutInflater.from(this).inflate(R.layout.categoria_crear, null);
        TextInputEditText etNombre = vista.findViewById(R.id.et_crear_nombre);
        
        builder.setView(vista);
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nombre = etNombre.getText().toString();
            if (gestorCategorias.agregarCategoria(nombre)) {
                refreshList();
                Toast.makeText(this, "Categoría guardada", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Nombre inválido", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void mostrarOpciones(Categoria categoria) {
        String[] opciones = {"Editar", "Eliminar", "Cancelar"};
        new AlertDialog.Builder(this)
            .setTitle(categoria.getNombre())
            .setItems(opciones, (dialog, which) -> {
                if (which == 0) mostrarDialogoEditar(categoria);
                else if (which == 1) mostrarDialogoEliminar(categoria);
            }).show();
    }

    private void mostrarDialogoEditar(Categoria categoria) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View vista = LayoutInflater.from(this).inflate(R.layout.categoria_editar, null);
        TextInputEditText etNombre = vista.findViewById(R.id.et_editar_nombre);
        etNombre.setText(categoria.getNombre());

        builder.setPositiveButton("Actualizar", (dialog, which) -> {
            categoria.setNombre(etNombre.getText().toString());
            gestorCategorias.actualizarCategoria(categoria);
            refreshList();
            Toast.makeText(this, "Actualizado con éxito", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void mostrarDialogoEliminar(Categoria categoria) {
        new AlertDialog.Builder(this)
            .setTitle("Eliminar Categoría")
            .setMessage("¿Estás seguro de eliminar '" + categoria.getNombre() + "'?")
            .setPositiveButton("Eliminar", (dialog, which) -> {
                gestorCategorias.eliminarCategoria(categoria);
                refreshList();
                Toast.makeText(this, "Eliminado con éxito", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }
}
