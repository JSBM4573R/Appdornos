package usa.sesion1.adornostienda;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;


public class ProductoCase {
    public static void agregarFavorito(int id, MyOpenHelper dataBase, SQLiteDatabase db){
        try{
            dataBase.seleccionarFavorito(id, db);
        }catch (Exception e){

        }finally {
            dataBase.close();
        }
    }
    public static void quitarFavorito(int id, MyOpenHelper dataBase, SQLiteDatabase db){
        Log.e("TAG-G6","Entra a quitarFavorito---------------------------");
        try{
            dataBase.retirarFavorito(id, db);
        }catch (Exception e){

        }finally {
            dataBase.close();
        }
    }
}
