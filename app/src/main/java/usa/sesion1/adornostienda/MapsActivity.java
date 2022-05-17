package usa.sesion1.adornostienda;

import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import usa.sesion1.adornostienda.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int apiConnect = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()); //Codigo de conexion que genere el API, si es disponible lanzo mi getApplicationContext
        if (apiConnect == ConnectionResult.SUCCESS){ //Si la conexion está disponible muestre el mapa
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } else{
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(apiConnect, (Activity) getApplicationContext(), 10);
            dialog.show();//muestro el dialog
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL); //Seleccionamos el tipo de mapa como Hibrido
        mMap.setMaxZoomPreference(20);//Establecemos el máximo rango del Zoom


        /**
         * Defino las sedes de trabajo y ubico la cámara en la sede principal
         */
        LatLng principal = new LatLng(4.641621, -74.073624);
        mMap.addMarker(new MarkerOptions().position(principal).title("AppDornos sede Galerías"));

        LatLng sur = new LatLng(4.585019, -74.101160);
        mMap.addMarker(new MarkerOptions().position(sur).title("AppDornos sede Sur")); //Este es el globo rojo, es decir el marcador

        LatLng norte = new LatLng(4.735911, -74.050161);
        mMap.addMarker(new MarkerOptions().position(norte).title("AppDornos sede Norte"));

        LatLng occidente = new LatLng(4.663343, -74.130343);
        mMap.addMarker(new MarkerOptions().position(occidente).title("AppDornos sede Occidente"));

        float zoom=12; //creo una variable para definir el zoom del mapa, luego de probar con 12 se observan todas las sedes en Bogotá
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(principal, zoom)); //Muevo la cámara y enfoco ese punto exacto e incluyo la variable zoom

    }
}