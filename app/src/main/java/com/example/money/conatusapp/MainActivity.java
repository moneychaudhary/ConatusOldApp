
package com.example.money.conatusapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.money.conatusapp.NavigationDrawer.NavigationDrawerActivityListner;
import com.example.money.conatusapp.NavigationDrawer.NavigationDrawerFragment;
import com.example.money.conatusapp.TeamMembers.Home.HomeFragment;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContactFragment = new ContactFragment();
        mTeamFragment = new TeamFragment();
        mFragmentManager = getSupportFragmentManager();
        mHomeFragment = new HomeFragment();
        mFragmentManager.beginTransaction().add(R.id.container_frame, mHomeFragment, HOME_FRAGMENT).addToBackStack(HOME_FRAGMENT).commit();
        mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navFragment);
        mNavigationDrawerFragment.setOnNavigationDrawerActivityListner(this);
        mNavigationDrawerFragment.setUp(R.id.navFragment, (DrawerLayout) findViewById(R.id.main_page_drawer), mMainToolbar);


    }

    @Override
    public void onItemClickedInDrawer(int position) {

        switch (position) {
            case 0:
                mMainToolbar.setTitle("Conatus");
                if (mHomeFragment != null)
                    mHomeFragment = new HomeFragment();
                mFragmentManager.beginTransaction().replace(R.id.container_frame, mHomeFragment, HOME_FRAGMENT).addToBackStack(HOME_FRAGMENT).commit();
                break;
            case 1:
                mMainToolbar.setTitle("Events");
                if (mEventsFragment == null)
                    mEventsFragment = new EventsFragment();
                mFragmentManager.beginTransaction().replace(R.id.container_frame, mEventsFragment, EVENT_FRAGMENT).addToBackStack(EVENT_FRAGMENT).commit();
                break;
            case 2:
                mMainToolbar.setTitle("Magazine");
                if (mMagazineFragment == null)
                    mMagazineFragment = new MagzineFragment();
                mFragmentManager.beginTransaction().replace(R.id.container_frame, mMagazineFragment, MAGAZINE_FRAGMENT).addToBackStack(MAGAZINE_FRAGMENT).commit();
                break;
            case 3:
                mMainToolbar.setTitle("Gallery");
                if (mGalleryFragment == null)
                    mGalleryFragment = new GalleryFragment();
                mFragmentManager.beginTransaction().replace(R.id.container_frame, mGalleryFragment, GALLERY_FRAGMENT).addToBackStack(GALLERY_FRAGMENT).commit();
                break;
            case 4:
                mMainToolbar.setTitle("Our Team");
                if (mTeamFragment == null)
                    mTeamFragment = new TeamFragment();
                mFragmentManager.beginTransaction().replace(R.id.container_frame, mTeamFragment, TEAM_FRAGMENT).addToBackStack(TEAM_FRAGMENT).commit();
                break;
            case 5:
                mMainToolbar.setTitle("About Us");
                if (mAboutUsFragment == null)
                    mAboutUsFragment = new AboutUsFragment();
                mFragmentManager.beginTransaction().replace(R.id.container_frame, mAboutUsFragment, ABOUT_FRAGMENT).addToBackStack(ABOUT_FRAGMENT).commit();
                break;
            case 6:
                mMainToolbar.setTitle("Contact Us");
                if (mContactFragment == null)
                    mContactFragment = new ContactFragment();
                mFragmentManager.beginTransaction().replace(R.id.container_frame, mContactFragment, CONTACT_FRAGMENT).addToBackStack(CONTACT_FRAGMENT).commit();
        }


    }
}


