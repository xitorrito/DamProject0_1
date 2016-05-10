package com.example.xito.damproject01.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xito.damproject01.R;
import com.example.xito.damproject01.Tasks;

import java.util.List;

public class TasksAdapter extends BaseAdapter  {

    private Context context;
    private List<Tasks> tasks;
    private Typeface font;
    private SQLiteDatabase db;
    private Tasks task;
    private int taskMinLevel;


    public TasksAdapter(Context context, List<Tasks> tasks, Typeface font, SQLiteDatabase db) {
        this.context = context;
        this.tasks=tasks;
        this.font=font;
        this.db=db;

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
            //viewHolder.cuadroFoto=(ImageView) convertView.findViewById(R.id.painting);


            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }


        viewHolder.taskName.setTypeface(font);
        viewHolder.taskDescription.setTypeface(font);
        viewHolder.taskRewardExp.setTypeface(font);
        viewHolder.raskRewardMoney.setTypeface(font);

        task = tasks.get(position);
        taskMinLevel= task.getTaskMinLevel();
        //if(taskMinLevel>playerLevel)


        viewHolder.taskName.setText(task.getTaskName());
        viewHolder.taskDescription.setText(task.getTaskDescription());
        viewHolder.taskRewardExp.setText(task.getTaskRewardExp()+"XP");
        viewHolder.raskRewardMoney.setText(task.getTaskRewardMoney()+"$");
        //viewHolder.cuadroFoto.setImageResource(cuadros.get(position).getPainting());
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Nombre", task.getTaskName());

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
    }



}

