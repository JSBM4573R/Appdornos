package usa.sesion1.adornostienda;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;


public class ProductoActividad extends AppCompatActivity {

    LinearLayout linearPadre;
    LinearLayout linearHorizontal;
    LinearLayout linearVerticalInterno;
    LinearLayout linearHorizontalUltimo;
    private int costoTotal = 0;
    int aux1;
    int aux2;
    String cant1;
    String precio1;
    ArrayList<Producto> carritoRecibido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_actividad);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.actionBar_Title);
        getSupportActionBar().setSubtitle(R.string.actionBar_Subtitle);
        getSupportActionBar().setLogo(R.mipmap.ic_icon_adorno);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;
        int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
        linearPadre = findViewById(R.id.listacarrito);
        //int costoTotal = 0;

        ArrayList<Producto> carritoDeCompras = new ArrayList<>();// = consultarProductos(getApplicationContext());
        int cantRecibida = getIntent().getExtras().getInt("cantProd");
        //Log.e("TAG_G6", "Mensaje G6: " + "carritoDeCompras..."+carritoDeCompras.size());
        Log.e("TAG_G6", "Mensaje G6: " + "cantidadRecibida..."+cantRecibida);
        //int n = cantRecibida.getIntExtra("cantProd",0);
        int[] id = new int[cantRecibida],cant = new int[cantRecibida];
        for(int i=0; i<cantRecibida; i++){
            id[i] = getIntent().getExtras().getInt("id"+i);
            cant[i] = getIntent().getExtras().getInt("cantidad"+i);
            Log.e("TAG_G6", "id"+i+"="+id[i] + "--" + "cantidad"+i+"="+cant[i]);

            //Toast.makeText(getApplicationContext(), "Antes de entrar consultarProducto-idCarrito -- id="+id, Toast.LENGTH_SHORT).show();
            carritoDeCompras.add(consultarProductos(id[i],cant[i]));
            //Log.e("TAG_G6 carrito= ", carritoDeCompras.="+id[i] + "--" + "cantidad"+i+"="+cant[i]);
        }
        //ArrayList<Producto> carritoDeCompras = (ArrayList<Producto>)carritoRecibido.getSerializableExtra("carrito");
        //Toast.makeText(getApplicationContext(),"Cantidad de productos en el Carrito --> "+carritoDeCompras.size(),Toast.LENGTH_LONG).show();

        costoTotal=0;
        for (Producto p: carritoDeCompras){
            linearHorizontal = new LinearLayout(this);
            linearHorizontal.setOrientation(LinearLayout.HORIZONTAL);
            linearHorizontal.setLayoutParams(new LinearLayout.LayoutParams(matchParent,wrapContent));

            ImageView imagen = new ImageView(this);
            imagen.setImageResource(p.getImagen());
            imagen.setLayoutParams(new LinearLayout.LayoutParams(1,150,1));

            linearVerticalInterno = new LinearLayout(this);
            linearVerticalInterno.setLayoutParams(new LinearLayout.LayoutParams(0,wrapContent,1));
            linearVerticalInterno.setOrientation(LinearLayout.VERTICAL);
            TextView txtNombre = new TextView(this);
            txtNombre.setText(p.getNombre());
            txtNombre.setLayoutParams(new LinearLayout.LayoutParams(wrapContent,wrapContent));
            TextView txtPrecio = new TextView(this);
            txtPrecio.setText(" "+p.getPrecio());
            txtPrecio.setLayoutParams(new LinearLayout.LayoutParams(wrapContent,wrapContent));
            linearVerticalInterno.addView(txtNombre);
            linearVerticalInterno.addView(txtPrecio);

            TextView txtCant = new TextView(this);
            txtCant.setText(" "+p.getCantidad());
            txtCant.setLayoutParams(new LinearLayout.LayoutParams(150,wrapContent));


            try {
                cant1=txtCant.getText().toString();
                precio1=txtPrecio.getText().toString();
                //aux1 = Integer.parseInt(cant1);
                //aux2 = Integer.decode(txtPrecio.getText().toString());
                costoTotal = costoTotal + (Integer.parseInt(txtCant.getText().toString().trim())*Integer.parseInt(txtPrecio.getText().toString().trim()));
                precio1=" "+costoTotal;
                //costoTotal = Integer.parseInt(cant1);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                precio1="";
            }

            Button btnMas = new Button(this);
            btnMas.setText("+");
            btnMas.setLayoutParams(new LinearLayout.LayoutParams(20,wrapContent,1));
            btnMas.setBackgroundResource(R.drawable.boton_redondo);
            Button btnMenos = new Button(this);
            btnMenos.setText("-");
            btnMenos.setLayoutParams(new LinearLayout.LayoutParams(20,wrapContent,1));
            btnMenos.setBackgroundResource(R.drawable.boton_redondo);

            linearHorizontal.addView(imagen);
            linearHorizontal.addView(linearVerticalInterno);
            linearHorizontal.addView(txtCant);
            linearHorizontal.addView(btnMas);
            linearHorizontal.addView(btnMenos);

            linearPadre.addView(linearHorizontal);

            btnMas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    aumentaProductoEnCarrito(p);
                    //String msg="Se añadió el producto " + p.getNombre();
                    //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    txtCant.setText(" "+p.getCantidad());
                }
            });
            btnMenos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disminuyeProductoEnCarrito(p);
                    String msg="Se quitó el producto " + p.getNombre();
                    //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    txtCant.setText(" "+p.getCantidad());
                }
            });
        }

        linearHorizontalUltimo = new LinearLayout(this);
        linearHorizontalUltimo.setLayoutParams(new LinearLayout.LayoutParams(matchParent,wrapContent));
        linearHorizontalUltimo.setOrientation(LinearLayout.HORIZONTAL);

        TextView txtTotal = new TextView(this);
        txtTotal.setText(" "+precio1);
        txtTotal.setLayoutParams(new LinearLayout.LayoutParams(150,wrapContent));

        Button btnPagar = new Button(this);
        btnPagar.setText("PAGAR");
        btnPagar.setLayoutParams(new LinearLayout.LayoutParams(100,wrapContent,1));
        btnPagar.setBackgroundResource(R.drawable.boton_redondo);
        ;

        linearHorizontalUltimo.addView(txtTotal);
        linearHorizontalUltimo.addView(btnPagar);
        //linearHorizontal.addView(linearHorizontalUltimo);
        linearPadre.addView(linearHorizontalUltimo);
        //linearProductos.addView(linearHorizontal);

        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (Producto p: carritoDeCompras){
                    Toast.makeText(getApplicationContext(), "Entra al for a calcular el COSTOTOTAL", Toast.LENGTH_LONG).show();
                    costoTotal = costoTotal + (p.getCantidad()*p.getPrecio());
                    String msg = "cantidad=" + p.getCantidad() + "precio="+p.getPrecio();
                    Log.e("COSTOTOTAL=",msg);
                }

                DialogoDeConfirmacion ddc = new DialogoDeConfirmacion();
                ddc.show(getFragmentManager(),"DialogDeConfirmacion");
            }
        });
    }

    private void aumentaProductoEnCarrito(Producto producto){
        int cantidadActual = producto.getCantidad();
        producto.setCantidad(cantidadActual+1);
        //costoTotal = costoTotal + producto.getPrecio();
    }

    private void disminuyeProductoEnCarrito(Producto producto){
        int cantidadActual = producto.getCantidad();
        producto.setCantidad(cantidadActual-1);
        //costoTotal = costoTotal - producto.getPrecio();
    }

    public Producto consultarProductos (int idCarrito,int cant){
        MyOpenHelper dataBase = new MyOpenHelper(this);
        SQLiteDatabase db = dataBase.getReadableDatabase();

        Cursor c = dataBase.leerProductos(db);
        Log.e("TAG_G6", "Mensaje G6: " + "consultarProductos en ProductoActividad..."+c.getCount());
        for (int i=0;i<c.getColumnCount();i++){
            Log.e("TAG_G6", "Mensaje G6: " + "consultarProductos en ProductoActividad..."+c.getColumnName(i));
        }
        Toast.makeText(getApplicationContext(), "Entra al consultarProducto-idCarrito -- cursor"+c.getCount(), Toast.LENGTH_SHORT).show();

        Producto p = null;
        while (c.moveToNext()){
            @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
            @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex("nombre"));
            @SuppressLint("Range") int precio = c.getInt(c.getColumnIndex("precio"));
            @SuppressLint("Range") int imagen = c.getInt(c.getColumnIndex("imagen"));
            @SuppressLint("Range") int cantidad = c.getInt(c.getColumnIndex("cantidad"));

            Log.e("TAG_ERROR", "Mensaje G6: " + "consultarProductos en ProductoActividad..."+"id="+id);
            //Log.e("TAG_ERROR", "Mensaje G6: " + "Producto cantidad..."+"cantidad="+cantidad);
            if (id==idCarrito){
                p = new Producto(id, nombre, precio, imagen, cant);
                Log.e("TAG_ERROR", "Mensaje G6: " + "Producto adicionado...id="+p.getId()+"cantidad="+p.getCantidad());
            }

        }
        return p;
    }

    private class MyActionDeshacer implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            Toast.makeText(getApplicationContext(), "Se han revertido los cambios", Toast.LENGTH_LONG).show();
        }
    }

    public static class DialogoDeConfirmacion extends DialogFragment {
        ProductoActividad p = new ProductoActividad();
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Costo total a PAGAR..."+p.getCostoTotal())
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getActivity(), "Será enviado a la página de PAGO", Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getActivity(), "Ha cancelado la operación de PAGO", Toast.LENGTH_LONG).show();
                        }
                    });
            // Create the AlertDialog object and return it
            return builder.create();
        }
    }

    public int getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(int costoTotal) {
        this.costoTotal = costoTotal;
    }
}
