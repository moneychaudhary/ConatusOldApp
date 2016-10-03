package com.example.money.conatusapp.Gallery;


import android.content.Context;
import android.content.Intent;
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

import com.example.money.conatusapp.ImageDownloadActivty;
import com.example.money.conatusapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
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
public class GalleryFragment extends Fragment {

    private RecyclerView mImageList;
    private DatabaseReference mDatabase;
    private StorageReference mStorageReference;
    private LinearLayoutManager mLinearLayoutManager;
    private static Context sContext;
    private int lastPosition = -1;


    public GalleryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mImageList = (RecyclerView) view.findViewById(R.id.gallery_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(sContext);
        mLinearLayoutManager.setStackFromEnd(true);
        mLinearLayoutManager.setReverseLayout(true);
        mImageList.setLayoutManager(mLinearLayoutManager);
        sContext = getActivity();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("gallery");
        mStorageReference = FirebaseStorage.getInstance().getReference();
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
                mStorageReference.child(model.getImage()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(getActivity()).load(uri).resize(350, 250).into(viewHolder.image);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });

                viewHolder.imageUrl = model.getImage();

            }
        };
        mImageList.setAdapter(firebaseRecyclerAdapter);
    }


    public static class GalleryViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private String imageUrl;

        public GalleryViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.gallery_image_view);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = ImageDownloadActivty.getImageDownloadIntent(sContext, imageUrl);
                    sContext.startActivity(intent);

                }
            });
        }
    }
}
