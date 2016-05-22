package com.example.xito.damproject01;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {
 
    //Sentencia SQL para crear las tablas
    String createTableJugador = "CREATE TABLE IF NOT EXISTS Player (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, level" +
            " INTEGER, exp INTEGER, money INTEGER)";
    String createTableTareas = "CREATE TABLE IF NOT EXISTS Tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT, " +
            "description TEXT, minLevel INTEGER, rewardExp INTEGER,rewardMoney INTEGER)";
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
       // db.execSQL(createTableTienda);
        //insertData(db);
        insertTasks(db);
        insertPlayer(db);
        Log.e("bd creada","soy la bd");
        //Player.insertPlayer(db);
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

        db.execSQL("DROP TABLE IF EXISTS Tasks");

    }

    private void insertTasks(SQLiteDatabase db){

        ContentValues contentValues = new ContentValues();


        ArrayList<String>taskName = new ArrayList<>();
        ArrayList<String>taskDescription = new ArrayList<>();
        ArrayList<Integer>taskMinLevel = new ArrayList<>();
        ArrayList<Integer>taskRewardExp = new ArrayList<>();
        ArrayList<Integer>taskRewardMoney = new ArrayList<>();

        //0
//        taskName.add("Espiar mensajes del movil de gente");
        taskName.add("Espiar mensajes del movil de gente");
        taskDescription.add("");
        taskMinLevel.add(1);
        taskRewardExp.add(10);
        taskRewardMoney.add(5);

        //1
        taskName.add("Hackear web del vecino");
        taskDescription.add("Se lo merece por hacer tantos ruiditos");
        taskMinLevel.add(1);
        taskRewardExp.add(20);
        taskRewardMoney.add(10);
        //2
        taskName.add("Robar dinero de sucursal bancaria");
        taskDescription.add("");
        taskMinLevel.add(2);
        taskRewardExp.add(35);
        taskRewardMoney.add(50);
        //3
        taskName.add("Manipular semaforos");
        taskDescription.add("");
        taskMinLevel.add(3);
        taskRewardExp.add(35);
        taskRewardMoney.add(15);
        //4
        taskName.add("Hackerman"); //Boss
        taskDescription.add("Derrota a Hackerman");
        taskMinLevel.add(4);
        taskRewardExp.add(40);
        taskRewardMoney.add(100);
        //5
        taskName.add("Celebgate");
        taskDescription.add("Publica fotos intimas de famosas");
        taskMinLevel.add(5);
        taskRewardExp.add(80);
        taskRewardMoney.add(20);
        //6
        taskName.add("Publicar documentos privados del gobierno");
        taskDescription.add("");
        taskMinLevel.add(5);
        taskRewardExp.add(140);
        taskRewardMoney.add(60);
        //7
        taskName.add("Robar dinero de la sede de un banco");
        taskDescription.add("");
        taskMinLevel.add(6);
        taskRewardExp.add(170);
        taskRewardMoney.add(90);


        for (int i = 0; i <8 ; i++) {
            contentValues.put("task",taskName.get(i));
            contentValues.put("description",taskDescription.get(i));
            contentValues.put("minLevel",taskMinLevel.get(i));
            contentValues.put("rewardExp",taskRewardExp.get(i));
            contentValues.put("rewardMoney",taskRewardMoney.get(i));
            db.insert("tasks",null, contentValues);
        }

//        registro.put("codigo", cod);
//        registro.put("descripcion", descri);
//        registro.put("precio", pre);
//        db.insert("tareas", null, registro);

    }

    private void insertPlayer(SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name","Hacker");
        contentValues.put("level",1);
        contentValues.put("exp",0);
        contentValues.put("money",0);
        db.insert("player",null, contentValues);
        Player.player = new Player(1,"Hacker",1,0,0);
    }
}
