package com.example.xito.damproject01;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.xito.damproject01.Adapters.TasksAdapter;

/**
 * Created by Xito on 22/05/2016.
 */
public class ProgressBarAsyncTask extends AsyncTask<Integer, Integer, Integer> {
    private Context mContext;
    private ProgressBar mProgressBar;
    private Player mPlayer;
    private Tasks mTask;
    private boolean mRunning;

    public ProgressBarAsyncTask(Context context, ProgressBar progressBar, Player player, Tasks task){
        mContext=context;
        mProgressBar=progressBar;
        mPlayer =player;
        mTask=task;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Integer doInBackground(Integer... params) {
        int i;
        for (i=0; i <=100;i++){
            //Modifico la UI
            publishProgress(i); //SE VA A onProgressUpdate()
            //Duermo 50 ms
            SystemClock.sleep(params[0]); // El tiempo indicado al crear asynctask
            //Compruebo si se ha cancelado
            if (isCancelled()){
                break;
            }
        }
        return i;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mProgressBar.setProgress(values[0]);
     }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        TasksAdapter.running=false;
        mProgressBar.setProgress(0);
        mProgressBar.setVisibility(View.INVISIBLE);
        mPlayer.setPlayerMoney(mPlayer.getPlayerMoney()+mTask.getTaskRewardMoney());
        mPlayer.setPlayerExp(mPlayer.getPlayerExp()+mTask.getTaskRewardExp());


    }


}
