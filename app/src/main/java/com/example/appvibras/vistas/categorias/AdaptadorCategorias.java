package com.example.appvibras.vistas.categorias;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.appvibras.modelo.entidades.Categoria;
import java.util.List;

/**
 * Vista (Adaptador) para representar la lista de categorías en la interfaz.
 */
public class AdaptadorCategorias extends ArrayAdapter<Categoria> {
    
    public AdaptadorCategorias(@NonNull Context context, @NonNull List<Categoria> objetos) {
        super(context, android.R.layout.simple_list_item_1, objetos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        
        Categoria categoria = getItem(position);
        TextView tv = convertView.findViewById(android.R.id.text1);
        if (categoria != null) {
            tv.setText(categoria.getNombre());
        }
        
        return convertView;
    }
}
