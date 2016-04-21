package com.example.xito.damproject01;

import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); //Remove the HomeAsUpButton arrow

        setTabIcons(tabLayout);





    }
    /*
    public void setPlayerStates() {
        Cursor cursor = db.query("player",nivel, null,null,null,null,null);
        //Vamos a la primera fila.
        cursor.moveToFirst();
        //Obtenemos el dato de la primera (y única) columna.
        int coins = cursor.getInt(0);

        Cursor cursor = bd.query("player",exp, null,null,null,null,null);
        //Vamos a la primera fila.
        cursor.moveToFirst();
        //Obtenemos el dato de la primera (y única) columna.
        int level = cursor.getInt(0);

}
        */

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment1(), "");
        adapter.addFragment(new Fragment2(), "");
        adapter.addFragment(new Fragment3(), "");
        viewPager.setAdapter(adapter);


    }

    public void setTabIcons(TabLayout tabIcons) {
        tabLayout.getTabAt(0).setIcon(R.drawable.pc_icon );
        tabLayout.getTabAt(1).setIcon(R.drawable.coin_icon);
        tabLayout.getTabAt(2).setIcon(R.drawable.tasks_icon);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
