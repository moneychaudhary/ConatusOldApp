package com.example.money.conatusapp.NavigationDrawer;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
    private List<DrawerMenu> mMenuList;
    public DrawerRecyclerViewAdapter(FragmentActivity activity,  List<DrawerMenu> list) {
        mFragmentActivity=activity;
        this.mMenuList=list;

    }

    @Override
    public DrawerRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflator= LayoutInflater.from(mFragmentActivity);
        View view= inflator.inflate(R.layout.navdrawer_recycler_view_layout,parent,false);
        return new DrawerRecyclerViewHolder(view,mFragmentActivity);

    }

    @Override
    public void onBindViewHolder(DrawerRecyclerViewHolder holder, int position) {
        holder.iconView.setImageResource(mMenuList.get(position).getImageid());
        holder.titleView.setText(mMenuList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mMenuList.size();
    }
}
