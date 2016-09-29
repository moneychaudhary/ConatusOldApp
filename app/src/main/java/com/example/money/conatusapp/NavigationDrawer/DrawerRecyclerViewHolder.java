package com.example.money.conatusapp.NavigationDrawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.money.conatusapp.R;

/**
 * Created by #money on 9/28/2016.
 */
public class DrawerRecyclerViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;
    public TextView titleView;
    public ImageView iconView;

    public DrawerRecyclerViewHolder(View itemView, Context context) {
        super(itemView);
        this.mContext = context;
        titleView = (TextView) itemView.findViewById(R.id.menu_title);
        iconView = (ImageView) itemView.findViewById(R.id.menu_icon_image);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}
