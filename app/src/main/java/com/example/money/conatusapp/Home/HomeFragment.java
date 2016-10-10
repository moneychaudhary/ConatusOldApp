package com.example.money.conatusapp.Home;


import android.content.Context;
import android.os.AsyncTask;
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

import com.devspark.progressfragment.ProgressFragment;
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
public class HomeFragment extends ProgressFragment {
    private RecyclerView mReyclerVIew;
    private DatabaseReference mDatabase;
    private LinearLayoutManager mLinearLayoutManager;
    private static Context sContext;
    private static List<Post> postList = new ArrayList<>();
    private HomeAdapter mMemberAdapter;
    private int count = 0;
    private View view;
    private HomeDatabase mHomeDatabase;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(view);
        setContentShown(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mHomeDatabase = new HomeDatabase(getActivity());
        new HomeDatabaseFetchData().execute();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
        mMemberAdapter = new HomeAdapter();
        sContext = getActivity();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                postList.removeAll(postList);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Post post = ds.getValue(Post.class);
                    postList.add(0, post);

                }
                mMemberAdapter.notifyDataSetChanged();
                new HomeDatabaseInsertData().execute(postList);
                setContentShown(true);

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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mReyclerVIew = (RecyclerView) view.findViewById(R.id.home_list);
        mReyclerVIew.setHasFixedSize(true);
        mReyclerVIew.setItemViewCacheSize(20);
        mReyclerVIew.setDrawingCacheEnabled(true);
        mReyclerVIew.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        if (count == 0) {
            mReyclerVIew.setLayoutManager(mLinearLayoutManager);
            mReyclerVIew.setAdapter(mMemberAdapter);
            count++;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
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
            viewHolder.subHeading.setText(postList.get(position).getSubhead());
            viewHolder.contentView.setText(postList.get(position).getDesc());
            viewHolder.date.setText(postList.get(position).getDate());
            viewHolder.time.setText(postList.get(position).getTime());
            Picasso.with(sContext).load(postList.get(position).getImage()).networkPolicy(NetworkPolicy.OFFLINE).fit().noFade().into(viewHolder.image, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(sContext).load(postList.get(position).getImage()).fit().noFade().into(viewHolder.image);
                }
            });


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (postList.get(position).getDesc().toString().length() > 10)
                        viewHolder.mExpandableLayout.toggle();

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
        private TextView contentView;
        private ExpandableLayout mExpandableLayout;


        public HomeViewHolder(View itemView) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.heading);
            date = (TextView) itemView.findViewById(R.id.date);
            time = (TextView) itemView.findViewById(R.id.time);
            subHeading = (TextView) itemView.findViewById(R.id.subheading);
            image = (ImageView) itemView.findViewById(R.id.article_image);
            mExpandableLayout = (ExpandableLayout) itemView.findViewById(R.id.expandable_content);
            contentView = (TextView) itemView.findViewById(R.id.articel_content_field);
        }

    }

    private class HomeDatabaseInsertData extends AsyncTask<List<Post>, Void, Void> {

        @Override
        protected Void doInBackground(List<Post>... post) {
            mHomeDatabase.insertData(post[0]);
            return null;
        }
    }

    private class HomeDatabaseFetchData extends AsyncTask<Void, Void, List<Post>> {

        @Override
        protected List<Post> doInBackground(Void... params) {
            return mHomeDatabase.getData();
        }

        @Override
        protected void onPostExecute(List<Post> posts) {
            postList = posts;
            if (!postList.isEmpty()) {
                setContentShown(true);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

