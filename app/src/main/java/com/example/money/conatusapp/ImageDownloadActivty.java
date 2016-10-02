package com.example.money.conatusapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageDownloadActivty extends AppCompatActivity {
    private static final String IMAGE_URL = "url";
    private ImageView mImageView;
    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("money", "aa");
        setContentView(R.layout.activity_image_download_activty);
        mImageView = (ImageView) findViewById(R.id.showimage_view);
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(IMAGE_URL);
        Picasso.with(this).load(mUrl).into(mImageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            super.onBackPressed();
        else if (item.getItemId() == R.id.download) {

        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent getImageDownloadIntent(Context context, String imageurl) {
        Intent intent = new Intent(context, ImageDownloadActivty.class);
        intent.putExtra(IMAGE_URL, imageurl);
        return intent;
    }
}
