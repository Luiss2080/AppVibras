package com.example.appvibras.controlador;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import com.example.appvibras.R;
import com.example.appvibras.controlador.base.BaseCrudActivity;
import com.example.appvibras.modelo.entidades.Cliente;
import com.example.appvibras.modelo.gestores.GestorClientes;
import com.example.appvibras.vistas.clientes.AdaptadorClientes;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

public class ClientesActivity extends BaseCrudActivity<Cliente> {
    private GestorClientes gestorClientes;
    private List<Cliente> listaClientes;
    private AdaptadorClientes adaptador;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_base_crud_index;
    }

    @Override
    protected String getPageTitle() {
        return getString(R.string.titulo_clientes);
    }

    @Override
    protected String getPageSubtitle() {
        return getString(R.string.subtitulo_clientes);
    }

    @Override
    protected void initializeCrudViews() {
        gestorClientes = new GestorClientes(this);
    }

    @Override
    protected void setupCrudListeners() {
    }

    @Override
    protected List<Cliente> getItems() {
        return gestorClientes.obtenerTodos();
    }

    @Override
    protected void updateListView(List<Cliente> items) {
        listaClientes = items;
        adaptador = new AdaptadorClientes(this, listaClientes);
        listView.setAdapter(adaptador);
    }

    @Override
    protected void onAddClick() {
        mostrarDialogoCrear();
    }

    @Override
    protected void onItemClick(int position) {
        // Abrir vista de detalle del cliente
        Cliente cliente = listaClientes.get(position);
        Intent intent = new Intent(this, ClienteDetalleActivity.class);
        intent.putExtra(ClienteDetalleActivity.EXTRA_CLIENTE_ID, cliente.getId());
        startActivity(intent);
    }

    @Override
    protected void onItemLongClick(int position) {
        mostrarOpciones(listaClientes.get(position));
    }

    private void mostrarDialogoCrear() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View vista = LayoutInflater.from(this).inflate(R.layout.cliente_crear, null);
        TextInputEditText etNombre = vista.findViewById(R.id.et_crear_nombre_cliente);
        TextInputEditText etTelefono = vista.findViewById(R.id.et_crear_telefono_cliente);
        TextInputEditText etDireccion = vista.findViewById(R.id.et_crear_direccion_cliente);

        builder.setView(vista);
        builder.setPositiveButton("Guardar", (dialog, which) -> {
            if (gestorClientes.agregar(etNombre.getText().toString(), etTelefono.getText().toString(), etDireccion.getText().toString())) {
                refreshList();
                Toast.makeText(this, "Cliente guardado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void mostrarOpciones(Cliente cliente) {
        String[] opciones = {"Editar", "Eliminar"};
        new AlertDialog.Builder(this)
            .setTitle(cliente.getNombre())
            .setItems(opciones, (dialog, which) -> {
                if (which == 0) mostrarDialogoEditar(cliente);
                else if (which == 1) confirmarEliminar(cliente);
            }).show();
    }

    private void mostrarDialogoEditar(Cliente cliente) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View vista = LayoutInflater.from(this).inflate(R.layout.cliente_editar, null);
        TextInputEditText etNombre = vista.findViewById(R.id.et_editar_nombre_cliente);
        TextInputEditText etTelefono = vista.findViewById(R.id.et_editar_telefono_cliente);
        TextInputEditText etDireccion = vista.findViewById(R.id.et_editar_direccion_cliente);

        etNombre.setText(cliente.getNombre());
        etTelefono.setText(cliente.getTelefono());
        etDireccion.setText(cliente.getDireccion());

        builder.setView(vista);
        builder.setPositiveButton("Actualizar", (dialog, which) -> {
            cliente.setNombre(etNombre.getText().toString());
            cliente.setTelefono(etTelefono.getText().toString());
            cliente.setDireccion(etDireccion.getText().toString());
            gestorClientes.actualizar(cliente);
            refreshList();
        });
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void confirmarEliminar(Cliente cliente) {
        new AlertDialog.Builder(this)
            .setTitle("Confirmar")
            .setMessage("¿Desea eliminar a " + cliente.getNombre() + "?")
            .setPositiveButton("Eliminar", (dialog, which) -> {
                gestorClientes.eliminar(cliente);
                refreshList();
                Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }
}
