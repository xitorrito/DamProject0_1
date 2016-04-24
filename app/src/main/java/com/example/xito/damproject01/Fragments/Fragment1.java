package com.example.xito.damproject01.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.xito.damproject01.R;


public class Fragment1 extends Fragment implements View.OnClickListener {

    TextView levelText;
    TextView level;
    TextView expText;
    TextView exp;
    TextView moneyText;
    TextView money;
    Button boton;
    TabLayout tabLayout;
    ViewPager viewPager;

    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        levelText=(TextView)getView().findViewById(R.id.textView_level);
        level=(TextView)getView().findViewById(R.id.textView_levelNumber);
        expText=(TextView)getView().findViewById(R.id.textView_xpText);
        exp=(TextView)getView().findViewById(R.id.textView_xp);
        moneyText =(TextView)getView().findViewById(R.id.textView_moneyText);
        money =(TextView)getView().findViewById(R.id.textView_MoneyNumber);
        boton=(Button)getView().findViewById(R.id.buttonGoTab);
        boton.setOnClickListener(this);
        tabLayout= (TabLayout)getView().findViewById(R.id.tabs);
        viewPager = (ViewPager)getActivity().findViewById(R.id.viewpager);


        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "fonts/LipbyChonk.ttf");
        levelText.setTypeface(font);
        level.setTypeface(font);
        expText.setTypeface(font);
        exp.setTypeface(font);
        moneyText.setTypeface(font);
        money.setTypeface(font);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment1, container, false);
    }

    @Override
    public void onClick(View v) {
        viewPager.setCurrentItem(2);

    }
}
