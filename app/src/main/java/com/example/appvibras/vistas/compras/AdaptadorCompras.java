package com.example.appvibras.vistas.compras;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.appvibras.modelo.entidades.Compra;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdaptadorCompras extends ArrayAdapter<Compra> {

    public AdaptadorCompras(@NonNull Context context, @NonNull List<Compra> objetos) {
        super(context, android.R.layout.simple_list_item_2, objetos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        Compra compra = getItem(position);
        TextView text1 = convertView.findViewById(android.R.id.text1);
        TextView text2 = convertView.findViewById(android.R.id.text2);

        if (compra != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            String fechaStr = sdf.format(new Date(compra.getFecha()));
            text1.setText("Compra #" + compra.getId() + " - Total: $" + compra.getTotal());
            text2.setText("Fecha: " + fechaStr);
        }

        return convertView;
    }
}
