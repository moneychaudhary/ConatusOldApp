package com.example.money.conatusapp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ImageDownloadActivty extends AppCompatActivity {
    private static final String IMAGE_URL = "url";
    private ImageView mImageView;
    private String mUrl;
    private FloatingActionButton mFab;
    private static Context sContext;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_download_activty);
        mImageView = (ImageView) findViewById(R.id.showimage_view);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mStorageRef.child(mUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setTitle("Image Download");
                        request.setDescription("Image is being downloaded.....");
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        String name = URLUtil.guessFileName(mUrl, null, MimeTypeMap.getFileExtensionFromUrl(mUrl));
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name);
                        DownloadManager manager = (DownloadManager) sContext.getSystemService(Context.DOWNLOAD_SERVICE);
                        manager.enqueue(request);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });

            }
        });
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(IMAGE_URL);
        mStorageRef.child(mUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(sContext).load(uri).into(mImageView);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
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
