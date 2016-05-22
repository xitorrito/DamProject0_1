package com.example.xito.damproject01;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xito on 28/04/2016.
 */
public class Tasks {
    private int taskId;
    private String taskName;
    private int taskRewardExp;
    private int taskRewardMoney;
    private String taskDescription;
    private int taskMinLevel;
    public static List<Tasks> tasks= new ArrayList<>();
    private Cursor c;


    public Tasks(int taskId, String taskName,String taskDescription, int taskRewardExp, int taskRewardMoney, int taskMinLevel) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskRewardExp = taskRewardExp;
        this.taskRewardMoney = taskRewardMoney;
        this.taskDescription = taskDescription;
        this.taskMinLevel = taskMinLevel;
    }

    public Tasks() {

    }

    public int getTaskMinLevel() {
        return taskMinLevel;
    }

    public void setTaskMinLevel(int taskMinLevel) {
        this.taskMinLevel = taskMinLevel;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskRewardExp(int taskRewardExp) {
        this.taskRewardExp = taskRewardExp;
    }

    public void setTaskRewardMoney(int taskRewardMoney) {
        this.taskRewardMoney = taskRewardMoney;
    }


    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public int getTaskRewardExp() {
        return taskRewardExp;
    }


    public int getTaskRewardMoney() {
        return taskRewardMoney;
    }

    public void getTasksFromDB(SQLiteDatabase db) {
        tasks.removeAll(tasks);
        c = db.query("tasks", new String[]{"id", "task", "description", "rewardExp", "rewardMoney", "minLevel"}, null, null, null, null, null, null);
        Log.e("insertTasks", "get tasks from bd");
        if (c.moveToFirst()) {
            do {
                taskId = c.getInt(0);
                taskName = c.getString(1);
                taskDescription = c.getString(2);
                taskRewardExp = c.getInt(3);
                taskRewardMoney = c.getInt(4);
                taskMinLevel = c.getInt(5);

                tasks.add(new Tasks(taskId, taskName, taskDescription, taskRewardExp, taskRewardMoney, taskMinLevel));

            } while (c.moveToNext());
        }
    }

}
