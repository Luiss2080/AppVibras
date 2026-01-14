package com.example.appvibras.vistas.ventas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.appvibras.R;
import com.example.appvibras.modelo.entidades.Venta;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdaptadorVentas extends ArrayAdapter<Venta> {

    public AdaptadorVentas(@NonNull Context context, @NonNull List<Venta> objetos) {
        super(context, R.layout.item_venta, objetos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_venta, parent, false);
        }

        Venta venta = getItem(position);

        if (venta != null) {
            TextView tvTitulo = convertView.findViewById(R.id.tv_venta_titulo);
            TextView tvFecha = convertView.findViewById(R.id.tv_venta_fecha);
            TextView tvTotal = convertView.findViewById(R.id.tv_venta_total);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            String fechaStr = sdf.format(new Date(venta.getFecha()));

            tvTitulo.setText("Venta #" + venta.getId());
            tvFecha.setText(fechaStr);
            tvTotal.setText("$" + String.format("%.2f", venta.getTotal()));
        }

        return convertView;
    }
}
