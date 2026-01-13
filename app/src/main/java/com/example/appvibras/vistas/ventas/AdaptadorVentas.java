package com.example.appvibras.vistas.ventas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.appvibras.modelo.entidades.Venta;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdaptadorVentas extends ArrayAdapter<Venta> {

    public AdaptadorVentas(@NonNull Context context, @NonNull List<Venta> objetos) {
        super(context, android.R.layout.simple_list_item_2, objetos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        Venta venta = getItem(position);
        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);

        if (venta != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            String fechaStr = sdf.format(new Date(venta.getFecha()));
            text1.setText("Venta #" + venta.getId() + " - Total: $" + venta.getTotal());
            text2.setText("Fecha: " + fechaStr);
        }

        return convertView;
    }
}
