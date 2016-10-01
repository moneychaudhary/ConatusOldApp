package com.example.money.conatusapp.NavigationDrawer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.money.conatusapp.R;

import java.util.List;

/**
 * Created by #money on 9/28/2016.
 */
public class DrawerRecyclerViewAdapter extends RecyclerView.Adapter<DrawerRecyclerViewAdapter.DrawerRecyclerViewHolder> {
    public NavDrawerClickListner listner;

    public Context mCotext;
    private List<DrawerMenu> mMenuList;

    public DrawerRecyclerViewAdapter(Context context, List<DrawerMenu> list) {
        mCotext = context;
        this.mMenuList = list;

    }

    public void setNavDrawerClickListner(NavDrawerClickListner listner) {
        this.listner = listner;
    }

    @Override
    public DrawerRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflator = LayoutInflater.from(mCotext);
        View view = inflator.inflate(R.layout.navdrawer_recycler_view_layout, parent, false);
        return new DrawerRecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(DrawerRecyclerViewHolder holder, int position) {
        holder.iconView.setImageResource(mMenuList.get(position).getImageid());
        holder.titleView.setText(mMenuList.get(position).getTitle());
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return mMenuList.size();
    }


    public class DrawerRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView titleView;
        public ImageView iconView;
        public int position;

        public DrawerRecyclerViewHolder(View itemView) {
            super(itemView);
            titleView = (TextView) itemView.findViewById(R.id.menu_title);
            iconView = (ImageView) itemView.findViewById(R.id.menu_icon_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listner != null)
                        listner.onNavMenuClicked(position);

                }
            });
        }
    }

}
