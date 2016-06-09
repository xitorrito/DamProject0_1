package com.example.xito.damproject01;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xito.damproject01.Adapters.BluetoothDevicesAdapter;
import com.example.xito.damproject01.Adapters.WifiScanAdapter;
import com.example.xito.damproject01.Models.Player;

import java.util.ArrayList;
import java.util.List;

public class HackDevices extends AppCompatActivity {
    private List<BluetoothDevice> devices;
    private WifiScanAdapter adapter;
    private SQLiteDatabase db;
    private DBManager dbManager;
    private Typeface font;
    private CardView cardView;
    private ListView listView;
    private Player player;
    private WifiManager wifiManager;
    private List<ScanResult> scanResults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hack_devices);
        dbManager = DBManager.getInstance(this);
        db = dbManager.getWritableDatabase();
        player=(Player)getIntent().getSerializableExtra("player");
        devices= new ArrayList<>();
        listView = (ListView)findViewById(R.id.listView);
        font = Typeface.createFromAsset(getAssets(), "fonts/LipbyChonk.ttf");

        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        registerReceiver(mWifiScanReceiver,
                new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();

    }

    private final BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                scanResults= wifiManager.getScanResults();
                listView.setAdapter(null);
                adapter= new WifiScanAdapter(getApplicationContext(),scanResults,font, db, HackDevices.this);
                listView.setAdapter(adapter);

            }
        }
    };
    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onStop() {
     //   saveDataToDB(db);
        unregisterReceiver(mWifiScanReceiver);
        Log.e("stop","scan");
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!db.isOpen()) {
            db = dbManager.getWritableDatabase();
        }
    }
    public void saveDataToDB(SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",player.getPlayerName());
        contentValues.put("level",player.getPlayerLevel());
        contentValues.put("exp",player.getPlayerExp());
        contentValues.put("money",player.getPlayerMoney());
        db.update("player",contentValues, "id="+player.getPlayerId(),null);
    }
}
