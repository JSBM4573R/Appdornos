package usa.sesion1.adornostienda;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.io.Serializable;

public class Productos extends AppCompatActivity implements Serializable {

    LinearLayout linearProductos;
    LinearLayout linearHorizontal;
    LinearLayout linearVerticalInterno;
    LinearLayout linearHorizontalUltimo;
    ArrayList<Producto> carrito;
    ArrayList<Producto> catalogo;
    Intent pantallaCarrito;
    private MenuItem opcVerCarrito; //VAriable para contener el boton

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("AppDORNOS");
        getSupportActionBar().setSubtitle("Materializamos ideas de diseño");
        getSupportActionBar().setLogo(R.mipmap.ic_icon_adorno);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        opcVerCarrito = (MenuItem)findViewById(R.id.opcVerCarrito);
        carrito = new ArrayList<>();
        linearProductos = (LinearLayout) findViewById(R.id.linearProductos);
        int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;
        int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;

        /*
        catalogo = new ArrayList<>();
        catalogo.add(new Producto(1, "Adorno Corazón dorado", R.drawable.adorno1,120000));
        catalogo.add(new Producto(2, "Adorno Estrella mpultiple", R.drawable.adorno2,45000));
        catalogo.add(new Producto(3, "Adorno Navideño elegante", R.drawable.adorno3,37000));
        catalogo.add(new Producto(4, "Adorno corona navideña", R.drawable.adorno4,80000));
        */

        ArrayList<Producto> catalogo = consultarProductos(getApplicationContext());

        for (Producto producto:catalogo){
            linearHorizontal = new LinearLayout(this);
            linearHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            linearHorizontal.setLayoutParams(new LinearLayout.LayoutParams(matchParent,wrapContent));
            ImageView imagen = new ImageView(this);
            imagen.setImageResource(producto.getImagen());
            imagen.setLayoutParams(new LinearLayout.LayoutParams(1,150,1));
            linearVerticalInterno = new LinearLayout(this);
            linearVerticalInterno.setLayoutParams(new LinearLayout.LayoutParams(0,wrapContent,1));
            linearVerticalInterno.setOrientation(LinearLayout.VERTICAL);

            TextView txtNombre = new TextView(this);
            txtNombre.setText(producto.getNombre());
            txtNombre.setLayoutParams(new LinearLayout.LayoutParams(150,wrapContent));

            TextView txtPrecio = new TextView(this);
            txtPrecio.setText(" "+producto.getPrecio());
            txtPrecio.setLayoutParams(new LinearLayout.LayoutParams(150,wrapContent));

            linearVerticalInterno.addView(txtNombre);
            linearVerticalInterno.addView(txtPrecio);
            Button btnAnadir = new Button(this);
            btnAnadir.setText("Añadir");
            btnAnadir.setLayoutParams(new LinearLayout.LayoutParams(100,wrapContent,1));
            btnAnadir.setBackgroundResource(R.drawable.boton_redondo);
            linearHorizontal.addView(imagen);
            linearHorizontal.addView(linearVerticalInterno);
            linearHorizontal.addView(btnAnadir);

            linearProductos.addView(linearHorizontal);
            //Toast.makeText(getApplicationContext(), " "+producto.getNombre()+" - "+producto.getPrecio(), Toast.LENGTH_LONG).show();
            btnAnadir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!buscarProducto(carrito,producto)){
                        carrito.add(producto);
                        String msg="Se añadió el producto " + producto.getNombre();
                        Log.e("TAG-G6",msg);
                        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }else{
                        aumentarProductoEnCarrito(carrito,producto);
                        String msg="Se agregó uno mas del producto " + producto.getNombre();
                        Log.e("TAG-G6",msg);
                        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    }

                }
            });
        }
        linearHorizontalUltimo = new LinearLayout(this);
        linearHorizontalUltimo.setLayoutParams(new LinearLayout.LayoutParams(matchParent,wrapContent));
        linearHorizontalUltimo.setOrientation(LinearLayout.HORIZONTAL);

        Button btnVerCarrito = new Button(this);
        btnVerCarrito.setText("Ver Carrito");
        btnVerCarrito.setLayoutParams(new LinearLayout.LayoutParams(100,wrapContent,1));
        btnVerCarrito.setBackgroundResource(R.drawable.boton_redondo);
        linearHorizontalUltimo.addView(btnVerCarrito);
        //linearHorizontal.addView(linearHorizontalUltimo);
        linearProductos.addView(linearHorizontalUltimo);
        //linearProductos.addView(linearHorizontal);
        btnVerCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Ingresa a Ver Carrito..."+carrito.size(),Toast.LENGTH_LONG).show();
                Log.e("TAG_G6", "Mensaje G6: " + "Ingresa a Ver Carrito..."+carrito.size());
                try {
                    pantallaCarrito = new Intent(getApplicationContext(), ProductoActividad.class);
                    //pantallaCarrito.putExtra("carrito",carrito);
                    pantallaCarrito.putExtra("cantProd", carrito.size());
                    int tempId,tempCant = 0;
                    for(int i=0; i<carrito.size(); i++){
                        pantallaCarrito.putExtra("id"+i, carrito.get(i).getId());
                        pantallaCarrito.putExtra("cantidad"+i, carrito.get(i).getCantidad());
                        tempId = carrito.get(i).getId();
                        tempCant = carrito.get(i).getCantidad();
                        Log.e("TAG-G6","Enviado: "+"id"+i+"="+tempId + "--" + "cantidad"+i+"="+tempCant);
                    }
                    Log.e("TAG-G6","Cantidad enviada..."+carrito.size());
                    startActivity(pantallaCarrito);
                }catch (Exception e){
                    Log.e("TAG_ERROR", "Error EX: " + e.getMessage());
                    e.printStackTrace();
                }

            }
        });
    }

    private boolean buscarProducto(ArrayList<Producto> productos, Producto producto){
        Toast.makeText(getApplicationContext(), "Entra en buscarProducto()", Toast.LENGTH_SHORT).show();
        for(Producto p: productos){
            if(producto.getId() == p.getId()){
                return true;
            }
        }
        return false;
    }

    private void aumentarProductoEnCarrito(ArrayList<Producto> productos, Producto producto){
        for(int i=0; i<carrito.size(); i++){
            int cantidadActual = carrito.get(i).getCantidad();
            carrito.get(i).setCantidad(cantidadActual+1);
            Log.e("TAG_G6", "Mensaje G6: " + "Cantidad producto..."+carrito.get(i).getCantidad());
        }
    }

    public ArrayList<Producto> consultarProductos (Context context){
        ArrayList<Producto> productos = new ArrayList<>();

        MyOpenHelper dataBase = new MyOpenHelper(this);
        SQLiteDatabase db = dataBase.getReadableDatabase();

        Cursor c = dataBase.leerProductos(db);

        Toast.makeText(getApplicationContext(), "Entra al cursor"+c.getCount(), Toast.LENGTH_SHORT).show();

        while (c.moveToNext()){
            @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
            @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex("nombre"));
            @SuppressLint("Range") int precio = c.getInt(c.getColumnIndex("precio"));
            @SuppressLint("Range") int imagen = c.getInt(c.getColumnIndex("imagen"));
            @SuppressLint("Range") int cantidad = c.getInt(c.getColumnIndex("cantidad"));

            productos.add(new Producto(id, nombre, precio, imagen, cantidad));
        }
        return productos;
    }

}