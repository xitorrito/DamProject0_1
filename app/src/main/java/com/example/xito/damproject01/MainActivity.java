package com.example.xito.damproject01;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.xito.damproject01.Adapters.ViewPagerAdapter;
import com.example.xito.damproject01.Fragments.Fragment1;
import com.example.xito.damproject01.Fragments.Fragment2;
import com.example.xito.damproject01.Fragments.Fragment3;
import com.example.xito.damproject01.Models.Item;
import com.example.xito.damproject01.Models.Player;
import com.example.xito.damproject01.Models.Tasks;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DBManager dbManager;
    private SQLiteDatabase db;
    private ActionBar actionBar;
    private Player player = new Player();
    private Tasks tasks = new Tasks();
    private Item item = new Item();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("prefs", 0);

        if (prefs.getBoolean("firstTime",true)) { //The app is launched for the first time
            //the app is being launched for first time, do something
            Log.e("first time", "first time");


            // record the fact that the app has been started at least once
            prefs.edit().putBoolean("firstTime", false).commit();
        }else{
            Log.e("second time", "second time");
        }

        dbManager = DBManager.getInstance(this);
        db = dbManager.getWritableDatabase();
        setContentView(R.layout.activity_main);
        setTabMenu();
        dbManager = new DBManager(this);
        actionBar = getSupportActionBar();
        actionBar.hide();
        //createPlayer(db);
          //Create a new player in the bd
        tasks.getTasksFromDB(db); //Create a static list of the tasks in the Tasks class
        player.getPlayerFromDB(db); //Create a static player in the Player class
        item.getItemsFromDB(db); //Create a static list of items in the Item class

        player=Player.player;
        Log.e("onCreate player level", player.getPlayerExp()+"");
    }

    private void setTabMenu() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); //Remove the HomeAsUpButton arrow

        tabLayout.getTabAt(0).setIcon(R.drawable.pc_icon );
        tabLayout.getTabAt(1).setIcon(R.drawable.coin_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.tasks_icon);

    }

    private void setupViewPager(ViewPager viewPager) {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //Fragment1 fragment1 = new Fragment1();
        //Bundle b = new Bundle();
        //b.putSerializable("player", player);
        //fragment1.setArguments(b);
        adapter.addFragment(new Fragment1(), "");
        adapter.addFragment(new Fragment2(), "");
        adapter.addFragment(new Fragment3(), "");
        viewPager.setAdapter(adapter);
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

    @Override
    protected void onStop() {
        super.onStop();
        saveDataToDB(db);
    }

    public void saveDataToDB(SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",player.getPlayerName());
        contentValues.put("level",player.getPlayerLevel());
        contentValues.put("exp",player.getPlayerExp());
        contentValues.put("money",player.getPlayerMoney());
        contentValues.put("energy",player.getPlayerEnergy());
        contentValues.put("efficacy",player.getPlayerEfficacy());
        db.update("player",contentValues, "id="+player.getPlayerId(),null);

    }

}
