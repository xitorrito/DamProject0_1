package com.example.xito.damproject01;

/**
 * Created by Xito on 28/04/2016.
 */
public class Tasks {
    private int taskId;
    private String taskName;
    private String taskRewardExp;
    private String taskRewardMoney;
    private String taskDescription;

    public Tasks(int taskId, String taskName,String taskDescription, String taskRewardExp, String taskRewardMoney) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskRewardExp = taskRewardExp;
        this.taskRewardMoney = taskRewardMoney;
        this.taskDescription = taskDescription;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskRewardExp(String taskRewardExp) {
        this.taskRewardExp = taskRewardExp;
    }

    public void setTaskRewardMoney(String taskRewardMoney) {
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

    public String getTaskRewardExp() {
        return taskRewardExp;
    }


    public String getTaskRewardMoney() {
        return taskRewardMoney;
    }

}
