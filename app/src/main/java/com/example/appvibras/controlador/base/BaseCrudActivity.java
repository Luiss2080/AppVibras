package com.example.appvibras.controlador.base;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appvibras.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

/**
 * Activity base para operaciones CRUD.
 * Todas las activities de gestión (Categorías, Clientes, Productos, etc.) heredan de esta clase.
 *
 * @param <T> Tipo de entidad que se gestionará (Categoria, Cliente, Producto, etc.)
 */
public abstract class BaseCrudActivity<T> extends AppCompatActivity {

    protected ListView listView;
    protected FloatingActionButton fabAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());

        // Configurar navegación
        com.example.appvibras.utils.NavigationHelper.setupNavigationButtons(this);

        // Configurar títulos del toolbar
        setupToolbar();

        // Inicializar vistas comunes
        initializeBaseViews();

        // Inicializar vistas específicas del CRUD
        initializeCrudViews();

        // Configurar listeners base
        setupBaseListeners();

        // Configurar listeners específicos
        setupCrudListeners();

        // Cargar datos iniciales
        refreshList();
    }

    /**
     * Configura el toolbar con título y subtítulo
     */
    private void setupToolbar() {
        android.widget.TextView tvTitulo = findViewById(R.id.tv_titulo);
        android.widget.TextView tvSubtitulo = findViewById(R.id.tv_subtitulo);

        if (tvTitulo != null) {
            tvTitulo.setText(getPageTitle());
        }
        if (tvSubtitulo != null) {
            tvSubtitulo.setText(getPageSubtitle());
        }
    }

    /**
     * Inicializa las vistas base (ListView, FAB)
     */
    private void initializeBaseViews() {
        listView = findViewById(R.id.lv_items);
        fabAgregar = findViewById(R.id.fab_agregar);
    }

    /**
     * Configura los listeners base
     */
    private void setupBaseListeners() {
        fabAgregar.setOnClickListener(v -> onAddClick());

        listView.setOnItemClickListener((parent, view, position, id) -> onItemClick(position));

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            onItemLongClick(position);
            return true;
        });
    }

    /**
     * Refresca la lista de elementos
     */
    protected void refreshList() {
        List<T> items = getItems();
        updateListView(items);
        updateEmptyState(items.isEmpty());
    }

    /**
     * Actualiza el estado vacío de la lista
     */
    private void updateEmptyState(boolean isEmpty) {
        // Buscar vista de estado vacío en el layout incluido
        android.view.View emptyStateView = findViewById(R.id.layout_empty_state);
        if (emptyStateView != null) {
            emptyStateView.setVisibility(isEmpty ? android.view.View.VISIBLE : android.view.View.GONE);
        }
        listView.setVisibility(isEmpty ? android.view.View.GONE : android.view.View.VISIBLE);
    }

    // ========== MÉTODOS ABSTRACTOS QUE DEBEN IMPLEMENTAR LAS SUBCLASES ==========

    /**
     * @return ID del recurso de layout a usar (ej: R.layout.activity_base_crud_index)
     */
    protected abstract int getLayoutResourceId();

    /**
     * @return Título de la página
     */
    protected abstract String getPageTitle();

    /**
     * @return Subtítulo de la página
     */
    protected abstract String getPageSubtitle();

    /**
     * Inicializa las vistas específicas del CRUD (gestores, etc.)
     */
    protected abstract void initializeCrudViews();

    /**
     * Configura listeners específicos del CRUD
     */
    protected abstract void setupCrudListeners();

    /**
     * Obtiene la lista de elementos desde el gestor
     * @return Lista de elementos
     */
    protected abstract List<T> getItems();

    /**
     * Actualiza la vista de lista con los elementos
     * @param items Lista de elementos a mostrar
     */
    protected abstract void updateListView(List<T> items);

    /**
     * Maneja el click en el botón agregar
     */
    protected abstract void onAddClick();

    /**
     * Maneja el click en un elemento de la lista
     * @param position Posición del elemento clickeado
     */
    protected abstract void onItemClick(int position);

    /**
     * Maneja el click largo en un elemento de la lista
     * @param position Posición del elemento
     */
    protected abstract void onItemLongClick(int position);
}

