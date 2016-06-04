package com.example.xito.damproject01;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.xito.damproject01.Models.Player;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {
 
    //Sentencia SQL para crear las tablas
    String createTableJugador = "CREATE TABLE IF NOT EXISTS Player (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, level" +
            " INTEGER, exp INTEGER, money INTEGER, energy INTEGER, efficacy INTEGER)";
    String createTableTareas = "CREATE TABLE IF NOT EXISTS Tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, task TEXT, " +
            "description TEXT, minLevel INTEGER, rewardExp INTEGER,rewardMoney INTEGER, time INTEGER, energy INTEGER, antivirus INTEGER, taskLevel INTEGER," +
            " timesCompleted INTEGER, timesForLevelling INTEGER)";
    String createTableTienda = "CREATE TABLE IF NOT EXISTS Items (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " item TEXT, description TEXT, priceMoney INTEGER, upgradeEnergy INTEGER, upgradeEfficacy INTEGER)";
    String createTableWifiHacks = "CREATE TABLE IF NOT EXISTS WifiHacks (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " networkName TEXT, macAddress TEXT, latitude REAL, longitude REAL)";

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
        insertTasks(db);
        insertPlayer(db);
        insertMarket(db);
        Log.e("bd creada","soy la bd");
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
        ArrayList<Integer>taskTime = new ArrayList<>();
        ArrayList<Integer>taskEnergy = new ArrayList<>();
        ArrayList<Integer>taskAntivirus = new ArrayList<>();


        //0
        taskName.add("Espiar mensajes del movil");
        taskDescription.add("");
        taskMinLevel.add(0);
        taskRewardExp.add(10);
        taskRewardMoney.add(5);
        taskTime.add(50);
        taskEnergy.add(0);
        taskAntivirus.add(0);

        //1
        taskName.add("Hackear web del vecino");
        taskDescription.add("");
        taskMinLevel.add(0);
        taskRewardExp.add(20);
        taskRewardMoney.add(10);
        taskTime.add(50);
        taskEnergy.add(1);
        taskAntivirus.add(2);

        //2
        taskName.add("Robar dinero de sucursal bancaria");
        taskDescription.add("");
        taskMinLevel.add(2);
        taskRewardExp.add(35);
        taskRewardMoney.add(50);
        taskTime.add(50);
        taskEnergy.add(2);
        taskAntivirus.add(5);
        //3
        taskName.add("Manipular semaforos");
        taskDescription.add("");
        taskMinLevel.add(3);
        taskRewardExp.add(35);
        taskRewardMoney.add(15);
        taskTime.add(10);
        taskEnergy.add(2);
        taskAntivirus.add(5);
        //4
        taskName.add("Hackerman"); //Boss
        taskDescription.add("Derrota a Hackerman");
        taskMinLevel.add(4);
        taskRewardExp.add(40);
        taskRewardMoney.add(100);
        taskTime.add(200);
        taskEnergy.add(4);
        taskAntivirus.add(8);
        //5
        taskName.add("Celebgate");
        taskDescription.add("Publica fotos intimas de famosas");
        taskMinLevel.add(5);
        taskRewardExp.add(80);
        taskRewardMoney.add(20);
        taskTime.add(50);
        taskEnergy.add(2);
        taskAntivirus.add(6);
        //6
        taskName.add("Publicar documentos privados del gobierno");
        taskDescription.add("");
        taskMinLevel.add(5);
        taskRewardExp.add(100);
        taskRewardMoney.add(60);
        taskTime.add(200);
        taskEnergy.add(4);
        taskAntivirus.add(10);
        //7
        taskName.add("Robar dinero de la sede de un banco");
        taskDescription.add("");
        taskMinLevel.add(6);
        taskRewardExp.add(120);
        taskRewardMoney.add(90);
        taskTime.add(150);
        taskEnergy.add(4);
        taskAntivirus.add(12);


        for (int i = 0; i <8 ; i++) {
            contentValues.put("task",taskName.get(i));
            contentValues.put("description",taskDescription.get(i));
            contentValues.put("minLevel",taskMinLevel.get(i));
            contentValues.put("rewardExp",taskRewardExp.get(i));
            contentValues.put("rewardMoney",taskRewardMoney.get(i));
            contentValues.put("energy",taskEnergy.get(i));
            contentValues.put("antivirus",taskAntivirus.get(i));
            contentValues.put("time",taskTime.get(i));
            contentValues.put("taskLevel",1);
            contentValues.put("timesCompleted",0);
            contentValues.put("timesForLevelling",15);
            db.insert("tasks",null, contentValues);
        }

    }

    private void insertPlayer(SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name","Hacker");
        contentValues.put("level",1);
        contentValues.put("exp",0);
        contentValues.put("money",0);
        contentValues.put("energy",10);
        contentValues.put("efficacy",2);
        db.insert("player",null, contentValues);
        Player.player = new Player(1,"Hacker",2,10,0,0,1);
    }

    private void insertMarket(SQLiteDatabase db){

        ContentValues contentValues = new ContentValues();

        ArrayList<String>itemName = new ArrayList<>();
        ArrayList<String>itemDescription = new ArrayList<>();
        ArrayList<Integer>itemPriceMoney = new ArrayList<>();
        ArrayList<Integer>itemUpgradeEnergy = new ArrayList<>();
        ArrayList<Integer>itemUpgradeEfficacy = new ArrayList<>();


        //0
        itemName.add("Bateria");
        itemDescription.add("");
        itemPriceMoney.add(100);
        itemUpgradeEnergy.add(10);
        itemUpgradeEfficacy.add(0);

        //1
        itemName.add("Teclado");
        itemDescription.add("");
        itemPriceMoney.add(110);
        itemUpgradeEnergy.add(0);
        itemUpgradeEfficacy.add(1);

        //2
        itemName.add("Modem");
        itemDescription.add("Permite hackear redes wifi");
        itemPriceMoney.add(110);
        itemUpgradeEnergy.add(0);
        itemUpgradeEfficacy.add(0);



        for (int i = 0; i <itemName.size() ; i++) {
            contentValues.put("item",itemName.get(i));
            contentValues.put("description",itemDescription.get(i));
            contentValues.put("priceMoney",itemPriceMoney.get(i));
            contentValues.put("upgradeEnergy",itemUpgradeEnergy.get(i));
            contentValues.put("upgradeEfficacy",itemUpgradeEfficacy.get(i));
            db.insert("items",null, contentValues);
        }

    }
}
