package usa.sesion1.adornostienda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyOpenHelper extends SQLiteOpenHelper {

    private static final String PRODUCTOS_TABLE_CREATE =
            "CREATE TABLE productos (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, " +
                    "precio INTEGER, imagen INTEGER, cantidad INTEGER, favorito BOOLEAN) ";

    private static final String SUCURSALES_TABLE_CREATE =
            "CREATE TABLE sucursales (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, direccion TEXT, " +
                    " latitud REAL, longitud REAL, imagen INTEGER) ";

    private static final int DB_VERSION = 3;
    private static final String DB_NAME = "compras.db";

    public MyOpenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PRODUCTOS_TABLE_CREATE);
        db.execSQL(SUCURSALES_TABLE_CREATE);

        ArrayList<Producto> productos = Proveedor.getProductos();
        for(Producto p:productos){
            insertarProducto(p, db);
        }

        ArrayList<Sucursal> sucursales = Proveedor.getSucursales();
        for(Sucursal s:sucursales){
            insertarSucursal(s, db);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS productos ");
        db.execSQL(" DROP TABLE IF EXISTS sucursales ");
        onCreate(db);
    }

    public void insertarProducto(Producto p, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("nombre", p.getNombre());
        cv.put("precio", p.getPrecio());
        cv.put("imagen", p.getImagen());
        cv.put("favorito", p.isFavorito());
        db.insert("productos", null, cv);
    }

    public void insertarProducto(String nombre, int precio, int imagen, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("precio", precio);
        cv.put("imagen", imagen);
        cv.put("favorito", false);

        db.insert("productos", null, cv);
    }

    public void insertarSucursal(Sucursal s, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("nombre", s.getNombre());
        cv.put("direccion", s.getDireccion());
        cv.put("latitud", s.getLatitud());
        cv.put("longitud", s.getLongitud());
        cv.put("imagen", s.getImagen());

        db.insert("sucursales", null, cv);
    }

    public void seleccionarFavorito(int id, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("favorito", true);
        cv.put("id", id);
        String[] arg = new String[]{""+id};
        Log.e("TAG-G6",cv.get("favorito")+" -- id=?"+arg);
        db.update("productos", cv, "id=?", arg);
    }

    public void retirarFavorito(int id, SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        cv.put("favorito", false);
        cv.put("id", id);
        String[] arg = new String[]{""+id};
        Log.e("TAG-G6",cv.get("favorito")+" -- id=?"+arg);
        db.update("productos", cv, "id=?", arg);
    }

    public Cursor leerProductos(SQLiteDatabase db){
        //return db.query("productos", null, null, null, null, null, null);
        return db.rawQuery("SELECT * FROM productos", null); //WHERE favorito = true ", null);
    }

    public Cursor leerSucursales(SQLiteDatabase db){
        return db.query("sucursales", null, null, null, null, null, null);
    }

    public Cursor leerProductosFavoritos(SQLiteDatabase db){
        //return db.rawQuery("SELECT * FROM productos WHERE favorito = true ", null);
        return db.rawQuery("SELECT * FROM productos ", null);
    }

    public void actualizarProducto(String nombre, int precio, int id, int cantidad, SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("precio", precio);
        cv.put("cantidad", cantidad);

        String[] arg = new String[]{"" + id};
        db.update("productos", cv, "id=?", arg);

    }

    public void borrarProducto(int id, SQLiteDatabase db) {
        String[] arg = new String[]{"" + id};
        db.delete("productos", "id=?", arg);

    }

}
