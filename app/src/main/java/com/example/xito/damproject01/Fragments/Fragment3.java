package com.example.xito.damproject01.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xito.damproject01.DBManager;
import com.example.xito.damproject01.HackDevices;
import com.example.xito.damproject01.HackingDevice;
import com.example.xito.damproject01.MapsActivity;
import com.example.xito.damproject01.Models.Player;
import com.example.xito.damproject01.ProgressBarAsyncTask;
import com.example.xito.damproject01.R;
import com.example.xito.damproject01.Models.Tasks;
import com.example.xito.damproject01.Adapters.TasksAdapter;

import java.util.ArrayList;
import java.util.List;


public class Fragment3 extends Fragment {

    private ListView listView;
    private SQLiteDatabase db;
    private DBManager dbManager;
    private List<Tasks> tasks= new ArrayList<>();
    private List<Tasks> tasksUnlocked = new ArrayList<>();
    private int taskId;
    private String taskName;
    private int taskRewardExp;
    private int taskRewardMoney;
    private String taskDescription;
    private int taskMinLevel;
    private int taskEnergy;
    private int taskAntivirus;
    private int taskTime;
    private int taskLevel;
    private int rewardExp;
    private int rewardMoney;
    private int minLevel;
    private int taskTimesCompleted;
    private int taskTimesForLevelling;
    private Cursor c;
    private Typeface font;
    private TasksAdapter customAdapter;
    private RecyclerView recyclerView;
    private Player player;
    private TextView levelText;
    private TextView level;
    private TextView expText;
    private TextView exp;
    private TextView moneyText;
    private TextView money;
    private TextView title;
    private TextView energy;
    private TextView efficacy;
    private int playerLevel;
    private ProgressBarAsyncTask progressBarAsyncTask;
    private Tasks task;

    public Fragment3() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  tasks=Tasks.tasks;
        font = Typeface.createFromAsset(getContext().getAssets(), "fonts/LipbyChonk.ttf");
        player = Player.player;
        playerLevel= player.getPlayerLevel();
        tasksUnlocked=getUnlockedTasks(playerLevel);
        dbManager=DBManager.getInstance(getContext());
        db=dbManager.getWritableDatabase();
        //task=new Tasks();

       // tasks=task.getTasksFromDBToArray(db);
        getTasksFromDB(db);

        customAdapter= new TasksAdapter(getContext(), tasks, font, db, getActivity());
        customAdapter.setOnDataChangeListener(new TasksAdapter.OnDataChangeListener() {
            @Override
            public void onDataChanged(int level) {
                updateTextViews();
                //customAdapter.notifyDataSetChanged();
              /*  if(tasksUnlocked.size()<=tasks.size()){
                    if(tasks.get(tasksUnlocked.size()-1).getTaskMinLevel()==player.getPlayerLevel()){
                        tasksUnlocked=getUnlockedTasks(playerLevel);
                        tasksUnlocked.add(getNewUnlockedTask());
                        customAdapter.notifyDataSetChanged();
                    }
                }*/
            }

        });



    }

    private void getTasksFromDB(SQLiteDatabase db) {
        tasks.removeAll(tasks);
        c = db.query("tasks", new String[]{"id", "task", "description", "rewardExp", "rewardMoney", "minLevel", "antivirus", "energy", "time", "taskLevel", "timesCompleted", "timesForLevelling"}, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                taskId = c.getInt(0);
                taskName = c.getString(1);
                taskDescription = c.getString(2);
                taskRewardExp = c.getInt(3);
                taskRewardMoney = c.getInt(4);
                taskMinLevel = c.getInt(5);
                taskAntivirus = c.getInt(6);
                taskEnergy = c.getInt(7);
                taskTime = c.getInt(8);
                taskLevel = c.getInt(9);
                taskTimesCompleted = c.getInt(10);
                taskTimesForLevelling = c.getInt(11);

                tasks.add(new Tasks(taskId, taskName, taskRewardExp, taskRewardMoney, taskDescription, taskMinLevel, taskEnergy, taskAntivirus, taskTime, taskLevel, taskTimesCompleted, taskTimesForLevelling));

            } while (c.moveToNext());
        }
    }


    private Tasks getNewUnlockedTask() {
        return tasks.get(tasksUnlocked.size()-1);
    }




    private void updateTextViews() {

        level.setText(player.getPlayerLevel()+"");
        exp.setText(player.getPlayerExp()+"");
        money.setText(player.getPlayerMoney()+"");
        efficacy.setText(player.getPlayerEfficacy()+"");
        energy.setText(player.getPlayerEnergy()+"");

    }

    private List<Tasks> getUnlockedTasks(int playerLevel){
        for (int i = tasksUnlocked.size(); i <tasks.size(); i++) {
            if(playerLevel>=tasks.get(i).getTaskMinLevel()){
                tasksUnlocked.add(tasks.get(i));
            }
        }

        return tasksUnlocked;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        listView = (ListView) getView().findViewById(R.id.listView_tasks);
//        //listView.setAdapter(null);
//        listView.setAdapter(customAdapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        listView = (ListView) getView().findViewById(R.id.listView_tasks);
        listView.setAdapter(null);
        listView.setAdapter(customAdapter);

        levelText=(TextView)getView().findViewById(R.id.textView_level);
        level=(TextView)getView().findViewById(R.id.textView_levelNumber);
        expText=(TextView)getView().findViewById(R.id.textView_xpText);
        exp=(TextView)getView().findViewById(R.id.textView_xp);
        moneyText =(TextView)getView().findViewById(R.id.textView_moneyText);
        money =(TextView)getView().findViewById(R.id.textView_MoneyNumber);
        energy =(TextView)getView().findViewById(R.id.textView_energy);
        efficacy =(TextView)getView().findViewById(R.id.textView_efficacy);

        font = Typeface.createFromAsset(getContext().getAssets(), "fonts/LipbyChonk.ttf");

        levelText.setTypeface(font);
        level.setTypeface(font);
        expText.setTypeface(font);
        exp.setTypeface(font);
        moneyText.setTypeface(font);
        money.setTypeface(font);

        Button button = (Button)getView().findViewById(R.id.hackDevices);
        Button button2 = (Button)getView().findViewById(R.id.buttonMap);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tasks.removeAll(tasks);
                c = db.query("items", new String[]{"id"}, "adquired>=? AND id=?", new String[] {"1","3"}, null, null, null, null);

                if (c.moveToFirst()) {
                    Intent intent = new Intent(getActivity(), HackDevices.class);
                    intent.putExtra("player", player);
                    startActivity(intent);

                }else{
                    Toast.makeText(getContext(), "Necesitas una antena para hackear redes wifi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager locationManager;
                boolean gps_enabled= false,network_enabled = false;

                    locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                try{
                    gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                }catch(Exception ex){}

                try{
                    network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                }catch(Exception ex){}

                if(gps_enabled || network_enabled) {
                    Intent intent = new Intent(getActivity(), MapsActivity.class);
                    intent.putExtra("backMenu", 1);
                    startActivity(intent);

                }else{
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    Toast.makeText(getContext(), "Debes activar la ubicación", Toast.LENGTH_SHORT).show();

                }
            }
        });

        updateTextViews();

//        Bundle b = getArguments();
//        player= (Player) b.getSerializable("player");
        //player=Player.player;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment3, container, false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            updateTextViews();
            //customAdapter.notifyDataSetChanged();
            if (!isVisibleToUser) {
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!db.isOpen()) {
            db = dbManager.getWritableDatabase();
        }
//        tasks=task.getTasksFromDBToArray(db);
//        customAdapter= new TasksAdapter(getContext(), tasks, font, db, getActivity());
//        listView.setAdapter(customAdapter);


    }
}
