package com.example.appvibras.vistas.productos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.appvibras.R;
import com.example.appvibras.modelo.entidades.Producto;
import java.util.List;

/**
 * Vista (Adaptador) para representar la lista de productos.
 */
public class AdaptadorProductos extends ArrayAdapter<Producto> {

    public AdaptadorProductos(@NonNull Context context, @NonNull List<Producto> objetos) {
        super(context, R.layout.item_producto, objetos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_producto, parent, false);
        }

        Producto producto = getItem(position);

        if (producto != null) {
            TextView tvNombre = convertView.findViewById(R.id.tv_producto_nombre);
            TextView tvStock = convertView.findViewById(R.id.tv_producto_stock);
            TextView tvPrecio = convertView.findViewById(R.id.tv_producto_precio);

            tvNombre.setText(producto.getNombre());
            tvStock.setText("Stock: " + producto.getStockActual());
            tvPrecio.setText("$" + String.format("%.2f", producto.getPrecio()));
        }

        return convertView;
    }
}
