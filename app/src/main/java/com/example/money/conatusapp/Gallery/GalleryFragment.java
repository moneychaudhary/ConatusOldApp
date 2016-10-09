package com.example.money.conatusapp.Gallery;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devspark.progressfragment.ProgressFragment;
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
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalleryFragment extends ProgressFragment {

    private RecyclerView mImageList;
    private DatabaseReference mDatabase;
    private static Context sContext;
    private GalleryDatabase galleryDatabase;
    private GridLayoutManager mGridLayoutManager;
    private static List<OneImage> oneImageList = new ArrayList<>();
    private GalleryAdapter mGalleryAdapter;
    private int count = 0;
    private View view;


    public GalleryFragment() {
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
        mGridLayoutManager = (GridLayoutManager) new GridLayoutManager(getContext(), 2);
        galleryDatabase = new GalleryDatabase(getActivity());
        GalleryDatabaseFetchData galleryDatabaseFetchData = new GalleryDatabaseFetchData();
        galleryDatabaseFetchData.execute();
        final GalleryDatabaseInsertData galleryDatabaseInsertData = new GalleryDatabaseInsertData();
        sContext = getActivity();
        mGalleryAdapter = new GalleryAdapter();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("gallery");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                oneImageList.removeAll(oneImageList);
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    OneImage oneImage = ds.getValue(OneImage.class);
                    oneImageList.add(oneImage);

                }
                setContentShown(true);
                Collections.reverse(oneImageList);
                galleryDatabaseInsertData.execute(oneImageList);
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
        view = inflater.inflate(R.layout.fragment_gallery, container, false);
        mImageList = (RecyclerView) view.findViewById(R.id.gallery_recycler_view);


        if (count == 0) {
            mImageList.setLayoutManager(mGridLayoutManager);
            mImageList.setAdapter(mGalleryAdapter);
            count++;
        }
        return super.onCreateView(inflater, container, savedInstanceState);

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

            Picasso.with(sContext).load(viewHolder.imageUrl).networkPolicy(NetworkPolicy.OFFLINE).resize(360, 360).centerCrop().noFade().into(viewHolder.image, new Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError() {
                    Picasso.with(sContext).load(viewHolder.imageUrl).resize(360, 360).centerCrop().noFade().into(viewHolder.image);

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

    private class GalleryDatabaseInsertData extends AsyncTask<List<OneImage>, Void, Void> {

        @Override
        protected Void doInBackground(List<OneImage>... params) {
            galleryDatabase.insertData(params[0]);
            return null;
        }
    }

    private class GalleryDatabaseFetchData extends AsyncTask<Void, Void, List<OneImage>> {

        @Override
        protected List<OneImage> doInBackground(Void... params) {
            return galleryDatabase.getData();
        }

        @Override
        protected void onPostExecute(List<OneImage> oneImages) {
            oneImageList = oneImages;
            if (!oneImages.isEmpty()) {
                setContentShown(true);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
