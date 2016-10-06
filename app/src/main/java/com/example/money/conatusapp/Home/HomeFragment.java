package com.example.money.conatusapp.Home;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.money.conatusapp.Database.HomeDatabase;
import com.example.money.conatusapp.R;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView mReyclerVIew;
    private DatabaseReference mDatabase;
    private HomeDatabase homeDatabase;
    private LinearLayoutManager mLinearLayoutManager;
    private static Context sContext;
    private static List<Post> postList = new ArrayList<>();
    private HomeAdapter mMemberAdapter;
    private int count = 0;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        homeDatabase = new HomeDatabase(getActivity());
        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
        mMemberAdapter = new HomeAdapter();
        postList = homeDatabase.getData();
        sContext = getActivity();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList.removeAll(postList);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Post post = ds.getValue(Post.class);
                    postList.add(post);

                }
                mMemberAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mReyclerVIew = (RecyclerView) view.findViewById(R.id.home_list);


        if (count == 0) {
            mReyclerVIew.setLayoutManager(mLinearLayoutManager);
            mReyclerVIew.setAdapter(mMemberAdapter);
            count++;
        }
        return view;
    }

    private static class HomeAdapter extends android.support.v7.widget.RecyclerView.Adapter<HomeViewHolder> {
        public HomeAdapter() {
        }

        @Override
        public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(sContext);
            View view = inflater.inflate(R.layout.home_list_recycler_view_layout, parent, false);
            return new HomeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final HomeViewHolder viewHolder, final int position) {
            viewHolder.heading.setText(postList.get(position).getTitle());
            viewHolder.date.setText(postList.get(position).getDate());
            viewHolder.time.setText(postList.get(position).getTime());
            viewHolder.subHeading.setText(postList.get(position).getSubhead());
            viewHolder.contentView.setText(postList.get(position).getDesc());
            Picasso.with(sContext).load(Uri.parse(postList.get(position).getImage())).networkPolicy(NetworkPolicy.OFFLINE).noFade().into(viewHolder.image, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(sContext).load(Uri.parse(postList.get(position).getImage())).noFade().into(viewHolder.image);
                }
            });


        }

        @Override
        public int getItemCount() {
            return postList.size();
        }
    }


    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        private TextView heading;
        private TextView date;
        private TextView time;
        private TextView subHeading;
        private ImageView image;
        private ExpandableLayout expandableLayout;
        private TextView contentView;


        public HomeViewHolder(View itemView) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.heading);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            subHeading = (TextView) itemView.findViewById(R.id.subheading);
            image = (ImageView) itemView.findViewById(R.id.article_image);
            expandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expanable_view);
            contentView = (TextView) itemView.findViewById(R.id.articel_content_field);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    expandableLayout.toggle();
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homeDatabase.insertData(postList);
    }
}

