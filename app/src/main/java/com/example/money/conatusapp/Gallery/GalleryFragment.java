package com.example.money.conatusapp.Gallery;


import android.content.Context;
import android.content.Intent;
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

import com.example.money.conatusapp.Database.GalleryDatabase;
import com.example.money.conatusapp.ImageDownloadActivty;
import com.example.money.conatusapp.R;
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
public class GalleryFragment extends Fragment {

    private RecyclerView mImageList;
    private DatabaseReference mDatabase;
    private static Context sContext;
    private int lastPosition = -1;
    private GalleryDatabase galleryDatabase;
    private LinearLayoutManager mLinearLayoutManager;
    private static List<OneImage> oneImageList = new ArrayList<>();
    private GalleryAdapter mGalleryAdapter;
    private int count = 0;


    public GalleryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);
        mLinearLayoutManager.setReverseLayout(true);
        galleryDatabase = new GalleryDatabase(getActivity());
        mDatabase = FirebaseDatabase.getInstance().getReference().child("gallery");
        mGalleryAdapter = new GalleryAdapter();
        oneImageList = galleryDatabase.getData();
        sContext = getActivity();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                oneImageList.removeAll(oneImageList);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    OneImage oneImage = ds.getValue(OneImage.class);
                    oneImageList.add(oneImage);

                }
                mGalleryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mImageList = (RecyclerView) view.findViewById(R.id.gallery_recycler_view);


        if (count == 0) {
            mImageList.setLayoutManager(mLinearLayoutManager);
            mImageList.setAdapter(mGalleryAdapter);
            count++;
        }
        return view;

    }


    private static class GalleryAdapter extends android.support.v7.widget.RecyclerView.Adapter<GalleryViewHolder> {
        public GalleryAdapter() {
        }

        @Override
        public GalleryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(sContext);
            View view = inflater.inflate(R.layout.gallery_recycler_view_layout, parent, false);
            return new GalleryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final GalleryViewHolder viewHolder, final int position) {
            viewHolder.imageUrl = oneImageList.get(position).getImage();

            Picasso.with(sContext).load(Uri.parse(viewHolder.imageUrl)).networkPolicy(NetworkPolicy.OFFLINE).into(viewHolder.image, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError() {
                    Picasso.with(sContext).load(Uri.parse(viewHolder.imageUrl)).into(viewHolder.image);

                }
            });


        }

        @Override
        public int getItemCount() {
            return oneImageList.size();
        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        galleryDatabase.insertData(oneImageList);
    }
}
