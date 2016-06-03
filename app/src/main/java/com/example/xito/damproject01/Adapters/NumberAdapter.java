package com.example.xito.damproject01.Adapters;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.xito.damproject01.Models.Item;
import com.example.xito.damproject01.Models.Player;
import com.example.xito.damproject01.ProgressBarAsyncTask;
import com.example.xito.damproject01.R;

import java.util.ArrayList;
import java.util.List;

public class NumberAdapter extends BaseAdapter  {

    private Context context;
    private ArrayList<Integer> mNumbers;
    private ArrayList<Integer> numbersForSearch;
    private Typeface font;
    private SQLiteDatabase db;
    private int taskMinLevel;
    private Player player;
    private int playerLevel;
    private OnDataChangeListenerTable mOnDataChangeListener;
    private Activity activity;
    private int level=0;
    private int number;


    public NumberAdapter(Context context, ArrayList<Integer> numbers, Typeface font, Player player, Activity activity, SQLiteDatabase db) {
        this.context = context;
        this.mNumbers=numbers;
        this.font=font;
        this.activity=activity;
        this.player=player;
        this.db=db;

    }

    public NumberAdapter(Context context, ArrayList<Integer> numbers, ArrayList<Integer> numbersForSearch) {
        this.context=context;
        this.mNumbers=numbers;
        this.numbersForSearch=numbersForSearch;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mNumbers.size();
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
        final ViewHolder viewHolder;
        number = mNumbers.get(position);

        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.item_table,null);
            viewHolder.cardView = (CardView) convertView.findViewById(R.id.card_number);
            //viewHolder.layout = (RelativeLayout) convertView.findViewById(R.id.relative_layout_table);
            viewHolder.number=(TextView)convertView.findViewById(R.id.number_text);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.number.setTypeface(font);

        viewHolder.number.setText(String.valueOf(mNumbers.get(position)));

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(level<6)
                if(viewHolder.number.getText().equals(numbersForSearch.get(level)+"")) {

                    if (viewHolder.number.getCurrentTextColor() == (Color.BLACK)) {
                        viewHolder.cardView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary));
                        viewHolder.number.setTextColor(Color.LTGRAY);
                        mNumbers.set(position, 0);

                        if(!mNumbers.contains(numbersForSearch.get(level))){
                            level++;
                            if (mOnDataChangeListener != null) {
                                mOnDataChangeListener.onDataChanged(level);
                            }
                        }


                    }else{

                        //viewHolder.cardView.setBackgroundColor(ContextCompat.getColor(context, R.color.material_green_500));
                        //viewHolder.number.setTextColor(ContextCompat.getColor(context, android.R.color.black));
                    }
                }







            }
        });
        return convertView;
    }

    static class ViewHolder{
        private CardView cardView;
        private TextView number;
        private RelativeLayout layout;

    }


    public void setOnDataChangeListener(OnDataChangeListenerTable onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }

    public interface OnDataChangeListenerTable{
        void onDataChanged(int i);
    }


}

