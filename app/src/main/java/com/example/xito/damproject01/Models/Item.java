package com.example.xito.damproject01.Models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xito on 28/05/2016.
 */
public class Item {

    private String itemName;
    private String itemDescription;
    private int itemPriceMoney;
    private int itemUpgradeEnergy;
    private int itemUpgradeEfficacy;
    private int itemId;
    public static List<Item> items;
    private Cursor c;

    public Item(int itemId,String itemName, String itemDescription, int itemPriceMoney, int itemUpgradeEnergy, int itemUpgradeEfficacy) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPriceMoney = itemPriceMoney;
        this.itemUpgradeEnergy = itemUpgradeEnergy;
        this.itemUpgradeEfficacy = itemUpgradeEfficacy;
    }

    public Item() {

    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }


    public int getItemUpgradeEfficacy() {
        return itemUpgradeEfficacy;
    }

    public void setItemUpgradeEfficacy(int itemUpgradeEfficacy) {
        this.itemUpgradeEfficacy = itemUpgradeEfficacy;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getItemPriceMoney() {
        return itemPriceMoney;
    }

    public void setItemPriceMoney(int itemPriceMoney) {
        this.itemPriceMoney = itemPriceMoney;
    }

    public int getItemUpgradeEnergy() {
        return itemUpgradeEnergy;
    }

    public void setItemUpgradeEnergy(int itemUpgradeEnergy) {
        this.itemUpgradeEnergy = itemUpgradeEnergy;
    }


    public void getItemsFromDB(SQLiteDatabase db) {
        items=new ArrayList<>();
        c = db.query("items", new String[]{"item", "description", "priceMoney", "upgradeEnergy", "upgradeEfficacy","id"}, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                itemName = c.getString(0);
                itemDescription = c.getString(1);
                itemPriceMoney = c.getInt(2);
                itemUpgradeEnergy = c.getInt(3);
                itemUpgradeEfficacy = c.getInt(4);
                itemId=c.getInt(5);

                items.add(new Item(itemId,itemName,itemDescription,itemPriceMoney,itemUpgradeEnergy,itemUpgradeEfficacy));

            } while (c.moveToNext());
        }
    }
}
