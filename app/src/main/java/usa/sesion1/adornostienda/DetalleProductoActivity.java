package usa.sesion1.adornostienda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DetalleProductoActivity extends AppCompatActivity {

    EditText edtNombreAct;
    EditText edtPrecioAct;

    Button btnActualizar;
    Button btnBorrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        edtNombreAct = (EditText)findViewById(R.id.edtNombreAct);
        edtPrecioAct = (EditText)findViewById(R.id.edtPrecioAct);

        btnActualizar = (Button)findViewById(R.id.btnActualizar);
        btnBorrar = (Button)findViewById(R.id.btnBorrar);

        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("id");
        String nombre = extras.getString("nombre");
        int precio = extras.getInt("precio");

        edtNombreAct.setText(nombre);
        edtPrecioAct.setText(""+precio);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualizarProducto(id);
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrarProducto(id);
            }
        });

    }

    private void actualizarProducto(int id){
        MyOpenHelper dataBase = new MyOpenHelper(DetalleProductoActivity.this);
        SQLiteDatabase db = dataBase.getWritableDatabase();

        try{
            String nombre = edtNombreAct.getText().toString();
            int precio = Integer.parseInt(edtPrecioAct.getText().toString());

            dataBase.actualizarProducto(nombre, precio, id, 1, db);
            Log.e("TAG_Wilson", "Se ha actualizado el producto");
            Toast.makeText(this, "Se ha actualizado el producto", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(DetalleProductoActivity.this, Principal.class);
            startActivity(intent);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            dataBase.close();
        }
    }


    private void borrarProducto(int id){
        MyOpenHelper dataBase = new MyOpenHelper(DetalleProductoActivity.this);
        SQLiteDatabase db = dataBase.getWritableDatabase();

        try{

            dataBase.borrarProducto(id, db);
            Log.e("TAG_Wilson", "Se ha borrado el producto");
            Toast.makeText(this, "Se ha borrado el producto", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(DetalleProductoActivity.this, Principal.class);
            startActivity(intent);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            dataBase.close();
        }
    }
}
