package usa.sesion1.adornostienda;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdaptadorImagen  extends ArrayAdapter<ImagenProducto> {

    public AdaptadorImagen(@NonNull Context context,  List<ImagenProducto> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    public View initView(int posicion, View view, ViewGroup viewGroup) {

        View vista = view;

        LayoutInflater inflater = LayoutInflater.from(getContext());
        vista = inflater.inflate(R.layout.item_imagen, null);

        ImageView imagen = (ImageView) vista.findViewById(R.id.item_spn_imagen);
        TextView tvwNombre = (TextView) vista.findViewById(R.id.item_spn_nombre);

        ImagenProducto miImagenItem = getItem(posicion);

        tvwNombre.setText(miImagenItem.getNombre());
        imagen.setImageResource(miImagenItem.getImagen());

        return vista;
    }
}

