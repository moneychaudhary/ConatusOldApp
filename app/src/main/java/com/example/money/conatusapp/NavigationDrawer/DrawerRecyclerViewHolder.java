package com.example.money.conatusapp.NavigationDrawer;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.money.conatusapp.R;

import butterknife.BindView;

/**
 * Created by #money on 9/28/2016.
 */
public class DrawerRecyclerViewHolder extends RecyclerView.ViewHolder{
    private FragmentActivity mFragmentActivity;
    @BindView(R.id.menu_title)
    TextView titleView;
    @BindView(R.id.menu_icon_image)
    public ImageView iconView;

    public DrawerRecyclerViewHolder(View itemView ,FragmentActivity activity) {
        super(itemView);
        this.mFragmentActivity=activity;
    }
}
