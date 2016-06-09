package com.example.xito.damproject01.Adapters;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.xito.damproject01.Models.Player;
import com.example.xito.damproject01.ProgressBarAsyncTask;
import com.example.xito.damproject01.R;
import com.example.xito.damproject01.Models.Tasks;

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
    private boolean success;

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
            viewHolder.taskRewardMoney =(TextView)convertView.findViewById(R.id.task_reward_money);
            viewHolder.taskRewardExp =(TextView)convertView.findViewById(R.id.task_reward_exp);
            viewHolder.taskEnergy =(TextView)convertView.findViewById(R.id.textView_energy_listview);
            viewHolder.taskDefenses =(TextView)convertView.findViewById(R.id.textViewDefenses);
            viewHolder.progressBar=(ProgressBar) convertView.findViewById(R.id.progressBar_task);
            viewHolder.progressBarTaskLevel=(ProgressBar) convertView.findViewById(R.id.progressBar_taskLevel);
            viewHolder.upgradeTask=(Button) convertView.findViewById(R.id.buttonUpgradeTask);
            //viewHolder.cuadroFoto=(ImageView) convertView.findViewById(R.id.painting);


            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.taskName.setTypeface(font);
        viewHolder.taskDescription.setTypeface(font);
        viewHolder.taskRewardExp.setTypeface(font);
        viewHolder.taskRewardMoney.setTypeface(font);
        viewHolder.taskEnergy.setTypeface(font);
        viewHolder.taskDefenses.setTypeface(font);
        //viewHolder.progressBar.setVisibility(View.GONE);

        task = tasks.get(position);
        if(task.getTaskAntivirus()>player.getPlayerEfficacy()){
            viewHolder.taskDefenses.setTextColor(ContextCompat.getColor(context,android.R.color.holo_red_light));
        }else{
            viewHolder.taskDefenses.setTextColor(ContextCompat.getColor(context,R.color.material_teal_600));
        }
        if(task.getTaskEnergy()>player.getPlayerEnergy())
            viewHolder.taskEnergy.setTextColor(ContextCompat.getColor(context,android.R.color.holo_red_light));
        else{
            viewHolder.taskEnergy.setTextColor(ContextCompat.getColor(context,R.color.material_teal_600));

        }


        viewHolder.progressBarTaskLevel.setMax(task.getTaskTimesForLevelling());
        viewHolder.progressBarTaskLevel.setProgress(task.getTaskTimesCompleted());
        if(viewHolder.progressBarTaskLevel.getProgress()==viewHolder.progressBarTaskLevel.getMax()){
            viewHolder.upgradeTask.setEnabled(false);

        }else{
            viewHolder.upgradeTask.setEnabled(true);
        }

        viewHolder.taskName.setText(task.getTaskName());
        viewHolder.taskDescription.setText(task.getTaskDescription());
        viewHolder.taskRewardExp.setText("+"+task.getTaskRewardExp()+"XP");
        viewHolder.taskRewardMoney.setText("+"+task.getTaskRewardMoney()+"$");
        viewHolder.taskEnergy.setText("-"+task.getTaskEnergy()+"");
        viewHolder.taskDefenses.setText(task.getTaskAntivirus()+"");
        viewHolder.upgradeTask.setEnabled(false);
        //viewHolder.cuadroFoto.setImageResource(cuadros.get(position).getPainting());

        viewHolder.upgradeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task=tasks.get(position);
                task.setTaskLevel(task.getTaskLevel()+1);
                task.setTaskTimesForLevelling(task.getTaskTimesForLevelling()+5);
                ContentValues contentValues = new ContentValues();
                contentValues.put("taskLevel",task.getTaskLevel());
                contentValues.put("timesForLevelling",task.getTaskTimesForLevelling());
                db.update("tasks",contentValues, "id="+task.getTaskId(),null);
                notifyDataSetChanged();
            }
        });

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task = tasks.get(position);
                if(player.getPlayerEnergy()<task.getTaskEnergy()){
                    YoYo.with(Techniques.Shake)
                            .duration(700)
                            .playOn(viewHolder.taskEnergy);
                }else{
                    YoYo.with(Techniques.Pulse)
                            .duration(200)
                            .playOn(viewHolder.cardView);

                    playerLevel=player.getPlayerLevel();

                    if(!running){
                        asyncTask = new ProgressBarAsyncTask(context,viewHolder.progressBar, player, task,activity);
                        Log.e("time", task.getTaskTime()+"");
                        //asyncTask.execute(task.getTaskTime()); //100 = 10 seconds
                        asyncTask.execute(10); //TODO: CAMBIAR
                        running=true;
                    }

                    asyncTask.setOnDataChangeListenerAsymlTask(new ProgressBarAsyncTask.OnDataChangeListenerAsyncTask() {
                        @Override
                        public void onDataChanged(boolean success) {
                            if(success){
                                /*
                                if(task.getTaskTimesForLevelling()!=task.getTaskTimesCompleted())
                                    task.setTaskTimesCompleted(task.getTaskTimesCompleted()+1);
                                Log.e("task times",task.getTaskTimesCompleted()+"");
                                Log.e("times for compl",task.getTaskTimesForLevelling()+"");*/
                            }else{
                            }
                            if(mOnDataChangeListener!=null){
                                mOnDataChangeListener.onDataChanged(playerLevel);
                            }
                            showToastTaskFinished(success);
                        }
                    });
                }
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    private void showToastTaskFinished(boolean success) {
        LayoutInflater inflater = activity.getLayoutInflater();

        View layout = inflater.inflate(R.layout.toast,
                (ViewGroup) activity.findViewById(R.id.toastLayout));

        TextView textToast = (TextView) layout.findViewById(R.id.textToast);
        ImageView imageToast = (ImageView)layout.findViewById(R.id.imageToast);

        if(success) {
            textToast.setText("¡Tarea completada con exito!");
            imageToast.setImageDrawable(context.getResources().getDrawable(android.R.drawable.checkbox_on_background));
        }else {
            textToast.setText("Mision fracasada. El antivirus ha protegido tus ataques");
        }
        Toast toast = new Toast(context);
        //toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
        YoYo.with(Techniques.FadeIn)
                .duration(100)
                .playOn(textToast);
        YoYo.with(Techniques.FadeIn)
                .duration(100)
                .playOn(imageToast);
    }


    static class ViewHolder{
        private CardView cardView;
        private TextView taskName;
        private TextView taskDescription;
        private TextView taskRewardMoney;
        private TextView taskRewardExp;
        private ProgressBar progressBar;
        private ProgressBar progressBarTaskLevel;
        private TextView taskEnergy;
        private TextView taskDefenses;
        private Button upgradeTask;
    }


    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }

    public interface OnDataChangeListener{
        void onDataChanged(int playerlevel);
    }


}

