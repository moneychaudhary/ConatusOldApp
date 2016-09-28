package com.example.money.conatusapp.NavigationDrawer;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.money.conatusapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by #money on 9/28/2016.
 */
public class DrawerRecyclerViewAdapter extends RecyclerView.Adapter<DrawerRecyclerViewHolder> {

    private FragmentActivity mFragmentActivity;
    List<DrawerMenu> mMenuList= Collections.EMPTY_LIST;
    public DrawerRecyclerViewAdapter(FragmentActivity activity,  List<DrawerMenu> list) {
        mFragmentActivity=activity;
        this.mMenuList=list;

    }

    @Override
    public DrawerRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    }

    @Override
    public void onBindViewHolder(DrawerRecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mMenuList.size();
    }
}
