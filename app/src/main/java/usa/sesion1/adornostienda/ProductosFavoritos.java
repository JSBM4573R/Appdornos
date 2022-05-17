package usa.sesion1.adornostienda;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProductosFavoritos extends AppCompatActivity {

    ListView lvwProductos;
    private LinearLayout linearPadre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linearPadre = (LinearLayout) findViewById(R.id.linearProductosFavoritos);

        setContentView(R.layout.activity_productos_favoritos);
        Toast.makeText(getApplicationContext(),"desde ProductosFavoritos",Toast.LENGTH_LONG).show();

        lvwProductos = (ListView)findViewById(R.id.lvwProductos);

        ArrayList<Producto> misProductos = consultarProductos(getApplicationContext());
        AdaptadorProductos adapter = new AdaptadorProductos(getApplicationContext(), misProductos);
        lvwProductos.setAdapter(adapter);

        lvwProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                Producto p = (Producto) adapterView.getItemAtPosition(posicion);
                Toast.makeText(getApplicationContext(), "Dio clic en ..."+p.getNombre(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public ArrayList<Producto> consultarProductos (Context context){
        ArrayList<Producto> productos = new ArrayList<>();

        MyOpenHelper dataBase = new MyOpenHelper(ProductosFavoritos.this);
        SQLiteDatabase db = dataBase.getReadableDatabase();

        Cursor c = dataBase.leerProductos(db);

        while (c.moveToNext()){
            @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
            @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex("nombre"));
            @SuppressLint("Range") int precio = c.getInt(c.getColumnIndex("precio"));
            @SuppressLint("Range") int imagen = c.getInt(c.getColumnIndex("imagen"));
            //@SuppressLint("Range") int cantidad = c.getInt(c.getColumnIndex("cantidad"));

            productos.add(new Producto(id, nombre, precio, imagen));
        }
        return productos;
    }

    private class MyActionDeshacer implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "Se han revertido los cambios", Toast.LENGTH_LONG).show();
        }
    }
}