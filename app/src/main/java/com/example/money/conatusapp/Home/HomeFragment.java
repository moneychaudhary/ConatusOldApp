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

import com.devspark.progressfragment.ProgressFragment;
import com.example.money.conatusapp.Database.HomeDatabase;
import com.example.money.conatusapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ramotion.foldingcell.FoldingCell;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends ProgressFragment {
    private RecyclerView mReyclerVIew;
    private DatabaseReference mDatabase;
    private HomeDatabase homeDatabase;
    private LinearLayoutManager mLinearLayoutManager;
    private static Context sContext;
    private static List<Post> postList = new ArrayList<>();
    private HomeAdapter mMemberAdapter;
    private int count = 0;
    private static HashSet<Integer> unfoldedIndexes = new HashSet<>();
    private View view;



    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(view);
        setContentShown(false);
        if (!postList.isEmpty()) {
            setContentShown(true);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
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
                setContentShown(true);
                homeDatabase.insertData(postList);
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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mReyclerVIew = (RecyclerView) view.findViewById(R.id.home_list);


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
            if (postList.get(position).getDesc().equals("null")) {
                viewHolder.mFoldedCell.getChildAt(0).setVisibility(View.VISIBLE);
                viewHolder.mFoldedCell.getChildAt(1).setVisibility(View.GONE);
                viewHolder.subHeading.setText(postList.get(position).getSubhead());
            } else {
                viewHolder.contentView.setText(postList.get(position).getDesc());
                viewHolder.folded_heading.setText(postList.get(position).getTitle());
                viewHolder.folded_date.setText(postList.get(position).getDate());
                viewHolder.folded_time.setText(postList.get(position).getTime());
                viewHolder.folded_subHeading.setText(postList.get(position).getSubhead());
                Picasso.with(sContext).load(Uri.parse(postList.get(position).getImage())).networkPolicy(NetworkPolicy.OFFLINE).noFade().into(viewHolder.folded_image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(sContext).load(Uri.parse(postList.get(position).getImage())).noFade().into(viewHolder.folded_image);
                    }
                });
            }
            viewHolder.heading.setText(postList.get(position).getTitle());
            viewHolder.date.setText(postList.get(position).getDate());
            viewHolder.time.setText(postList.get(position).getTime());
            if (unfoldedIndexes.contains(position)) {
                viewHolder.mFoldedCell.unfold(true);
            } else {
                viewHolder.mFoldedCell.fold(true);
            }
            Picasso.with(sContext).load(Uri.parse(postList.get(position).getImage())).networkPolicy(NetworkPolicy.OFFLINE).noFade().into(viewHolder.image, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(sContext).load(Uri.parse(postList.get(position).getImage())).noFade().into(viewHolder.image);
                }
            });


            viewHolder.mFoldedCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!postList.get(position).getDesc().equals("null")) {
                        viewHolder.mFoldedCell.toggle(false);
                        registerToggle(position);
                    }
                }
            });



        }

        public void registerToggle(int position) {
            if (unfoldedIndexes.contains(position))
                registerFold(position);
            else
                registerUnfold(position);
        }

        public void registerFold(int position) {
            unfoldedIndexes.remove(position);
        }

        public void registerUnfold(int position) {
            unfoldedIndexes.add(position);
        }
        @Override
        public int getItemCount() {
            return postList.size();
        }
    }


    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        private TextView heading;
        private TextView folded_heading;
        private TextView date;
        private TextView folded_date;
        private TextView time;
        private TextView folded_time;
        private TextView subHeading;
        private TextView folded_subHeading;
        private ImageView image;
        private ImageView folded_image;
        private TextView contentView;
        private FoldingCell mFoldedCell;


        public HomeViewHolder(View itemView) {
            super(itemView);
            heading = (TextView) itemView.findViewById(R.id.heading);
            folded_heading = (TextView) itemView.findViewById(R.id.folded_heading);
            date = (TextView) itemView.findViewById(R.id.date);
            folded_date = (TextView) itemView.findViewById(R.id.folded_date);
            time = (TextView) itemView.findViewById(R.id.time);
            folded_time = (TextView) itemView.findViewById(R.id.folded_time);
            subHeading = (TextView) itemView.findViewById(R.id.subheading);
            folded_subHeading = (TextView) itemView.findViewById(R.id.folded_subheading);
            image = (ImageView) itemView.findViewById(R.id.article_image);
            folded_image = (ImageView) itemView.findViewById(R.id.folded_image);
            mFoldedCell = (FoldingCell) itemView.findViewById(R.id.folding_cell);
            contentView = (TextView) itemView.findViewById(R.id.articel_content_field);

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

