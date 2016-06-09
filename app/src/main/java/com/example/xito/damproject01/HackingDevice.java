package com.example.xito.damproject01;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.xito.damproject01.Adapters.NumberAdapter;
import com.example.xito.damproject01.Models.Networks;
import com.example.xito.damproject01.Models.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HackingDevice extends Activity {
    private TableLayout tl;
    private GridView table;
    private NumberAdapter adapter;
    private ProgressBar progressBar;
    private AsynktaskBar asynktaskBar;
    private int i=0;
    private Random random;
    private  int range;
    private TextView text1;
    private TextView text2;
    private TextView textCountDown;
    private ArrayList<Integer> numbersForTable;
    private ArrayList<Integer> randomNumbers;
    private ArrayList<Integer> numbersForSearch;
    private int numbersCorrect=0;
    private int numberCountDown=0;
    private TextView t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22, t23, t24, t25, t26,
        t27, t28, t29, t30, t31, t32, t33, t34, t35, t36, t37, t38, t39, t40, t41, t42, t43, t44, t45, t46, t47, t48, t49, t50, t51, t52,
        t53, t54, t55, t56, t57, t58, t59, t60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hacking_device);
        numbersForTable = new ArrayList<>();
        randomNumbers = new ArrayList<>();
        numbersForSearch = new ArrayList<>();

        random = new Random();
        range = 9 - 1 + 1;
        while(numbersCorrect<9){
            numbersForTable.clear();
            numbersCorrect=0;
            for (int i = 1; i <= 30; i++) {
                numbersForTable.add(random.nextInt(range) + 1);
            }
            for (int i = 1; i <=9 ; i++) {
                if (numbersForTable.contains(i)) {
                    numbersCorrect++;
                }
            }
        }

        for (int i = 1; i <= 9; i++) {
            randomNumbers.add(i);
        }

        Collections.shuffle(randomNumbers);
        for (int i = 0; i < 6; i++) {
            numbersForSearch.add(randomNumbers.get(i));
        }




        table= (GridView)findViewById(R.id.table);
        table.setVisibility(View.INVISIBLE);
        text1= (TextView) findViewById(R.id.textTable1);
        text2= (TextView) findViewById(R.id.textTable2);
        textCountDown= (TextView) findViewById(R.id.textCountDown);
        text2.setVisibility(View.INVISIBLE);
        text1.setVisibility(View.INVISIBLE);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/LipbyChonk.ttf");
        textCountDown.setTypeface(font);
        text2.setTypeface(font);
        text1.setTypeface(font);


        progressBar=(ProgressBar) findViewById(R.id.progressTable);
        progressBar.setVisibility(View.INVISIBLE);
        asynktaskBar = new AsynktaskBar();
        final CountDownTimer Count = new CountDownTimer(3000, 500) {
            public void onTick(long millisUntilFinished) {
                textCountDown.setText(String.valueOf((millisUntilFinished / 1000+1)));
                if(numberCountDown!=(int)millisUntilFinished / 1000+1){
                    YoYo.with(Techniques.FadeIn)
                            .duration(300)
                            .playOn(textCountDown);
                }else{
                    YoYo.with(Techniques.FadeOut)
                            .duration(300)
                            .playOn(textCountDown);
                }
                numberCountDown=((int)millisUntilFinished / 1000+1);


            }

            public void onFinish() {
                textCountDown.setVisibility(View.GONE);
                text1.setText("Encuentra el numero ");
                text2.setVisibility(View.VISIBLE);
                text1.setVisibility(View.VISIBLE);
                text2.setText(String.valueOf(randomNumbers.get(0)));
                table.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeIn)
                        .duration(50)
                        .playOn(table);
                progressBar.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInUp)
                        .duration(100)
                        .playOn(progressBar);
                asynktaskBar.execute(300);
                adapter= new NumberAdapter(getApplicationContext(),numbersForTable, numbersForSearch);
                table.setAdapter(adapter);
                adapter.setOnDataChangeListener(new NumberAdapter.OnDataChangeListenerTable() {
                    @Override
                    public void onDataChanged(int level) {
                        if (level<6){
                            text2.setText(String.valueOf(randomNumbers.get(level)));
                            YoYo.with(Techniques.ZoomInRight)
                                    .duration(300)
                                    .playOn(text2);
                        }else{
                            showDialogGameCompleted();
                            asynktaskBar.cancel(true);
                        }
                    }
                });


            }
        };
        Count.start();


       /* CountDownTimer countDownTimer;
        countDownTimer=new CountDownTimer(20000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                i++;

                progressBar.setProgress((int) Math.round(millisUntilFinished / 1000.0));
            }

            @Override
            public void onFinish() {
                //Do what you want
                i++;
                progressBar.setProgress(0);
            }
        };
        countDownTimer.start();


        adapter.setOnDataChangeListener(new NumberAdapter.OnDataChangeListenerTable() {
            @Override
            public void onDataChanged(int progress) {


            }
        });

/*

        Random random2 = new Random();
        int range2 = 30 - 1 + 1;

        numbersSearch.add(random2.nextInt(range2)+1); //position
        if(numbers.get(numbersSearch.get(0))==1||numbers.get(numbersSearch.get(0))==5 || numbers.get(numbersSearch.get(0))==6
                ||numbers.get(numbersSearch.get(0))==10 || numbers.get(numbersSearch.get(0))==11||numbers.get(numbersSearch.get(0))==15 || numbers.get(numbersSearch.get(0))==16
                ||numbers.get(numbersSearch.get(0))==20 || numbers.get(numbersSearch.get(0))==21||numbers.get(numbersSearch.get(0))==25 || numbers.get(numbersSearch.get(0))==26
                ||numbers.get(numbersSearch.get(0))==30){

        }
        int position = random2.nextInt(range2)+1;

        //left column
        if(numbers.get(position)==1 || numbers.get(position)==6 || numbers.get(position)==11|| numbers.get(position)==16 || numbers.get(position)==21|| numbers.get(position)==26){
            //first row
            if(numbers.get(position)==1 || numbers.get(position)==2 || numbers.get(position)==3|| numbers.get(position)==4 || numbers.get(position)==5){

            }

        }
        //row 1: 1-5)
                //1-5
        //row 2: 6-10
        //row 3: 11-15
        //row 4: 16-20
        //row 5: 21-25
        //row 6: 26-30

       // if(numbersSearch.get(0)==1||)
        //position == i;




       /* Random random = new Random();
        int range = 9 - 1 + 1;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {

                numbers[i][j]=random.nextInt(range) + 1;
            }
        }
        /*
        for (int k = 0; k <2; k++) {

            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            t1 = new TextView(this);
            t2 = new TextView(this);
            t3 = new TextView(this);
            t4 = new TextView(this);

            for (int i = 0; i < numbers.length; i++) {
                for (int j = 0; j < numbers.length; j++) {

                    numbers[i][j]=random.nextInt(range) + 1;
                    t1.setText(numbers[i][j]);
                    t1.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                    t1.setTextSize(17);

                }
                row.addView(t1);
            }

            t1.setText(String.valueOf(random.nextInt(range) + 1));
            t2.setText(String.valueOf(random.nextInt(range) + 1));
            t3.setText(String.valueOf(random.nextInt(range) + 1));
            t4.setText(String.valueOf(random.nextInt(range) + 1));

            t1.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            t2.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            t3.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            t4.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

            t1.setTextSize(17);
            t2.setTextSize(17);
            t3.setTextSize(17);
            t4.setTextSize(17);



            row.addView(t1);
            row.addView(t2);
            row.addView(t3);
            row.addView(t4);

            row.setGravity(Gravity.CENTER_HORIZONTAL );
            row.setGravity(Gravity.CENTER_VERTICAL );


        }*/

    }

    private void showDialogGameOver(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HackingDevice.this);
        alertDialogBuilder.setMessage("¡Intenta ser mas rapido la proxima vez!")
                .setTitle("Hackeo fallido")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setNeutralButton("Volver a intentar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                })
                .setCancelable(false)
                .show();

    }

    private void showDialogGameCompleted(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HackingDevice.this);
        alertDialogBuilder.setMessage("¡Has conseguido acceder a la red wifi de la víctima!\n\n+100 XP  +100$")
                .setTitle("Hackeo completado")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        int playerLevel=Player.player.getPlayerLevel();
                        Player.player.setPlayerExp(Player.player.getPlayerExp()+100);
                        Player.player.setPlayerMoney(Player.player.getPlayerMoney()+100);
                       /* if(playerLevel!=Player.player.getPlayerLevel()){
                            Player.showDialogNewLevel();
                        }*/

                        Intent intent = new Intent(getApplicationContext(),HackDevices.class);
                        startActivity(intent);
                        finish();

                    }
                })
                .setNeutralButton("Ver mapa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LocationManager locationManager;
                        boolean gps_enabled= false,network_enabled = false;

                        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                        try{
                            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        }catch(Exception ex){}

                        try{
                            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                        }catch(Exception ex){}

                        if(gps_enabled || network_enabled){
                            Intent intent = getIntent();
                            Intent intent2 = new Intent(getApplicationContext(), MapsActivity.class);
                            ScanResult scanResult = intent.getExtras().getParcelable("network");
                            intent2.putExtra("network", scanResult);
                            startActivity(intent2);
                            finish();

                        }else {
                            Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(myIntent);
                            Toast.makeText(HackingDevice.this, "Debes activar la ubicación", Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .setCancelable(false)
                .show();

    }


    class AsynktaskBar extends AsyncTask<Integer, Integer, Integer> {

        boolean isRunning = true;

        public void stop() {
            isRunning = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setProgress(100);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            for (i=100; i >=0;i--) {
                publishProgress(i);
                SystemClock.sleep(params[0]);
                if (isCancelled()) {
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            progressBar.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            numbersForTable.clear();
            adapter.notifyDataSetChanged();
            showDialogGameOver();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        asynktaskBar.cancel(true);
    }

    @Override
    protected void onStop() {
        super.onStop();
        asynktaskBar.cancel(true);
    }
}

