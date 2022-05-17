/**
 *
 * @author Grupo6
 * @description Este módulo se creó para manejar el backend de la App de adornos en su pantalla principal
 *
 */
package usa.sesion1.adornostienda;

//Sección de importación de componentes
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import java.util.Timer;

//import dmax.dialog.SpotsDialog;


/**
 *
 * @description Clase principal para manipular la pantalla inicial de la app
 */
public class Principal extends AppCompatActivity  {
    //Declaración de atributos de la clase
    private EditText etUsuario; //Variable para contener el boton
    private EditText etClave; //Variable para contener el boton
    private Button btnLogin; //Variable para contener el boton
    private ImageView imgIngreso; //Variable para contener el boton
    private TextView txtIngreso; //Variable para contener el boton
    private MenuItem opcGestionarProductos; //Variable para contener el boton
    private MenuItem opcComprarProductos; //VAriable para contener el boton
    private MenuItem opcCatalogo; //Variable para contener el boton
    private MenuItem opcFavoritos; //Variable para contener el boton
    private MenuItem opcSucursales; //Variable para contener el boton
    private MenuItem opcServicios; //Variable para contener el boton
    private MenuItem opcContacto; //Variable para contener el boton
    private MenuItem opcRegUser; //Variable para contener el boton
    private ProgressBar progressBar1; //Variable para contener el ProgressBar

    AlertDialog mDialog;
    /**
     *
     * @description Sobreescritura del método onCreate para realizar actividades cuando se crea la activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toast.makeText(getApplicationContext(),"Creado menu opcComprarProductos",Toast.LENGTH_LONG).show();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.actionBar_Title);
        getSupportActionBar().setSubtitle(R.string.actionBar_Subtitle);
        getSupportActionBar().setLogo(R.mipmap.ic_icon_adorno);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        /* MyOpenHelper
        MyOpenHelper dataBase = new MyOpenHelper(this);
        SQLiteDatabase db = dataBase.getWritableDatabase();
        db.delete("productos", null, null);
        */

        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etClave = (EditText) findViewById(R.id.etClave);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);


        /* Implementacion para el OnResume el cual se cambiara por el Asynctask del Reto4
        imgIngreso = (ImageView)findViewById(R.id.imgIngreso);
        txtIngreso = (TextView)findViewById(R.id.txtIngreso);
        imgIngreso.setVisibility(View.INVISIBLE);
        txtIngreso.setVisibility(View.INVISIBLE);*/

        opcGestionarProductos = (MenuItem)findViewById(R.id.opcGestionarProductos);
        opcComprarProductos = (MenuItem)findViewById(R.id.opcComprarProductos);
        opcCatalogo = (MenuItem)findViewById(R.id.opcCatalogo);
        opcFavoritos = (MenuItem)findViewById(R.id.opcFavoritos);
        opcSucursales = (MenuItem)findViewById(R.id.opcSucursales);
        opcServicios = (MenuItem)findViewById(R.id.opcServicios);
        opcContacto = (MenuItem)findViewById(R.id.opcContacto);
        opcRegUser = (MenuItem)findViewById(R.id.opcRegUser);
        Toast.makeText(getApplicationContext(),"Creado menu opcComprarProductos",Toast.LENGTH_LONG).show();
        //mDialog = new SpotsDialog.Builder().setContext(Principal.this).setMessage(R.string.msg_espere).build();

        //Asynctask
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Task().execute(etUsuario.getText().toString());
            }
        });
    }

    /**
     * Se implementa clase Task que extiende de Asynctask para:
     * Inhabilitar el boton del Login
     * Mostrar un ProgressBar que indique al usuario que se esta ejecutando una operacion
     * Simular una pausa de 5 segundos
     * Una vez terminnada esta pausa, mostrar por un Toast la Bienvenida al usuario,
     * con su respectivo nombre ingresado en el EditText.
     */
    class Task extends AsyncTask<String, Void, String>{

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "Ejecutando AsyncTask",Toast.LENGTH_SHORT).show();
            progressBar1.setVisibility(View.VISIBLE);
            btnLogin.setEnabled(false);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return strings[0];
        }

        @Override
        protected void onPostExecute(String s) {
            String usuario = etUsuario.getText().toString();

            progressBar1.setVisibility(View.INVISIBLE);
            btnLogin.setEnabled(true);

            Toast.makeText(getApplicationContext(), "Bienvenido \n" + usuario, Toast.LENGTH_LONG).show();


        }
    }



    /**
     *
     * @description Sobreescritura del método onClick para realizar actividades cuando el usuario
     * haga click
     */
    /*@Override
    public void onClick(View v) {
        Toast.makeText(this, R.string.msg_pessBtn, Toast.LENGTH_LONG).show();
        imgIngreso.setVisibility(View.VISIBLE);
        txtIngreso.setVisibility(View.VISIBLE);
        btnLogin.setText(R.string.regresar);
    }*/

    /**
     *
     * @description Sobreescritura del método onCreateOptionsMenu para realizar actividades cuando
     * se crean las opciones del menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menuprincipal, menu);

        return true;
    }

    /**
     *
     * @description Sobreescritura del método onOptionsItemSelected para realizar actividades cuando el usuario seleccione una opción del menu
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.opcGestionarProductos){
            Toast.makeText(getApplicationContext(),"Ingresa a Gestionar Productos",Toast.LENGTH_LONG).show();
            Intent pantallaGestionaProductos = new Intent(this, GestionaProductos.class);
            startActivity(pantallaGestionaProductos);
        }
        if (id == R.id.opcComprarProductos){
            Toast.makeText(getApplicationContext(),"Ingresa a Comprar Productos",Toast.LENGTH_LONG).show();
            Intent pantallaCompraProductos = new Intent(this, Productos.class);
            startActivity(pantallaCompraProductos);
        }
        if (id == R.id.opcCatalogo){
            Toast.makeText(getApplicationContext(),"Ingresa a Catálogo",Toast.LENGTH_LONG).show();
            Intent pantallaCatalogo = new Intent(this, CatalogoActivity.class);
            startActivity(pantallaCatalogo);
        }
        if (id == R.id.opcFavoritos){
            Toast.makeText(getApplicationContext(),"Ingresa a favoritos",Toast.LENGTH_LONG).show();
            Intent pantallaFavoritos = new Intent(this, FavoritoActivity.class);
            startActivity(pantallaFavoritos);
        }
        if (id == R.id.opcSucursales){
            Toast.makeText(getApplicationContext(),"Ingresa a sucursales",Toast.LENGTH_LONG).show();
            Intent pantallaSucursales = new Intent(this, SucursalesActivity.class);
            startActivity(pantallaSucursales);
        }
        if (id == R.id.opcServicios){
            Toast.makeText(getApplicationContext(),R.string.ingresarServ,Toast.LENGTH_LONG).show();
            Intent pantallaServicios = new Intent(this, Servicios.class);
            startActivity(pantallaServicios);
        }
        if (id == R.id.opcContacto){
            Toast.makeText(getApplicationContext(),R.string.ingresarContact,Toast.LENGTH_LONG).show();
            Intent pantallaContacto = new Intent(this, Contacto.class);
            startActivity(pantallaContacto);
        }
        if (id == R.id.opcRegUser){
            Toast.makeText(getApplicationContext(),R.string.ingresarUser,Toast.LENGTH_LONG).show();
            Intent pantallaRegUser = new Intent(this, RegistroUsuario.class);
            startActivity(pantallaRegUser);
        }
        return super.onOptionsItemSelected(item);
    }


    /*El metodo onResume() se cambia con el Asynctask implementado para el reto4
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(),R.string.ingresarOnresume,Toast.LENGTH_LONG).show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 3000ms
                //mDialog.hide();
            }
        }, 3000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //mDialog.show();
    }*/
} //Fin de la clase