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
    private BluetoothAdapter bluetoothAdapter;
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

       /* bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            //no bluetooth
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                cardView = (CardView)findViewById(R.id.card_view_bluetooth_disabled);
                cardView.setVisibility(View.VISIBLE);
            }
        }
        IntentFilter filter = new IntentFilter();

        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        registerReceiver(mReceiver, filter);
        bluetoothAdapter.startDiscovery();*/
    }

    private final BroadcastReceiver mWifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                scanResults= wifiManager.getScanResults();
                listView.setAdapter(null);
                adapter= new WifiScanAdapter(getApplicationContext(),scanResults,font, db);
                listView.setAdapter(adapter);

            }
        }
    };

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                //discovery starts, we can show progress dialog or perform other tasks
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                //discovery finishes, dismis progress dialog
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //bluetooth device found
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                devices.add(device);
                adapter.notifyDataSetChanged();

                Toast.makeText(HackDevices.this, "Found device " + device.getName(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onDestroy() {
       // unregisterReceiver(mReceiver);

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            unregisterReceiver(mReceiver);
        }catch (IllegalArgumentException e){

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!db.isOpen()) {
            db = dbManager.getWritableDatabase();

        }else{
            saveDataToDB(db);
            Log.e("onresume player level", player.getPlayerExp()+"");
        }
    }
    public void saveDataToDB(SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",player.getPlayerName());
        contentValues.put("level",player.getPlayerLevel());
        contentValues.put("exp",player.getPlayerExp());
        contentValues.put("money",player.getPlayerMoney());
        db.update("player",contentValues, "id="+player.getPlayerId(),null);
        Log.e("guardado en bd", "jugador guardado");
    }
}
