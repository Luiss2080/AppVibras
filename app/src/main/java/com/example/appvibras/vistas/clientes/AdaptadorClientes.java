package com.example.appvibras.vistas.clientes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.appvibras.modelo.entidades.Cliente;
import java.util.List;

/**
 * Vista (Adaptador) para representar la lista de clientes.
 */
public class AdaptadorClientes extends ArrayAdapter<Cliente> {

    public AdaptadorClientes(@NonNull Context context, @NonNull List<Cliente> objetos) {
        super(context, android.R.layout.simple_list_item_2, objetos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        Cliente cliente = getItem(position);
        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);

        if (cliente != null) {
            text1.setText(cliente.getNombre());
            text2.setText("Tel: " + cliente.getTelefono() + " | Dir: " + cliente.getDireccion());
        }

        return convertView;
    }
}
