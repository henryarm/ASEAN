package com.example.asean;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.asean.model.Asean;
import com.example.asean.model.AseanItemMoney;
import com.example.asean.model.KeyAsean;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
//import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class AseanListMoneyActivity extends AppCompatActivity {

    private ImageView imageViewBankA, imageViewBankB;
    private TextView textView_name, textView_detail, textView_exchange_rate;

    private String TAG = "AseanListMoneyActivity";

    private AseanItemMoney aseanItemMoney;
    private Asean asean;

    private DatabaseReference mRef;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asean_list_money);

        asean = ((Asean) Parcels.unwrap(getIntent().getParcelableExtra(KeyAsean.DETAIL)));
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mRef = mRootRef.child("asean").child(String.valueOf(asean.getId()));

        storage = FirebaseStorage.getInstance();

        aseanItemMoney = asean.getMoney();
        initView();

    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        imageViewBankA = (ImageView) findViewById(R.id.image_view_money_a);
        imageViewBankB = (ImageView) findViewById(R.id.image_view_money_b);
        textView_name = (TextView) findViewById(R.id.text_view_name);
        textView_detail = (TextView) findViewById(R.id.text_view_detail);
        textView_exchange_rate =  (TextView) findViewById(R.id.text_view_exchange_rate);

        textView_name.setTypeface(null, Typeface.BOLD);
        textView_name.setPaintFlags( textView_name.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        textView_name.setText(aseanItemMoney.getMoney_detail_1());
        textView_detail.setText(aseanItemMoney.getDetail());

        textView_exchange_rate.setTypeface(null, Typeface.BOLD);
        textView_exchange_rate.setText(""+aseanItemMoney.getMoney_detail_2()+"*");

        StorageReference storageRef = storage.getReference();
        final StorageReference imageA = storageRef.child(aseanItemMoney.getMoney_image_a()+".jpg");
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(imageA)
                .into(imageViewBankA);

        final StorageReference imageB = storageRef.child(aseanItemMoney.getMoney_image_b()+".jpg");
        Glide.with(this)
                .using(new FirebaseImageLoader())
                .load(imageB)
                .into(imageViewBankB);

//        textView_detail.setText(object.getString("money_detail_2"));
//        textView3.setText(object.getString("money_detail_3"));
//        textView4.setText(object.getString("money_detail_4"));
//        textView5.setText(object.getString("money_detail_5"));
//
//        String strMoneyA = aseanItemMoney.getItem_money_a();
//        String strMoneyB = aseanItemMoney.getItem_money_b();
//
//        int imageFlag = getResources().getIdentifier(strMoneyA, "drawable", getPackageName());
//        int imageFlower = getResources().getIdentifier(strMoneyB, "drawable", getPackageName());
//
//        try {
////            Picasso.with(AseanListMoneyActivity.this).load(imageFlag).into(imageViewBankA);
////            Picasso.with(AseanListMoneyActivity.this).load(imageFlower).into(imageViewBankB);
//        } catch (Error err) {
//            imageViewBankA.setImageResource(R.mipmap.ic_launcher);
//            imageViewBankB.setImageResource(R.mipmap.ic_launcher);
//        }
    }

}
