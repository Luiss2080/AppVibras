package com.example.appvibras.controlador;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.appvibras.R;
import com.example.appvibras.modelo.entidades.Cliente;
import com.example.appvibras.modelo.gestores.GestorClientes;
import com.example.appvibras.vistas.clientes.AdaptadorClientes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

public class ClientesActivity extends AppCompatActivity {
    private ListView lvClientes;
    private FloatingActionButton fabAgregar;
    private GestorClientes gestorClientes;
    private List<Cliente> listaClientes;
    private AdaptadorClientes adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cliente_index);

        lvClientes = findViewById(R.id.lv_clientes_index);
        fabAgregar = findViewById(R.id.fab_agregar_cliente);
        gestorClientes = new GestorClientes(this);

        actualizarLista();

        fabAgregar.setOnClickListener(v -> mostrarDialogoCrear());
        lvClientes.setOnItemLongClickListener((parent, view, position, id) -> {
            mostrarOpciones(listaClientes.get(position));
            return true;
        });
    }

    private void actualizarLista() {
        listaClientes = gestorClientes.obtenerTodos();
        adaptador = new AdaptadorClientes(this, listaClientes);
        lvClientes.setAdapter(adaptador);
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
                actualizarLista();
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
            actualizarLista();
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
                actualizarLista();
            })
            .setNegativeButton("Cancelar", null)
            .show();
    }
}
