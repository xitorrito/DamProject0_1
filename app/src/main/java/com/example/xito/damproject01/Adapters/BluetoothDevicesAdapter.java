package com.example.xito.damproject01.Adapters;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.xito.damproject01.Models.Player;
import com.example.xito.damproject01.ProgressBarAsyncTask;
import com.example.xito.damproject01.R;

import java.util.List;

public class BluetoothDevicesAdapter extends BaseAdapter  {

    private Context context;
    private List<BluetoothDevice> devices;
    private Typeface font;
    private SQLiteDatabase db;
    private int taskMinLevel;
    private Player player = Player.player;
    private int playerLevel;
    private OnDataChangeListener mOnDataChangeListener;
    private Activity activity;
    private ListView listView;
    private ProgressBarAsyncTask asyncTask;
    public static boolean running=false;

    public BluetoothDevicesAdapter(Context context, List<BluetoothDevice> devices, Typeface font, SQLiteDatabase db, Activity activity) {
        this.context = context;
        this.devices=devices;
        this.font=font;
        this.db=db;
        this.activity=activity;

    }

    public BluetoothDevicesAdapter(Context context, List<BluetoothDevice> devices, Typeface font, SQLiteDatabase db) {
        this.context = context;
        this.devices=devices;
        this.font=font;
        this.db=db;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return devices.size();
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
            convertView=inflater.inflate(R.layout.item_bluetooth_devices,null);
            viewHolder.cardView = (CardView) convertView.findViewById(R.id.card_view);
            viewHolder.deviceName =(TextView)convertView.findViewById(R.id.device_name);
            viewHolder.devideAddress =(TextView)convertView.findViewById(R.id.device_address);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.deviceName.setTypeface(font);
        viewHolder.devideAddress.setTypeface(font);
        //viewHolder.progressBar.setVisibility(View.GONE);

        viewHolder.deviceName.setText(devices.get(position).getName());
        viewHolder.devideAddress.setText(devices.get(position).getAddress());

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YoYo.with(Techniques.Pulse)
                        .duration(200)
                        .playOn(viewHolder.cardView);
                Toast.makeText(context, devices.get(position).toString()+"", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, devices.get(position).describeContents()+"", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, devices.get(position).getBondState()+"", Toast.LENGTH_SHORT).show();

                /*if(!running){
                    asyncTask = new ProgressBarAsyncTask(context,viewHolder.progressBar, player, task);
                    asyncTask.execute(100); //100 = 10 seconds
                    running=true;
                }*/

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

