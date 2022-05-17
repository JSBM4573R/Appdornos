package usa.sesion1.adornostienda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide(); //oculta el action bar

        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, Principal.class);
                startActivity(intent); //Damos inicio a esta actividad
                finish(); //la finalizo para que no se vuelva a mostrar
            }
        };

        Timer tiempo = new Timer();
        tiempo.schedule(tarea, 5000);
        Toast.makeText(getApplicationContext(), "Cargando...", Toast.LENGTH_LONG).show();
    }


}