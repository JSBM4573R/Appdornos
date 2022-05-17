package usa.sesion1.adornostienda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import usa.sesion1.adornostienda.R;
import usa.sesion1.adornostienda.AdaptadorProductos;
import usa.sesion1.adornostienda.MyOpenHelper;
import usa.sesion1.adornostienda.Producto;

public class FavoritoActivity extends AppCompatActivity {

    ListView lvwFavoritos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorito);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("AppDornos");
        getSupportActionBar().setSubtitle("Materializamos ideas de diseño");
        getSupportActionBar().setLogo(R.mipmap.ic_icon_adorno);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Log.e("TAG-G6","Entra a FAvoritoActivity......... ");
        lvwFavoritos = (ListView) findViewById(R.id.lvwProductosFavoritos);

        ArrayList<Producto> misProductos = consultarProductosFavoritos(this);
        AdaptadorProductos adapter = new AdaptadorProductos(getApplicationContext(), misProductos);
        lvwFavoritos.setAdapter(adapter);

        lvwFavoritos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                Producto p = (Producto) adapterView.getItemAtPosition(posicion);
                lanzarDialogo(p);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public ArrayList<Producto> consultarProductosFavoritos(Context context){
        ArrayList<Producto> productos = new ArrayList<>();

        MyOpenHelper dataBase = new MyOpenHelper(FavoritoActivity.this);
        SQLiteDatabase db = dataBase.getReadableDatabase();

        Cursor c = dataBase.leerProductosFavoritos(db);
        Log.e("TAG-G6","Antes de entrar a construir el cursor ");
        String msg = "";
        while (c.moveToNext()){
            @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
            @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex("nombre"));
            @SuppressLint("Range") int precio = c.getInt(c.getColumnIndex("precio"));
            @SuppressLint("Range") int imagen = c.getInt(c.getColumnIndex("imagen"));
            @SuppressLint("Range") int favorito = c.getInt(c.getColumnIndex("favorito"));

            msg = ""+id+"   Favorito="+favorito;
            Log.e("TAG-G6",msg);
            if(favorito == 1){
                Log.e("TAG-G6","TRUE - Entra a agregar favorito a la lista ");
                productos.add(new Producto(id, nombre, precio, imagen, true));
            }/*else{
                Log.e("TAG-G6","FALSE - Entra a no agregar favorito a la lista ");
                productos.add(new Producto(id, nombre, precio, imagen, false));
            }*/
        }
        return productos;

    }

    private void lanzarDialogo(Producto p){
        MyOpenHelper dataBase = new MyOpenHelper(FavoritoActivity.this);
        SQLiteDatabase db = dataBase.getWritableDatabase();

        FavoritoActivity.DialogoDeConfirmacion ddc = new FavoritoActivity.DialogoDeConfirmacion();
        ddc.setParametros(p, dataBase, db);
        ddc.show(getSupportFragmentManager(), "DialogoDeConfirmacion");
    }


    public static class DialogoDeConfirmacion extends DialogFragment {

        Producto producto;
        MyOpenHelper dataBase;
        SQLiteDatabase db;

        public void setParametros(Producto producto,  MyOpenHelper dataBase, SQLiteDatabase db){
            this.producto = producto;
            this.dataBase = dataBase;
            this.db = db;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("¿Está seguro de QUITAR este producto a favoritos?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //UPDATE
                            ProductoCase.quitarFavorito(producto.getId(), dataBase, db);
                            Toast.makeText(getActivity(), "El producto " + producto.getNombre() + " ha sido RETIRADO de favoritos ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Log.e("TAG_INFO", "Producto continúa en favoritos ");
                            Toast.makeText(getActivity(), "Producto NO RETIRADO de favoritos ", Toast.LENGTH_LONG).show();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}