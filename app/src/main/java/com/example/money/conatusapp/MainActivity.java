
package com.example.money.conatusapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.money.conatusapp.Gallery.GalleryFragment;
import com.example.money.conatusapp.Home.HomeFragment;
import com.example.money.conatusapp.NavigationDrawer.NavigationDrawerActivityListner;
import com.example.money.conatusapp.NavigationDrawer.NavigationDrawerFragment;
import com.example.money.conatusapp.TeamMembers.TeamFragment;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.List;

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
    private HomeFragment mHomeFragment;
    private EventsFragment mEventsFragment;
    private MagzineFragment mMagazineFragment;
    private GalleryFragment mGalleryFragment;
    private TeamFragment mTeamFragment;
    private AboutUsFragment mAboutUsFragment;
    private ContactFragment mContactFragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maintoolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mMainToolbar);

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
//        Picasso.setSingletonInstance(built);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mFragmentManager = getSupportFragmentManager();
        mHomeFragment = new HomeFragment();
        mFragmentManager.beginTransaction().add(R.id.container_frame, mHomeFragment, HOME_FRAGMENT).commit();
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navFragment);
        mNavigationDrawerFragment.setOnNavigationDrawerActivityListner(this);
        mNavigationDrawerFragment.setUp(R.id.navFragment, (DrawerLayout) findViewById(R.id.main_page_drawer), mMainToolbar);


    }

    @Override
    public void onItemClickedInDrawer(int position) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        List<Fragment> fragmentList = mFragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            transaction.hide(fragment);
        }

        switch (position) {
            case 0:
                mMainToolbar.setTitle("Conatus");
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.container_frame, mHomeFragment, HOME_FRAGMENT);
                } else {
                    transaction.show(mFragmentManager.findFragmentByTag(HOME_FRAGMENT));
                }
                break;
            case 1:
                mMainToolbar.setTitle("Events");
                if (mEventsFragment == null) {
                    mEventsFragment = new EventsFragment();
                    transaction.add(R.id.container_frame, mEventsFragment, EVENT_FRAGMENT);
                } else {
                    transaction.show(mFragmentManager.findFragmentByTag(EVENT_FRAGMENT));
                }
                break;
            case 3:
                mMainToolbar.setTitle("Gallery");
                if (mGalleryFragment == null) {
                    mGalleryFragment = new GalleryFragment();
                    transaction.add(R.id.container_frame, mGalleryFragment, GALLERY_FRAGMENT);
                } else {
                    transaction.show(mFragmentManager.findFragmentByTag(GALLERY_FRAGMENT));
                }
                break;
            case 4:
                mMainToolbar.setTitle("Our Team");
                if (mTeamFragment == null) {
                    mTeamFragment = new TeamFragment();
                    transaction.add(R.id.container_frame, mTeamFragment, TEAM_FRAGMENT);
                } else {
                    transaction.show(mFragmentManager.findFragmentByTag(TEAM_FRAGMENT));
                }
                break;
            case 5:
                mMainToolbar.setTitle("About Us");
                if (mAboutUsFragment == null) {
                    mAboutUsFragment = new AboutUsFragment();
                    transaction.add(R.id.container_frame, mAboutUsFragment, ABOUT_FRAGMENT);
                } else {
                    transaction.show(mFragmentManager.findFragmentByTag(ABOUT_FRAGMENT));
                }
                break;
            case 6:
                mMainToolbar.setTitle("Contact Us");
                if (mContactFragment == null) {
                    mContactFragment = new ContactFragment();
                    transaction.add(R.id.container_frame, mContactFragment, CONTACT_FRAGMENT);
                } else {
                    transaction.show(mFragmentManager.findFragmentByTag(CONTACT_FRAGMENT));
                }
        }
        transaction.commit();


    }
}


