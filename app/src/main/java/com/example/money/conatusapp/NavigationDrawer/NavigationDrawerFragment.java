package com.example.money.conatusapp.NavigationDrawer;


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

;

public class NavigationDrawerFragment extends Fragment implements NavDrawerClickListner {
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationDrawerActivityListner listner;
    private DrawerLayout mDrawerLayout;
    private View mDrawerContainerView;
    private RecyclerView drawerRecyclerView;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_naviagtion_drawerfragment, container);
        drawerRecyclerView = (RecyclerView) rootView.findViewById(R.id.drawer_recycler_view);
        DrawerRecyclerViewAdapter adapter = new DrawerRecyclerViewAdapter(getActivity(), getList());
        adapter.setNavDrawerClickListner(this);
        drawerRecyclerView.setAdapter(adapter);
        drawerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;

    }

    public List<DrawerMenu> getList() {
        List<DrawerMenu> list = new ArrayList<>();
        String title[] = getResources().getStringArray(R.array.drawer_menu);
        int imageid[] = {R.mipmap.ic_home_black_24dp, R.mipmap.ic_event_black_24dp, R.mipmap.ic_book_black_24dp,
                R.mipmap.ic_wallpaper_black_24dp, R.mipmap.ic_group_black_24dp, R.mipmap.ic_help_outline_black_24dp, R.mipmap.ic_contact_mail_black_24dp};
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
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }



    @Override
    public void onNavMenuClicked(int position) {
        if (listner != null) {
            mDrawerLayout.closeDrawer(mDrawerContainerView);
            listner.onItemClickedInDrawer(position);
        }
    }

    public void setOnNavigationDrawerActivityListner(NavigationDrawerActivityListner listner) {
        this.listner = listner;
    }
}

