package com.example.manasaa.layout4;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mAsyncButton,mPicasaButton,mGlideButton;
    private ImageView mImageView;
    private ProgressDialog mProgressDialog;
    private static final String IMAGE_URL="http://www.dailybackgrounds.com/wp-content/uploads/2015/07/lovely-kit-kat-chocolate-trio-pack-1024x636.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAsyncButton=(Button)findViewById(R.id.button1);
        mPicasaButton=(Button)findViewById(R.id.button2);
        mGlideButton=(Button)findViewById(R.id.button3);
        mImageView=(ImageView)findViewById(R.id.image_section);

        mAsyncButton.setOnClickListener(this);
        mPicasaButton.setOnClickListener(this);
        mGlideButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch (id){
            case R.id.button1:
                                new ImageDownloader().execute(IMAGE_URL);
                                break;
            case R.id.button2:
                                mImageView.setImageResource(android.R.color.transparent);
                                Picasso.with(this)
                                        .load(IMAGE_URL)
                                        .into(mImageView);
                                break;
            case R.id.button3:
                                mImageView.setImageResource(android.R.color.transparent);
                                Glide.with(this)
                                        .load(IMAGE_URL)
                                        .into(mImageView);
                                break;
        }
    }

    //Image downloading using AsyncTask
    private class ImageDownloader extends AsyncTask<String, Integer, Bitmap>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Downloading Image");//Set progressdialog title
            mProgressDialog.setMessage("Loading...");// Set progressdialog message
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();// Show progressdialog

        }
        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                InputStream input = new java.net.URL(imageURL).openStream();// Download Image from URL
                bitmap = BitmapFactory.decodeStream(input);                // Decode Bitmap
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mImageView.setImageBitmap(bitmap);
            mProgressDialog.dismiss();
        }
    }

}
