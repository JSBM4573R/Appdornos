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

public class CatalogoActivity extends AppCompatActivity {

    ListView lvwProductos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("AppDornos");
        getSupportActionBar().setSubtitle("Materializamos ideas de diseño");
        getSupportActionBar().setLogo(R.mipmap.ic_icon_adorno);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Log.e("TAG-G6","Entra en CatalogoActivity...");
        lvwProductos = (ListView) findViewById(R.id.lvwProductos);
        Log.e("TAG-G6","Antes de crear el listado con el adaptador...");

        ArrayList<Producto> misProductos = consultarProductos(this);
        AdaptadorProductos adapter = new AdaptadorProductos(getApplicationContext(), misProductos);
        lvwProductos.setAdapter(adapter);
        Log.e("TAG-G6","Después del adaptador...");
        lvwProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                Producto p = (Producto) adapterView.getItemAtPosition(posicion);
                lanzarDialogo(p);
            }
        });
    }

    public ArrayList<Producto> consultarProductos (Context context){
        ArrayList<Producto> productos = new ArrayList<>();

        MyOpenHelper dataBase = new MyOpenHelper(CatalogoActivity.this);
        SQLiteDatabase db = dataBase.getReadableDatabase();

        Cursor c = dataBase.leerProductos(db);

        while (c.moveToNext()){
            @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
            @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex("nombre"));
            @SuppressLint("Range") int precio = c.getInt(c.getColumnIndex("precio"));
            @SuppressLint("Range") int imagen = c.getInt(c.getColumnIndex("imagen"));
            @SuppressLint("Range") int favorito = c.getInt(c.getColumnIndex("favorito"));

            if(favorito == 1){
                productos.add(new Producto(id, nombre, precio, imagen, true));
            }else{
                productos.add(new Producto(id, nombre, precio, imagen, false));
            }
        }
        return productos;

    }

    private void lanzarDialogo(Producto p){
        MyOpenHelper dataBase = new MyOpenHelper(CatalogoActivity.this);
        SQLiteDatabase db = dataBase.getWritableDatabase();

        DialogoDeConfirmacion ddc = new DialogoDeConfirmacion();
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
            builder.setMessage("¿Está seguro de agregar este producto a favoritos?")
                    .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //UPDATE
                            ProductoCase.agregarFavorito(producto.getId(), dataBase, db);
                            Toast.makeText(getActivity(), "El producto " + producto.getNombre() + " ha sido agregado a favoritos ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Log.e("TAG_INFO", "Producto NO agregado a favooritos ");
                            Toast.makeText(getActivity(), "Producto NO agregado a favoritos ", Toast.LENGTH_LONG).show();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }
}