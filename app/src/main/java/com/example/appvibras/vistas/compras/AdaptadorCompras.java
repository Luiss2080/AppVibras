package com.example.appvibras.vistas.compras;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.appvibras.R;
import com.example.appvibras.modelo.entidades.Compra;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdaptadorCompras extends ArrayAdapter<Compra> {

    public AdaptadorCompras(@NonNull Context context, @NonNull List<Compra> objetos) {
        super(context, R.layout.item_compra, objetos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_compra, parent, false);
        }

        Compra compra = getItem(position);

        if (compra != null) {
            TextView tvTitulo = convertView.findViewById(R.id.tv_compra_titulo);
            TextView tvFecha = convertView.findViewById(R.id.tv_compra_fecha);
            TextView tvTotal = convertView.findViewById(R.id.tv_compra_total);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            String fechaStr = sdf.format(new Date(compra.getFecha()));

            tvTitulo.setText("Compra #" + compra.getId());
            tvFecha.setText(fechaStr);
            tvTotal.setText("$" + String.format("%.2f", compra.getTotal()));
        }

        return convertView;
    }
}
