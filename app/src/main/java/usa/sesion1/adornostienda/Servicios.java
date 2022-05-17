package usa.sesion1.adornostienda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Servicios extends AppCompatActivity  implements View.OnClickListener {

    private Button btnServicioBisuteria;
    private Button btnServicioArbol;
    private Button btnServicioFiestas;
    private Button btnServicioQuince;
    private TextView txtServicioBisuteria;
    private TextView txtServicioArbol;
    private TextView txtServicioFiestas;
    private TextView txtServicioQuince;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("AppDornos");
        getSupportActionBar().setSubtitle("Materializamos ideas de diseño");
        getSupportActionBar().setLogo(R.mipmap.ic_icon_adorno);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        txtServicioBisuteria = (TextView) findViewById(R.id.txtServicioBisuteria);
        txtServicioArbol = (TextView) findViewById(R.id.txtServicioArbol);
        txtServicioFiestas = (TextView) findViewById(R.id.txtServicioFiestas);
        txtServicioQuince = (TextView) findViewById(R.id.txtServicioQuince);

        btnServicioBisuteria = (Button) findViewById(R.id.btnServicioBisuteria);
        btnServicioArbol = (Button) findViewById(R.id.btnServicioArbol);
        btnServicioFiestas = (Button) findViewById(R.id.btnServicioFiestas);
        btnServicioQuince = (Button) findViewById(R.id.btnServicioQuince);

        btnServicioBisuteria.setOnClickListener(this);
        btnServicioArbol.setOnClickListener(this);
        btnServicioFiestas.setOnClickListener(this);
        btnServicioQuince.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String msg="Se muestra más INFO del SERVICIO ";
        switch(v.getId()) {
            case R.id.btnServicioBisuteria:
                msg = msg + " BISUTERIA. " + txtServicioBisuteria.getText() ;
                break;
            case R.id.btnServicioArbol:
                msg = msg + " ADORNO ARBOL NAVIDEÑO. " + txtServicioArbol.getText();
                break;
            case R.id.btnServicioFiestas:
                msg = msg + " DECORACIÓN FIESTAS. " + txtServicioFiestas.getText();
                break;
            case R.id.btnServicioQuince:
                msg = msg + " DECORACIÓN QUINCE. " + txtServicioQuince.getText();
                break;
        }
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

    }
}