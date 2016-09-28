package com.example.money.conatusapp.NavigationDrawer;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.money.conatusapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NavigationDrawerFragment extends Fragment {
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private final static String SHARED_PREFERENCE_FILE_NAME = "datafile";
    private final static String DRAWER_COUNT = "count";
    private boolean mFirstTimeDrawerOpened;
    private boolean mFromSaveInstance;
    private View mDrawerContainerView;
    @BindView(R.id.drawer_recycler_view)
    RecyclerView drawerRecyclerView;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirstTimeDrawerOpened = Boolean.valueOf(getDataFromSharedPreferences(getActivity(), DRAWER_COUNT, "false"));
        if (savedInstanceState == null) {
            mFromSaveInstance = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_naviagtion_drawerfragment, container, false);
        drawerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        drawerRecyclerView.setAdapter(new DrawerRecyclerViewAdapter(getActivity(), getList()));
        return layout;
    }

    public List<DrawerMenu> getList() {
        List<DrawerMenu> list = new ArrayList<>();
        String title[] = getResources().getStringArray(R.array.drawer_menu);
        int imageid[] = {R.drawable.timeline, R.drawable.magzine, R.drawable.events, R.drawable.gallery, R.drawable.team, R.drawable.alumni, R.drawable.query, R.drawable.about, R.drawable.contact};
        for (int i = 0; i < title.length; i++) {
            DrawerMenu menu = new DrawerMenu(title[i], imageid[i]);
            list.add(menu);
        }
        return list;
    }


    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        mDrawerContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!mFirstTimeDrawerOpened) {
                    mFirstTimeDrawerOpened = true;
                    saveDataToSharedPreferences(getActivity(), DRAWER_COUNT, String.valueOf(mFirstTimeDrawerOpened));
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();

            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                if (slideOffset < 0.6) {
                    toolbar.setAlpha(1 - slideOffset);
                }
            }
        };
        if (!mFirstTimeDrawerOpened && !mFromSaveInstance) {
            mDrawerLayout.openDrawer(mDrawerContainerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveDataToSharedPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();

    }

    public static String getDataFromSharedPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }
}
