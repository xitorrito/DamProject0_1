package com.example.xito.damproject01;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
 
public class DBManager extends SQLiteOpenHelper {
 
    //Sentencia SQL para crear las tablas
    String createTableJugador = "CREATE TABLE IF NOT EXISTS Jugador (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, nivel" +
            " INTEGER, exp INTEGER dinero INTEGER)";
    String createTableTareas = "CREATE TABLE IF NOT EXISTS Tareas (id INTEGER PRIMARY KEY AUTOINCREMENT, tarea TEXT, " +
            "nivelMin INTEGER, recompensaExp INTEGER,recompensaDinero INTEGER)";
    String createTableTienda = "CREATE TABLE IF NOT EXISTS Tienda (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " item TEXT, descripcion TEXT, precioMonedas INTEGER, precioItems INTEGER)";

    private static DBManager uniqueInstance;
    
    public DBManager(Context context) {
        super(context, "dbproject.db", null, 1);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(createTableJugador);
        db.execSQL(createTableTareas);
        db.execSQL(createTableTienda);
        insertData(db);
    }
    
    public void insertData(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        //db.insert("");

        
       // values.put();
     
    }

    public static synchronized DBManager getInstance(Context context) {
        if (uniqueInstance == null) {
            uniqueInstance = new DBManager(context.getApplicationContext());
        }
        return uniqueInstance;
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //NOTA: Por simplicidad del ejemplo aquí utilizamos directamente la opción de
        //      eliminar la tabla anterior y crearla de nuevo vacía con el nuevo formato.
        //      Sin embargo lo normal será que haya que migrar datos de la tabla antigua
        //      a la nueva, por lo que este método debería ser más elaborado.
 
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Usuarios");

    }
}
