package com.example.money.conatusapp.Gallery;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.money.conatusapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends Fragment {

    private RecyclerView mImageList;
    private DatabaseReference mDatabase;
    private LinearLayoutManager mLinearLayoutManager;


    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mImageList = (RecyclerView) view.findViewById(R.id.gallery_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mImageList.setLayoutManager(mLinearLayoutManager);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("gallery");
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        final FirebaseRecyclerAdapter<OneImage, GalleryViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<OneImage, GalleryViewHolder>(OneImage.class,
                R.layout.gallery_recycler_view_layout,
                GalleryViewHolder.class,
                mDatabase) {

            protected void populateViewHolder(final GalleryViewHolder viewHolder, OneImage model, int position) {
                Picasso.with(getActivity()).load(model.getImage()).resize(400, 300).into(viewHolder.image);
            }
        };
        mImageList.setAdapter(firebaseRecyclerAdapter);
    }


    public static class GalleryViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.gallery_image_view);
        }
    }
}
