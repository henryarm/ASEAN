package com.example.asean;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ShowImageActivity extends AppCompatActivity {

    private TouchImageView imageViewShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        int resID = getIntent().getExtras().getInt("image_show");

        imageViewShow = (TouchImageView) findViewById(R.id.image_view_show);
        imageViewShow.setImageResource(resID);

        //imageViewShow.setImageResource();
    }
}
