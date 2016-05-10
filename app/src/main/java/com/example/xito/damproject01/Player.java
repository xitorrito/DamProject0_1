package com.example.xito.damproject01;

import android.database.Cursor;

public class Player {

    private int playerId;
    private String playerName;
    private int playerLevel;
    private int playerExp;
    private int playerMoney;


    public Player(int playerId, String playerName, int playerLevel, int playerExp, int playerMoney) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerLevel = playerLevel;
        this.playerExp = playerExp;
        this.playerMoney = playerMoney;
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
        this.playerExp = playerExp;
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
    public int getExpForNextLevel(int level) {
        return (int) Math.round(Math.pow(40 * 1.5, (level - 1)));
    }

}

