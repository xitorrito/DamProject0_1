package com.example.xito.damproject01.Fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.xito.damproject01.DBManager;
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
    private int taskId;
    private String taskName;
    private String taskDescription;
    private int rewardExp;
    private int rewardMoney;
    private Cursor c;
    private Typeface font;
    private TasksAdapter customAdapter;

    public Fragment3() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManager = DBManager.getInstance(getContext());
        db = dbManager.getWritableDatabase();
        tasks.removeAll(tasks);
        Log.e("create","he sido creado");
        c = db.query("tasks", new String[]{"id","task", "description", "rewardExp", "rewardMoney"}, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                taskId = c.getInt(0);
                taskName = c.getString(1);
                taskDescription = c.getString(2);
                rewardExp = c.getInt(3);
                rewardMoney = c.getInt(4);

                tasks.add(new Tasks(taskId,taskName, taskDescription, rewardExp + "", rewardMoney + ""));

            } while (c.moveToNext());
        }
        font = Typeface.createFromAsset(getContext().getAssets(), "fonts/LipbyChonk.ttf");
        customAdapter= new TasksAdapter(this.getContext(), tasks, font, db);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //d
        listView = (ListView) getView().findViewById(R.id.listView_tasks);
        listView.setAdapter(null);
        listView.setAdapter(customAdapter);
        Log.e("create","he sido creado2");

    }

    private List<Tasks> getTasks(){
        List<Tasks> tasks=new ArrayList<>();
        tasks.add(new Tasks(1,"Robar sucursal bancaria", "Seguro que ni lo notan", "50xp","350$"));
        return tasks;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment3, container, false);
    }
}
