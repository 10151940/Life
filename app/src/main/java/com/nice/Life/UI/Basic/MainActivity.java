package com.nice.Life.UI.Basic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.example.life.R;
import com.nice.Life.UI.delicious.DeliciousFragment;
import com.nice.Life.UI.mycenter.MycenterFrament;
import com.nice.Life.UI.weather.WeatherFrament;

public class MainActivity extends BaseActivity {

    private BottomNavigationView bottomNavigationView;
    private WeatherFrament       weatherFrament;
    private DeliciousFragment    deliciousFragment;
    private MycenterFrament      mycenterFrament;
    private Fragment[]           fragments;
    private int                  lastfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
    }

    private  void initFragment() {
        weatherFrament = new WeatherFrament();
        deliciousFragment = new DeliciousFragment();
        mycenterFrament = new MycenterFrament();
        fragments = new Fragment[]{weatherFrament, deliciousFragment, mycenterFrament};
        lastfragment = 0;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainView, weatherFrament)
                .show(weatherFrament)
                .commit();
        bottomNavigationView = findViewById(R.id.bnv);
        bottomNavigationView.setOnNavigationItemSelectedListener(changeFragment);
    }

    //判断选择的菜单
    private BottomNavigationView.OnNavigationItemSelectedListener changeFragment = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.item_weather: {
                    if (lastfragment != 0) {
                        switchFragment(lastfragment, 0);
                        lastfragment = 0;
                    }
                    return true;
                }
                case R.id.item_delicious: {
                    if (lastfragment != 1) {
                        switchFragment(lastfragment, 1);
                        lastfragment = 1;
                    }
                    return  true;
                }
                case R.id.item_mycenter: {
                    if (lastfragment != 2) {
                        switchFragment(lastfragment, 2);
                        lastfragment = 2;
                    }
                    return  true;
                }
            }
            return false;
        }
    };

    //切换Fragment
    private void switchFragment(int lastfragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);
        if (fragments[index].isAdded() == false) {
            transaction.add(R.id.mainView, fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

}
