package com.example.appvibras.vistas.clientes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.appvibras.R;
import com.example.appvibras.modelo.entidades.Cliente;
import java.util.List;

/**
 * Vista (Adaptador) para representar la lista de clientes.
 */
public class AdaptadorClientes extends ArrayAdapter<Cliente> {

    public AdaptadorClientes(@NonNull Context context, @NonNull List<Cliente> objetos) {
        super(context, R.layout.item_cliente, objetos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cliente, parent, false);
        }

        Cliente cliente = getItem(position);

        if (cliente != null) {
            TextView tvNombre = convertView.findViewById(R.id.tv_cliente_nombre);
            TextView tvTelefono = convertView.findViewById(R.id.tv_cliente_telefono);
            TextView tvEmail = convertView.findViewById(R.id.tv_cliente_email);

            tvNombre.setText(cliente.getNombre());
            tvTelefono.setText("📞 " + cliente.getTelefono());
            tvEmail.setText("📍 " + (cliente.getDireccion() != null && !cliente.getDireccion().isEmpty()
                ? cliente.getDireccion() : "Sin dirección"));
        }

        return convertView;
    }
}
