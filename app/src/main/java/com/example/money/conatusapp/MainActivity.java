package com.example.money.conatusapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.money.conatusapp.NavigationDrawer.NavigationDrawerActivityListner;
import com.example.money.conatusapp.NavigationDrawer.NavigationDrawerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationDrawerActivityListner {

    @BindView(R.id.mainpage_toolbar)
    Toolbar mMainToolbar;

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mMainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFragmentManager = getSupportFragmentManager();
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navFragment);
        mNavigationDrawerFragment.setOnNavigationDrawerActivityListner(this);
        mNavigationDrawerFragment.setUp(R.id.navFragment, (DrawerLayout) findViewById(R.id.main_page_drawer), mMainToolbar);


    }

    @Override
    public void onItemClickedInDrawer(View view, int position) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (position == 1) {
            AboutUsFragment fragment = new AboutUsFragment();
            transaction.add(R.id.container_frame, fragment, "AboutUs");
        } else if (position == 2) {
            AboutUsFragment fragment = new AboutUsFragment();
            transaction.add(R.id.container_frame, fragment, "AboutUs");
        } else if (position == 3) {
            AboutUsFragment fragment = new AboutUsFragment();
            transaction.add(R.id.container_frame, fragment, "AboutUs");
        } else if (position == 4) {
            AboutUsFragment fragment = new AboutUsFragment();
            transaction.add(R.id.container_frame, fragment, "AboutUs");
        } else if (position == 5) {
            AboutUsFragment fragment = new AboutUsFragment();
            transaction.add(R.id.container_frame, fragment, "AboutUs");
        } else if (position == 6) {
            AboutUsFragment fragment = new AboutUsFragment();
            transaction.add(R.id.container_frame, fragment, "AboutUs");
        } else if (position == 7) {
            AboutUsFragment fragment = new AboutUsFragment();
            transaction.add(R.id.container_frame, fragment, "AboutUs");
        }
        transaction.commit();


    }
}


