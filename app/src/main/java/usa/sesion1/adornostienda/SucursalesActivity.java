package usa.sesion1.adornostienda;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
//import android.content.res.Configuration;
import org.osmdroid.config.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
//import com.google.android.gms.maps.MapView;
//import com.google.android.maps.MapView;
//import com.google.firebase.firestore.GeoPoint;
//import org.osmdroid.views.MapController;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import android.app.Activity;

import java.util.ArrayList;

public class SucursalesActivity extends AppCompatActivity {


    private MapView myOpenMap;
    private MapController myMapController;

    ProgressDialog barraProgreso;
    RecyclerView rcvSucursales;
    ArrayList<Sucursal> sucursales;
    AdaptadorSucursal adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sucursales);
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        rcvSucursales = (RecyclerView)findViewById(R.id.rcvSucursales);
        rcvSucursales.setLayoutManager(new LinearLayoutManager(this));
        rcvSucursales.setHasFixedSize(true);
        sucursales = new ArrayList<Sucursal>();
        barraProgreso = new ProgressDialog(this);
        barraProgreso.setMessage("Consultando en el servidor");
        barraProgreso.setTitle("Cargando Información");
        barraProgreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        new TareaAsincronica().execute();
    }

    public class TareaAsincronica extends AsyncTask<Void, Void, Void> {

        //Sobre mi hilo principal, para gestionar el ProgressDialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            barraProgreso.show();
        }

        //Sobre mi hilo secundario, para ejecutar mi tarea
        @Override
        protected Void doInBackground(Void... voids) {
            sucursales = new ArrayList<>();

            try {
                Thread.sleep(2000);
            }catch (Exception e){

            }

            MyOpenHelper dataBase = new MyOpenHelper(SucursalesActivity.this);
            SQLiteDatabase db = dataBase.getReadableDatabase();

            Cursor c = dataBase.leerSucursales(db);

            Sucursal sucursalTemp = null;

            while (c.moveToNext()){
                @SuppressLint("Range") int id = c.getInt(c.getColumnIndex("id"));
                @SuppressLint("Range") String nombre = c.getString(c.getColumnIndex("nombre"));
                @SuppressLint("Range") String direccion = c.getString(c.getColumnIndex("direccion"));
                @SuppressLint("Range") double latitud = c.getDouble(c.getColumnIndex("latitud"));
                @SuppressLint("Range") double longitud = c.getDouble(c.getColumnIndex("longitud"));
                @SuppressLint("Range") int imagen = c.getInt(c.getColumnIndex("imagen"));

                sucursalTemp = new Sucursal(id, nombre, direccion, latitud, longitud, imagen);
                sucursales.add(sucursalTemp);

            }
            return null;
        }

        //Sobre mi hilo principal, para gestionar el ProgressDialog
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            barraProgreso.cancel();

            adaptador = new AdaptadorSucursal(sucursales);

            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Sucursal sucSeleccionada = sucursales.get(rcvSucursales.getChildAdapterPosition(view));
                    cargarMapa(sucSeleccionada);
                }
            });

            rcvSucursales.setAdapter(adaptador);

        }

        private void cargarMapa(Sucursal sucursal){
            GeoPoint sucursalSeleccionada = new GeoPoint(sucursal.getLatitud(), sucursal.getLongitud());
            Configuration.getInstance().load(getApplicationContext(), PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

            myOpenMap = (MapView) findViewById(R.id.mapa);
            myOpenMap.setBuiltInZoomControls(true);
            myMapController = (MapController) myOpenMap.getController();
            myMapController.setCenter(sucursalSeleccionada);
            myMapController.setZoom(17);

            myOpenMap.setMultiTouchControls(true);

            final MyLocationNewOverlay myLocationoverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getApplicationContext()), myOpenMap);
            myOpenMap.getOverlays().add(myLocationoverlay); //No añadir si no quieres una marca
            myLocationoverlay.enableMyLocation();
            myLocationoverlay.runOnFirstFix(new Runnable() {
                public void run() {
                    myMapController.animateTo(myLocationoverlay.getMyLocation());
                }
            });


            ArrayList<OverlayItem> puntos = new ArrayList<OverlayItem>();
            puntos.add(new OverlayItem(sucursal.getNombre(), sucursal.getDireccion(), sucursalSeleccionada));

            ItemizedIconOverlay.OnItemGestureListener<OverlayItem> tap = new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                @Override
                public boolean onItemLongPress(int arg0, OverlayItem arg1) {
                    return false;
                }
                @Override
                public boolean onItemSingleTapUp(int index, OverlayItem item) {
                    return true;
                }
            };

            ItemizedOverlayWithFocus<OverlayItem> capa = new ItemizedOverlayWithFocus<OverlayItem>(getApplicationContext(), puntos, tap);
            capa.setFocusItemsOnTap(true);
            myOpenMap.getOverlays().add(capa);


        }
    }
}