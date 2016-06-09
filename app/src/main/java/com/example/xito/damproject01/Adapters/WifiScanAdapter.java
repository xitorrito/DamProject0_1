package com.example.xito.damproject01.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.wifi.ScanResult;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.xito.damproject01.HackingDevice;
import com.example.xito.damproject01.Models.Networks;
import com.example.xito.damproject01.R;

import java.util.ArrayList;
import java.util.List;

public class WifiScanAdapter extends BaseAdapter  {

    private Context context;
    private List<ScanResult> networks;
    private Typeface font;
    private SQLiteDatabase db;
    private int playerLevel;
    private OnDataChangeListener mOnDataChangeListener;
    private Activity activity;
    private ArrayList<Networks> networksFromDB;


    public WifiScanAdapter(Context context, List<ScanResult> networks, Typeface font, SQLiteDatabase db, Activity activity) {
        this.context = context;
        this.networks=networks;
        this.font=font;
        this.db=db;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return networks.size();
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
        networksFromDB = Networks.getNetworksFromDB(db);

        final ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView=inflater.inflate(R.layout.item_wifi_network,null);
            viewHolder.cardView = (CardView) convertView.findViewById(R.id.card_view);
            viewHolder.deviceName =(TextView)convertView.findViewById(R.id.device_name);
            viewHolder.devideAddress =(TextView)convertView.findViewById(R.id.device_address);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.deviceName.setTypeface(font);
        viewHolder.devideAddress.setTypeface(font);

        viewHolder.deviceName.setText(networks.get(position).SSID);
        viewHolder.devideAddress.setText(networks.get(position).BSSID);
/*
        for (int i = 0; i <networksFromDB.size() ; i++) {
            if (viewHolder.devideAddress.getText().toString().equals(networksFromDB.get(i).getMacAddress())){
                viewHolder.cardView.setCardBackgroundColor(R.color.colorPrimary);
                viewHolder.deviceName.setTextColor(Color.WHITE);
                viewHolder.devideAddress.setTextColor(Color.WHITE);
            }
        }
*/
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean networkAttacked=false;
                if(networksFromDB.size()>0){
                    for (int i = 0; i <networksFromDB.size() ; i++) {
                        if (viewHolder.devideAddress.getText().toString().equals(networksFromDB.get(i).getMacAddress())) {
                            networkAttacked=true;
                        }
                    }
                }

                if(!networkAttacked){
                    YoYo.with(Techniques.Pulse)
                            .duration(200)
                            .playOn(viewHolder.cardView);
                    //Toast.makeText(context, networks.get(position).toString()+"", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(activity, HackingDevice.class);
                    intent.putExtra("network",networks.get(position));
                    activity.startActivity(intent);
                    activity.finish();
                    if(mOnDataChangeListener!=null){
                        mOnDataChangeListener.onDataChanged();
                    }
                }else{
                    Toast.makeText(context, "Ya has hackeado esta red", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }


    static class ViewHolder{
        private CardView cardView;
        private TextView deviceName;
        private TextView devideAddress;
        //private TextView raskRewardMoney;
       // private TextView taskRewardExp;
        //private ProgressBar progressBar;
    }


    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }

    public interface OnDataChangeListener{
        void onDataChanged();
    }
    /*
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
    }*/


}

