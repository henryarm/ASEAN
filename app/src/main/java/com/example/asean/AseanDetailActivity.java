package com.example.asean;

import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.asean.adapter.AseanAdapter;
import com.bumptech.glide.Glide;
import com.example.asean.model.Asean;
import com.example.asean.model.AseanItem;
import com.example.asean.model.KeyAsean;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
//import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AseanDetailActivity extends AppCompatActivity {

    private TextView textViewName, textViewCity, textViewLanguage, textViewPopulation, textViewLeregion, textViewRegime, textViewFlowerDetail, textViewDressDetail;
    private ImageView imageViewFlag, imageViewFlower, imageViewDress;

    private String TAG = "AseanDetailActivity";

    int imageFlag;
    int imageFlower;
    int imageDress;

    private Asean asean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asean_detail);

        asean = Parcels.unwrap(getIntent().getParcelableExtra(KeyAsean.DETAIL));
        final FirebaseStorage storage = FirebaseStorage.getInstance();


        initView();

        textViewName.setText(asean.getName());
        textViewCity.setText(asean.getCity());
        textViewLanguage.setText(asean.getLanguage());
        textViewPopulation.setText(asean.getPopulation());
        textViewLeregion.setText(asean.getReligion());
        textViewRegime.setText(asean.getRegime());
        textViewFlowerDetail.setText(asean.getFlower_detail());
        textViewDressDetail.setText(asean.getNational_dress_detail());

        try {
            StorageReference storageRef = storage.getReference();
            final StorageReference image_flag = storageRef.child(asean.getFlag_image()+".png");
            final StorageReference image_flower = storageRef.child(asean.getFlower_image()+".png");
            final StorageReference image_dress = storageRef.child(asean.getNational_dress_image()+".png");

            Glide.with(this)
                    .using(new FirebaseImageLoader())
                    .load(image_flag)
                    .into(imageViewFlag);
            Glide.with(this)
                    .using(new FirebaseImageLoader())
                    .load(image_flower)
                    .into(imageViewFlower);
            Glide.with(this)
                    .using(new FirebaseImageLoader())
                    .load(image_dress)
                    .into(imageViewDress);


        } catch (Error err) {
            imageViewFlag.setImageResource(R.mipmap.ic_launcher);
            imageViewFlower.setImageResource(R.mipmap.ic_launcher);
            imageViewDress.setImageResource(R.mipmap.ic_launcher);
        }

    }

    private void initView() {
        textViewName = (TextView) findViewById(R.id.text_view_name);
        textViewCity = (TextView) findViewById(R.id.text_view_city);
        textViewLanguage = (TextView) findViewById(R.id.text_view_language);
        textViewPopulation = (TextView) findViewById(R.id.text_view_population);
        textViewLeregion = (TextView) findViewById(R.id.text_view_religion);
        textViewRegime = (TextView) findViewById(R.id.text_view_regime);
        textViewFlowerDetail = (TextView) findViewById(R.id.text_view_flower_detail);
        textViewDressDetail = (TextView) findViewById(R.id.text_view_dress_detail);

        imageViewFlag = (ImageView) findViewById(R.id.image_view_flag_detail);
        imageViewFlower = (ImageView) findViewById(R.id.image_view_flower);
        imageViewDress = (ImageView) findViewById(R.id.image_view_dress);

        imageViewFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AseanDetailActivity.this, ShowImageActivity.class).putExtra("image_show", imageFlag));
            }
        });

        imageViewFlower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AseanDetailActivity.this, ShowImageActivity.class).putExtra("image_show", imageFlower));
            }
        });

        imageViewDress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AseanDetailActivity.this, ShowImageActivity.class).putExtra("image_show", imageDress));
            }
        });
    }

}
