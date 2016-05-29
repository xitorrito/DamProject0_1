package com.example.xito.damproject01.Fragments;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.example.xito.damproject01.Adapters.MarketAdapter;
import com.example.xito.damproject01.DBManager;
import com.example.xito.damproject01.Models.Item;
import com.example.xito.damproject01.Models.Player;
import com.example.xito.damproject01.R;

import java.util.ArrayList;
import java.util.List;


public class Fragment2 extends Fragment {

    private GridView gridView;
    private Item item;
    private List<Item> items;
    private MarketAdapter adapter;
    private Typeface font;
    private SQLiteDatabase db;
    private DBManager dbManager;
    private Player player;
    private TextView levelText;
    private TextView level;
    private TextView expText;
    private TextView exp;
    private TextView moneyText;
    private TextView money;
    private TextView title;
    private TextView energy;
    private TextView efficacy;

    public Fragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbManager=DBManager.getInstance(getContext());
        db=dbManager.getWritableDatabase();
        player=Player.player;
        items =new ArrayList<>(Item.items);
        font = Typeface.createFromAsset(getContext().getAssets(), "fonts/LipbyChonk.ttf");
        adapter = new MarketAdapter(getContext(),items,font,player,getActivity(),db);
        adapter.setOnDataChangeListener(new MarketAdapter.OnDataChangeListenerMarket() {
            @Override
            public void onDataChanged() {
                updateTextViews();
            }
        });



    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = (GridView)view.findViewById(R.id.gridViewMarket);
        levelText=(TextView)view.findViewById(R.id.textView_level);
        level=(TextView)view.findViewById(R.id.textView_levelNumber);
        expText=(TextView)view.findViewById(R.id.textView_xpText);
        exp=(TextView)view.findViewById(R.id.textView_xp);
        moneyText =(TextView)view.findViewById(R.id.textView_moneyText);
        money =(TextView)view.findViewById(R.id.textView_MoneyNumber);
        energy =(TextView)view.findViewById(R.id.textView_energy);
        efficacy =(TextView)view.findViewById(R.id.textView_efficacy);

        font = Typeface.createFromAsset(getContext().getAssets(), "fonts/LipbyChonk.ttf");

        levelText.setTypeface(font);
        level.setTypeface(font);
        expText.setTypeface(font);
        exp.setTypeface(font);
        moneyText.setTypeface(font);
        money.setTypeface(font);

        gridView.setAdapter(adapter);
        updateTextViews();

    }

    public void updateTextViews() {

        level.setText(player.getPlayerLevel()+"");
        exp.setText(player.getPlayerExp()+"");
        money.setText(player.getPlayerMoney()+"");
        efficacy.setText(player.getPlayerEfficacy()+"");
        energy.setText(player.getPlayerEnergy()+"");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment2, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        //updateTextViews(); //update al volver
        if (!db.isOpen()) {
            db = dbManager.getWritableDatabase();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            updateTextViews();
            if (!isVisibleToUser) {
            }
        }
    }
}
