package com.example.asean;

import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asean.model.AseanItemMoney;
import com.example.asean.model.KeyAsean;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

public class AseanListMoneyActivity extends AppCompatActivity {

    private ImageView imageViewBankA, imageViewBankB;
    private TextView textView1, textView2, textView3, textView4, textView5;

    private String TAG = "AseanListMoneyActivity";

    private AseanItemMoney aseanItemMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asean_list_money);

        initView();

        aseanItemMoney = Parcels.unwrap(getIntent().getParcelableExtra(KeyAsean.MONEY));
        textView1.setText(aseanItemMoney.getItem_money_name());
//        textView2.setText(object.getString("money_detail_2"));
//        textView3.setText(object.getString("money_detail_3"));
//        textView4.setText(object.getString("money_detail_4"));
//        textView5.setText(object.getString("money_detail_5"));

        String strMoneyA = aseanItemMoney.getItem_money_a();
        String strMoneyB = aseanItemMoney.getItem_money_b();

        int imageFlag = getResources().getIdentifier(strMoneyA, "drawable", getPackageName());
        int imageFlower = getResources().getIdentifier(strMoneyB, "drawable", getPackageName());

        try {
            Picasso.with(AseanListMoneyActivity.this).load(imageFlag).into(imageViewBankA);
            Picasso.with(AseanListMoneyActivity.this).load(imageFlower).into(imageViewBankB);
        } catch (Error err) {
            imageViewBankA.setImageResource(R.mipmap.ic_launcher);
            imageViewBankB.setImageResource(R.mipmap.ic_launcher);
        }
    }

    private void initView() {
        imageViewBankA = (ImageView) findViewById(R.id.image_view_monay_a);
        imageViewBankB = (ImageView) findViewById(R.id.image_view_monay_b);
        textView1 = (TextView) findViewById(R.id.text_view_monay_1);
//        textView2 = (TextView) findViewById(R.id.text_view_monay_2);
//        textView3 = (TextView) findViewById(R.id.text_view_monay_3);
//        textView4 = (TextView) findViewById(R.id.text_view_monay_4);
//        textView5 = (TextView) findViewById(R.id.text_view_monay_5);
    }

}
