package com.example.money.conatusapp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ImageDownloadActivty extends AppCompatActivity {
    private static final String IMAGE_URL = "url";
    private ImageView mImageView;
    private String mUrl;
    private FloatingActionButton mFab;
    private static Context sContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_download_activty);
        mImageView = (ImageView) findViewById(R.id.showimage_view);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mUrl));
                request.setTitle("Image Download");
                request.setDescription("Image is being downloaded.....");
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                String name = mUrl.substring(mUrl.length() - 12, mUrl.length()) + ".jpeg";
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name);
                DownloadManager manager = (DownloadManager) sContext.getSystemService(Context.DOWNLOAD_SERVICE);
                manager.enqueue(request);
            }
        });
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(IMAGE_URL);
        ;
        Picasso.with(sContext).load(mUrl).networkPolicy(NetworkPolicy.OFFLINE).into(mImageView, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
                Picasso.with(sContext).load(mUrl).into(mImageView);

            }
        });


    }


    public static Intent getImageDownloadIntent(Context context, String imageurl) {
        sContext = context;
        Intent intent = new Intent(context, ImageDownloadActivty.class);
        intent.putExtra(IMAGE_URL, imageurl);
        return intent;
    }
}
