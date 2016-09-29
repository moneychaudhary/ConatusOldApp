
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
import com.example.money.conatusapp.TeamMembers.TeamFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationDrawerActivityListner {

    @BindView(R.id.mainpage_toolbar)
    Toolbar mMainToolbar;

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private FragmentManager mFragmentManager;
    private final static String HOME_FRAGMENT = "home";
    private final static String EVENT_FRAGMENT = "event";
    private final static String MAGAZINE_FRAGMENT = "magazine";
    private final static String GALLERY_FRAGMENT = "galley";
    private final static String TEAM_FRAGMENT = "team";
    private final static String ABOUT_FRAGMENT = "about";
    private final static String CONTACT_FRAGMENT = "contact";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mMainToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        HomeFragment fragment = new HomeFragment();
        transaction.add(R.id.container_frame, fragment, HOME_FRAGMENT);
        transaction.commit();
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navFragment);
        mNavigationDrawerFragment.setOnNavigationDrawerActivityListner(this);
        mNavigationDrawerFragment.setUp(R.id.navFragment, (DrawerLayout) findViewById(R.id.main_page_drawer), mMainToolbar);


    }

    @Override
    public void onItemClickedInDrawer(View view, int position) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (position == 0) {
            HomeFragment fragment = new HomeFragment();
            transaction.replace(R.id.container_frame, fragment, HOME_FRAGMENT);
        } else if (position == 1) {
            EventsFragment fragment = new EventsFragment();
            transaction.replace(R.id.container_frame, fragment, EVENT_FRAGMENT);
        } else if (position == 2) {
            MagzineFragment fragment = new MagzineFragment();
            transaction.replace(R.id.container_frame, fragment, MAGAZINE_FRAGMENT);
        } else if (position == 3) {
            GalleryFragment fragment = new GalleryFragment();
            transaction.replace(R.id.container_frame, fragment, GALLERY_FRAGMENT);
        } else if (position == 4) {
            TeamFragment fragment = new TeamFragment();
            transaction.replace(R.id.container_frame, fragment, TEAM_FRAGMENT);
        } else if (position == 5) {
            AboutUsFragment fragment = new AboutUsFragment();
            transaction.replace(R.id.container_frame, fragment, ABOUT_FRAGMENT);
        } else if (position == 6) {
            ContactFragment fragment = new ContactFragment();
            transaction.replace(R.id.container_frame, fragment, CONTACT_FRAGMENT);
        }
        transaction.commit();


    }
}


