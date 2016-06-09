package com.example.xito.damproject01;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.xito.damproject01.Adapters.TasksAdapter;
import com.example.xito.damproject01.Models.Player;
import com.example.xito.damproject01.Models.Tasks;

/**
 * Created by Xito on 22/05/2016.
 */
public class ProgressBarAsyncTask extends AsyncTask<Integer, Integer, Integer> {
    private Context mContext;
    private ProgressBar mProgressBar;
    private Player mPlayer;
    private Tasks mTask;
    private Activity mActivity;
    private int playerLevel;
    private OnDataChangeListenerAsyncTask mOnDataChangeListener;
    private boolean mRunning, success;
    private int efficacy, antivirus, difference;
    private double probability;

    public ProgressBarAsyncTask(Context context, ProgressBar progressBar, Player player, Tasks task, Activity activity){
        mContext=context;
        mProgressBar=progressBar;
        mPlayer =player;
        mTask=task;
        mActivity=activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.setVisibility(View.VISIBLE);
        efficacy = mPlayer.getPlayerEfficacy();
        antivirus = mTask.getTaskAntivirus();
        Log.e("efficacy", efficacy+"");
        Log.e("antivirus", antivirus+"");
        difference = efficacy - antivirus;
        Log.e("diferencia", difference+"");
        if(efficacy==antivirus){
            probability=95;
        }
        if((difference)<0) {
            if ((difference) == -1) {
                probability = 15;
            }
            if ((difference) <= -2) {
                probability = 5;
            }
        }else{
            if (difference==1) {
                probability=95;
            }
            if (difference>=2){
                probability=99;
            }
        }

        int number = (int)((Math.random()*100)+1);
        Log.e("probability", probability+"");
        Log.e("number", number+"");
        if (number <= probability){
            success=true;
        }else{
            success=false;
        }
        Log.e("probability", probability+"");
        Log.e("success", success+"");

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
        playerLevel=mPlayer.getPlayerLevel();
        mPlayer.setPlayerEnergy(mPlayer.getPlayerEnergy()-mTask.getTaskEnergy());
        if(success){
            mPlayer.setPlayerMoney(mPlayer.getPlayerMoney()+mTask.getTaskRewardMoney());
            mPlayer.setPlayerExp(mPlayer.getPlayerExp()+mTask.getTaskRewardExp());
            if(playerLevel!=mPlayer.getPlayerLevel()){
                mPlayer.showDialogNewLevel(mActivity);
            }
        }else{
            //Not success mission
        }



        if(mOnDataChangeListener!=null){
            mOnDataChangeListener.onDataChanged(success);
        }

    }

    public interface OnDataChangeListenerAsyncTask{
        void onDataChanged(boolean success);
    }
    public void setOnDataChangeListenerAsymlTask(OnDataChangeListenerAsyncTask onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }


}
