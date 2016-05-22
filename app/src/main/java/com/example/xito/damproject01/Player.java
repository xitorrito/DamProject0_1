package com.example.xito.damproject01;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.io.Serializable;

public class Player implements Serializable {

    private int playerId;
    private String playerName;
    private int playerLevel;
    private int playerExp;
    private int playerMoney;
    public static Player player = new Player();
    private int expForNextLevel=40;

    public Player(int playerId, String playerName, int playerLevel, int playerExp, int playerMoney) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerLevel = playerLevel;
        this.playerExp = playerExp;
        this.playerMoney = playerMoney;

    }

    public Player() {

    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPlayerLevel() {
        return playerLevel;
    }

    public void setPlayerLevel(int playerLevel) {
        this.playerLevel = playerLevel;
    }

    public int getPlayerExp() {
        return playerExp;
    }

    public void setPlayerExp(int playerExp) {

        this.playerExp=playerExp;

        double expForNextLevel = getExpForNextLevel();

        if(this.getPlayerExp()>=this.expForNextLevel){
            this.playerLevel++; //Level up
            this.expForNextLevel=(int)expForNextLevel; //Experience for the next level
            this.playerExp=0; //Reset experience points
        }
    }

    public int getPlayerMoney() {
        return playerMoney;
    }

    public void setPlayerMoney(int playerMoney) {
        this.playerMoney = playerMoney;

    }


    //    public void getPlayerStates() {
//        Cursor cursor = bd.query("player",nivel, null,null,null,null,null);
//        //Vamos a la primera fila.
//        cursor.moveToFirst();
//        //Obtenemos el dato de la primera (y única) columna.
//        int coins = cursor.getInt(0);
//
//        Cursor cursor = bd.query("player",exp, null,null,null,null,null);
//        //Vamos a la primera fila.
//        cursor.moveToFirst();
//        //Obtenemos el dato de la primera (y única) columna.
//        int level = cursor.getInt(0);
//
//
//    }
//
    public double getExpForNextLevel() {

       return this.expForNextLevel+expForNextLevel*0.7;
          //  return Math.pow(40 * 1.5, level-1);
    }

    public void setExpForNextLevel(int expForNextLevel) {
        this.expForNextLevel = expForNextLevel;
    }

    public void getPlayerFromDB(SQLiteDatabase db){
        Cursor c = db.query("player", new String[]{"id","name", "level", "exp"}, null, null,null,null,null);
        if (c.moveToFirst()) {
            do {
                player.setPlayerId(c.getInt(0));
                player.setPlayerName(c.getString(1));
                player.setPlayerLevel(c.getInt(2));
                player.setPlayerExp(c.getInt(3));

            } while (c.moveToNext());
        }
    }

    public void insertPlayer(SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name","Hacker");
        contentValues.put("level",1);
        contentValues.put("exp",0);
        contentValues.put("money",0);
        db.insert("player",null, contentValues);
        player = new Player(1,"Hacker",1,0,0);
    }

    public void showDialogNewLevel(Activity activity){

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setMessage("Ahora estás en el nivel "+player.getPlayerLevel())
                .setTitle("¡Has subido de nivel!")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setCancelable(false)
                .show();
    }

}

