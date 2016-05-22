package com.example.xito.damproject01.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xito.damproject01.DBManager;
import com.example.xito.damproject01.HackDevices;
import com.example.xito.damproject01.MainActivity;
import com.example.xito.damproject01.Player;
import com.example.xito.damproject01.R;
import com.example.xito.damproject01.Tasks;
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
    private String taskDescription;
    private int rewardExp;
    private int rewardMoney;
    private int minLevel;
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
    private int playerLevel;

    public Fragment3() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        font = Typeface.createFromAsset(getContext().getAssets(), "fonts/LipbyChonk.ttf");
        tasks=Tasks.tasks;
        player = Player.player;
        playerLevel= player.getPlayerLevel();
        tasksUnlocked=getUnlockedTasks(playerLevel);
        customAdapter= new TasksAdapter(getContext(), tasksUnlocked, font, db, getActivity());
        customAdapter.setOnDataChangeListener(new TasksAdapter.OnDataChangeListener() {
            @Override
            public void onDataChanged() {
                updateTextViews();
                Log.e("level ondatachanged", player.getPlayerLevel()+"");
                Log.e("tasks size", tasksUnlocked.size()+"");
                if(tasksUnlocked.size()<=tasks.size()){
                    if(tasks.get(tasksUnlocked.size()-1).getTaskMinLevel()==player.getPlayerLevel()){
                        tasksUnlocked=getUnlockedTasks(playerLevel);
                        tasksUnlocked.add(getNewUnlockedTask(playerLevel));
                        customAdapter.notifyDataSetChanged();
                    }
                }


            }

        });

    }


    private Tasks getNewUnlockedTask(int playerLevel) {
        return tasks.get(tasksUnlocked.size()-1);
    }




    private void updateTextViews() {

        level.setText(player.getPlayerLevel()+"");
        exp.setText(player.getPlayerExp()+"");
        money.setText(player.getPlayerMoney()+"");

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

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/LipbyChonk.ttf");

        levelText.setTypeface(font);
        level.setTypeface(font);
        expText.setTypeface(font);
        exp.setTypeface(font);
        moneyText.setTypeface(font);
        money.setTypeface(font);

        Button button = (Button)getView().findViewById(R.id.hackDevices);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HackDevices.class);
                startActivity(intent);
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
}
