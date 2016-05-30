package com.example.xito.damproject01.Models;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
    private int taskEnergy;
    private int taskAntivirus;
    private int taskTime;
    private int taskLevel;
    private Cursor c;
    public static List<Tasks> tasks= new ArrayList<>();
    private int taskTimesCompleted;
    private int taskTimesForLevelling;

    public Tasks(int taskId, String taskName, int taskRewardExp, int taskRewardMoney, String taskDescription,
                 int taskMinLevel, int taskEnergy, int taskAntivirus, int taskTime, int taskLevel, int taskTimesCompleted, int taskTimesForLevelling) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskRewardExp = taskRewardExp;
        this.taskRewardMoney = taskRewardMoney;
        this.taskDescription = taskDescription;
        this.taskMinLevel = taskMinLevel;
        this.taskEnergy = taskEnergy;
        this.taskAntivirus = taskAntivirus;
        this.taskTime = taskTime;
        this.taskLevel=taskLevel;
        this.taskTimesCompleted=taskTimesCompleted;
        this.taskTimesForLevelling=taskTimesForLevelling;
    }

    public int getTaskTimesCompleted() {
        return taskTimesCompleted;
    }

    public void setTaskTimesCompleted(int taskTimesCompleted) {
        this.taskTimesCompleted = taskTimesCompleted;
    }

    public int getTaskLevel() {
        return taskLevel;
    }

    public void setTaskLevel(int taskLevel) {
        this.taskLevel = taskLevel;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskTimesForLevelling() {
        return taskTimesForLevelling;
    }

    public void setTaskTimesForLevelling(int taskTimesForLevelling) {
        this.taskTimesForLevelling = taskTimesForLevelling;
    }

    public int getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(int taskTime) {
        this.taskTime = taskTime;
    }

    public int getTaskAntivirus() {
        return taskAntivirus;
    }

    public void setTaskAntivirus(int taskAntivirus) {
        this.taskAntivirus = taskAntivirus;
    }

    public int getTaskEnergy() {
        return taskEnergy;
    }

    public void setTaskEnergy(int taskEnergy) {
        this.taskEnergy = taskEnergy;
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
        c = db.query("tasks", new String[]{"id", "task", "description", "rewardExp", "rewardMoney", "minLevel", "antivirus", "energy", "time","taskLevel","timesCompleted","timesForLevelling"}, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                taskId = c.getInt(0);
                taskName = c.getString(1);
                taskDescription = c.getString(2);
                taskRewardExp = c.getInt(3);
                taskRewardMoney = c.getInt(4);
                taskMinLevel = c.getInt(5);
                taskAntivirus=c.getInt(6);
                taskEnergy=c.getInt(7);
                taskTime=c.getInt(8);
                taskLevel=c.getInt(9);
                taskTimesCompleted=c.getInt(10);
                taskTimesForLevelling=c.getInt(11);

                tasks.add(new Tasks(taskId,taskName,taskRewardExp,taskRewardMoney,taskDescription,taskMinLevel,taskEnergy,taskAntivirus,taskTime,taskLevel,taskTimesCompleted,taskTimesForLevelling));

            } while (c.moveToNext());
        }
    }

}
