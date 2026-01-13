package com.example.appvibras.vistas.productos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.appvibras.modelo.entidades.Producto;
import java.util.List;

/**
 * Vista (Adaptador) para representar la lista de productos.
 */
public class AdaptadorProductos extends ArrayAdapter<Producto> {

    public AdaptadorProductos(@NonNull Context context, @NonNull List<Producto> objetos) {
        super(context, android.R.layout.simple_list_item_2, objetos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        Producto producto = getItem(position);
        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);

        if (producto != null) {
            text1.setText(producto.getNombre());
            text2.setText("$" + producto.getPrecio() + " | Stock: " + producto.getStockActual());
        }

        return convertView;
    }
}
