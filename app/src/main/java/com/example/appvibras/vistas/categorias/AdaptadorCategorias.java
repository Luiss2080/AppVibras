package com.example.appvibras.vistas.categorias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.appvibras.R;
import com.example.appvibras.modelo.entidades.Categoria;
import java.util.List;

/**
 * Vista (Adaptador) para representar la lista de categorías en la interfaz.
 */
public class AdaptadorCategorias extends ArrayAdapter<Categoria> {
    
    public AdaptadorCategorias(@NonNull Context context, @NonNull List<Categoria> objetos) {
        super(context, R.layout.item_categoria, objetos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_categoria, parent, false);
        }
        
        Categoria categoria = getItem(position);

        if (categoria != null) {
            TextView tvNombre = convertView.findViewById(R.id.tv_categoria_nombre);
            TextView tvDescripcion = convertView.findViewById(R.id.tv_categoria_descripcion);

            tvNombre.setText(categoria.getNombre());
            tvDescripcion.setText(categoria.getDescripcion() != null && !categoria.getDescripcion().isEmpty()
                ? categoria.getDescripcion()
                : "Sin descripción");
        }
        
        return convertView;
    }
}
