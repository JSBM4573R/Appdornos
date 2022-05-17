package usa.sesion1.adornostienda;

import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class GestionaProductos extends AppCompatActivity {

    EditText edtNombre;
    EditText edtPrecio;
    Spinner spnImagenes;
    Button btnGuardar;
    ListView lvwProductos;
    private LinearLayout linearPadre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        linearPadre = (LinearLayout) findViewById(R.id.linearGestionarProductos);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.actionBar_Title);
        getSupportActionBar().setSubtitle(R.string.actionBar_Subtitle);
        getSupportActionBar().setLogo(R.mipmap.ic_icon_adorno);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.activity_gestionar_productos);
        Toast.makeText(getApplicationContext(),"desde gestiona Productos",Toast.LENGTH_LONG).show();
        edtNombre = (EditText)findViewById(R.id.edtNombre);
        edtPrecio = (EditText)findViewById(R.id.edtPrecio);
        spnImagenes = (Spinner) findViewById(R.id.spnImagenes);

        btnGuardar = (Button)findViewById(R.id.btnGuardar);
        lvwProductos = (ListView)findViewById(R.id.lvwProductos);

        ArrayList<ImagenProducto> miGaleria = new ArrayList<>();
        miGaleria.add(new ImagenProducto(0, "Seleccione una imagen...", R.drawable.white));
        miGaleria.add(new ImagenProducto(1, "Corazón Dorado", R.drawable.adorno1));
        miGaleria.add(new ImagenProducto(2, "Bola de colores", R.drawable.adorno2));
        miGaleria.add(new ImagenProducto(3, "Moño de florez", R.drawable.adorno3));
        miGaleria.add(new ImagenProducto(4, "Adorno de moneda", R.drawable.adorno4));
        miGaleria.add(new ImagenProducto(5, "Arbol de navidad", R.drawable.arbolnavidad));
        miGaleria.add(new ImagenProducto(6, "Guirnalda", R.drawable.guirnalda));
        miGaleria.add(new ImagenProducto(7, "Estrella", R.drawable.estrella));

        AdaptadorImagen adapImagen = new AdaptadorImagen(this, miGaleria);
        spnImagenes.setAdapter(adapImagen);

        ArrayList<Producto> misProductos = consultarProductos(getApplicationContext());
        AdaptadorProductos adapter = new AdaptadorProductos(getApplicationContext(), misProductos);
        lvwProductos.setAdapter(adapter);

        //edtNombre.setText(spnImagenes.getCount());

        lvwProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                Producto p = (Producto) adapterView.getItemAtPosition(posicion);
                Toast.makeText(getApplicationContext(), "Dio clic en ...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(GestionaProductos.this, DetalleProductoActivity.class);
                intent.putExtra("id", p.getId());
                intent.putExtra("nombre", p.getNombre());
                intent.putExtra("precio", p.getPrecio());

                startActivity(intent);
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarProducto();

                ArrayList<Producto> misProductos = consultarProductos(getApplicationContext());
                Toast.makeText(getApplicationContext(), "ConsultaProducto--"+misProductos.size(), Toast.LENGTH_SHORT).show();
                AdaptadorProductos adapter = new AdaptadorProductos(getApplicationContext(), misProductos);
                lvwProductos.setAdapter(adapter);

                edtNombre.setText("");
                edtPrecio.setText("");
                spnImagenes.setSelection(0);
            }
        });
    }

    private void guardarProducto(){
        MyOpenHelper dataBase = new MyOpenHelper(GestionaProductos.this);
        SQLiteDatabase db = dataBase.getWritableDatabase();

        try{
            String nombre = edtNombre.getText().toString();
            int precio = Integer.parseInt(edtPrecio.getText().toString());

            ImagenProducto img = (ImagenProducto) spnImagenes.getSelectedItem();
            int imagen = img.getImagen();

            dataBase.insertarProducto(nombre, precio, imagen, db);
            Log.e("TAG_Wilson", "Se ha guardado el productoxxxxxx");
            Toast.makeText(this, "Se ha guardado el productoxxxxxx", Toast.LENGTH_LONG).show();
            Snackbar mySnackbar = Snackbar.make(linearPadre, "Usted ha guardado un adorno", Snackbar.LENGTH_LONG);
            mySnackbar.setAction("DESHACER", new MyActionDeshacer());
            mySnackbar.show();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            dataBase.close();
        }

    }

    public ArrayList<Producto> consultarProductos (Context context){
        ArrayList<Producto> productos = new ArrayList<>();

        MyOpenHelper dataBase = new MyOpenHelper(GestionaProductos.this);
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