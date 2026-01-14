package com.example.appvibras.controlador;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appvibras.R;
import com.example.appvibras.modelo.entidades.Categoria;
import com.example.appvibras.modelo.gestores.GestorCategorias;
import com.example.appvibras.vistas.categorias.AdaptadorCategorias;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

/**
 * Controlador para la gestión de categorías (CRUD).
 * Conecta la lógica del Modelo con las Vistas XML.
 */
public class CategoriasActivity extends AppCompatActivity {

    private ListView lvCategorias;
    private FloatingActionButton fabAgregar;
    private GestorCategorias gestorCategorias;
    private List<Categoria> listaCategorias;
    private AdaptadorCategorias adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categoria_index);

        // Habilitar botón de regreso en ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 1. Inicializar Vista
        lvCategorias = findViewById(R.id.lv_categorias_index);
        fabAgregar = findViewById(R.id.fab_agregar);

        // 2. Inicializar Modelo
        gestorCategorias = new GestorCategorias(this);
        
        // 3. Configurar Lista y Adaptador
        actualizarLista();

        // 4. Eventos
        fabAgregar.setOnClickListener(v -> mostrarDialogoAgregar());

        lvCategorias.setOnItemLongClickListener((parent, view, position, id) -> {
            mostrarOpciones(listaCategorias.get(position));
            return true;
        });
    }

    private void actualizarLista() {
        listaCategorias = gestorCategorias.obtenerTodas();
        adaptador = new AdaptadorCategorias(this, listaCategorias);
        lvCategorias.setAdapter(adaptador);

        // Mostrar/ocultar mensaje de no hay datos
        TextView tvNoHayDatos = findViewById(R.id.tv_no_hay_datos);
        if (listaCategorias.isEmpty()) {
            lvCategorias.setVisibility(android.view.View.GONE);
            tvNoHayDatos.setVisibility(android.view.View.VISIBLE);
        } else {
            lvCategorias.setVisibility(android.view.View.VISIBLE);
            tvNoHayDatos.setVisibility(android.view.View.GONE);
        }
    }

    private void mostrarDialogoAgregar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View vista = LayoutInflater.from(this).inflate(R.layout.categoria_crear, null);
        TextInputEditText etNombre = vista.findViewById(R.id.et_crear_nombre);
        
        builder.setView(vista);
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nombre = etNombre.getText().toString();
            if (gestorCategorias.agregarCategoria(nombre)) {
                actualizarLista();
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

        builder.setView(vista);
        builder.setPositiveButton("Actualizar", (dialog, which) -> {
            categoria.setNombre(etNombre.getText().toString());
            gestorCategorias.actualizarCategoria(categoria);
            actualizarLista();
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
                actualizarLista();
                Toast.makeText(this, "Eliminado con éxito", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
