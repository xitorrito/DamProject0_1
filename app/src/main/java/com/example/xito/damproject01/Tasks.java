package com.example.xito.damproject01;

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


    public Tasks(int taskId, String taskName,String taskDescription, int taskRewardExp, int taskRewardMoney, int taskMinLevel) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskRewardExp = taskRewardExp;
        this.taskRewardMoney = taskRewardMoney;
        this.taskDescription = taskDescription;
        this.taskMinLevel = taskMinLevel;
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

}
