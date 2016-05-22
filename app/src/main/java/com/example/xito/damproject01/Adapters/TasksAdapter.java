package com.example.xito.damproject01.Adapters;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.xito.damproject01.Player;
import com.example.xito.damproject01.ProgressBarAsyncTask;
import com.example.xito.damproject01.R;
import com.example.xito.damproject01.Tasks;
import com.liulishuo.magicprogresswidget.MagicProgressBar;

import java.util.List;

public class TasksAdapter extends BaseAdapter  {

    private Context context;
    private List<Tasks> tasks;
    private Typeface font;
    private SQLiteDatabase db;
    private Tasks task;
    private int taskMinLevel;
    private Player player = Player.player;
    private int playerLevel;
    OnDataChangeListener mOnDataChangeListener;
    private Activity activity;
    private ListView listView;
    private ProgressBarAsyncTask asyncTask;
    public static boolean running=false;

    public TasksAdapter(Context context, List<Tasks> tasks, Typeface font, SQLiteDatabase db, Activity activity) {
        this.context = context;
        this.tasks=tasks;
        this.font=font;
        this.db=db;
        this.activity=activity;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        playerLevel=player.getPlayerLevel();
        final ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.item_listview,null);
            viewHolder.cardView = (CardView) convertView.findViewById(R.id.card_view);
            viewHolder.taskName=(TextView)convertView.findViewById(R.id.task_name);
            viewHolder.taskDescription=(TextView)convertView.findViewById(R.id.task_description);
            viewHolder.raskRewardMoney =(TextView)convertView.findViewById(R.id.task_reward_money);
            viewHolder.taskRewardExp =(TextView)convertView.findViewById(R.id.task_reward_exp);
            viewHolder.progressBar=(ProgressBar) convertView.findViewById(R.id.progressBar_task);
            //viewHolder.cuadroFoto=(ImageView) convertView.findViewById(R.id.painting);


            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.taskName.setTypeface(font);
        viewHolder.taskDescription.setTypeface(font);
        viewHolder.taskRewardExp.setTypeface(font);
        viewHolder.raskRewardMoney.setTypeface(font);
        //viewHolder.progressBar.setVisibility(View.GONE);

        task = tasks.get(position);


        viewHolder.taskName.setText(task.getTaskName());
        viewHolder.taskDescription.setText(task.getTaskDescription());
        viewHolder.taskRewardExp.setText(task.getTaskRewardExp()+"XP");
        viewHolder.raskRewardMoney.setText(task.getTaskRewardMoney()+"$");
        //viewHolder.cuadroFoto.setImageResource(cuadros.get(position).getPainting());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse)
                        .duration(200)
                        .playOn(viewHolder.cardView);
                task = tasks.get(position);
                //Log.e("Nombre", task.getTaskName());

                if(!running){
                    asyncTask = new ProgressBarAsyncTask(context,viewHolder.progressBar, player, task);
                    asyncTask.execute(100); //100 = 10 seconds
                    running=true;
                }




//                double expForNextLevel = player.getExpForNextLevel();
//
//                if(player.getPlayerExp()>=expForNextLevel){
//                    player.setPlayerLevel(playerLevel++); //Level up
//                    player.setExpForNextLevel((int)expForNextLevel); //Experience for the next level
//                    player.setPlayerExp(0); //Reset experience points
//                }


                if(mOnDataChangeListener!=null){
                    mOnDataChangeListener.onDataChanged();
                }

                if(playerLevel!=player.getPlayerLevel()){
                    player.showDialogNewLevel(activity);
                }
            }
        });

        return convertView;
    }


    static class ViewHolder{
        private CardView cardView;
        private TextView taskName;
        private TextView taskDescription;
        private TextView raskRewardMoney;
        private TextView taskRewardExp;
        private ProgressBar progressBar;
    }


    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }

    public interface OnDataChangeListener{
        void onDataChanged();
    }
    class ProgressBarAsyncTask2 extends AsyncTask<Integer, Integer, Integer> {
        private Context mContext;
        private ProgressBar mProgressBar;
        public ProgressBarAsyncTask2(Context context, ProgressBar progressBar){
            mContext=context;
            mProgressBar=progressBar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("onpreexecute", "onPreExecute: ");

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
            Log.e("postexecute", "He acabado");
            playerLevel=player.getPlayerLevel();
            player.setPlayerMoney(player.getPlayerMoney()+task.getTaskRewardMoney());
            player.setPlayerExp(player.getPlayerExp()+task.getTaskRewardExp());

        }
    }


}

