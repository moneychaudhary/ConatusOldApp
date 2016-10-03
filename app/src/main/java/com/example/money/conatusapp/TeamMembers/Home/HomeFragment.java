package com.example.money.conatusapp.TeamMembers.Home;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.money.conatusapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.github.aakira.expandablelayout.ExpandableLayout;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private RecyclerView mReyclerVIew;
    private DatabaseReference mDatabase;
    private StorageReference mStorgae;
    private LinearLayoutManager mLinearLayoutManager;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mReyclerVIew = (RecyclerView) view.findViewById(R.id.home_list);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mReyclerVIew.setLayoutManager(mLinearLayoutManager);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
        mStorgae = FirebaseStorage.getInstance().getReference();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Post, HomeViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post, HomeViewHolder>(
                Post.class,
                R.layout.home_list_recycler_view_layout,
                HomeViewHolder.class,
                mDatabase
        ) {
            @Override
            protected void populateViewHolder(final HomeViewHolder viewHolder, final Post model, int position) {

                if (model.getDesc().equals("null"))
                    viewHolder.expandableLayout.collapse();

                mStorgae.child(model.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(getActivity()).load(uri).resize(300, 200).into(viewHolder.image);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
                viewHolder.heading.setText(model.getTitle());
                viewHolder.date.setText(model.getDate());
                viewHolder.time.setText(model.getTime());
                viewHolder.subHeading.setText(model.getSubhead());
                viewHolder.contentView.setText(model.getDesc());
                viewHolder.imageUrl = model.getImage();

            }
        };


        mReyclerVIew.setAdapter(firebaseRecyclerAdapter);
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        private TextView heading;
        private TextView date;
        private TextView time;
        private TextView subHeading;
        private ImageView image;
        private ExpandableLayout expandableLayout;
        private TextView contentView;
        private String imageUrl;

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

}

