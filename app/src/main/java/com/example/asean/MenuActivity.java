package com.example.asean;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.asean.model.Asean;
import com.example.asean.model.KeyAsean;

import org.parceler.Parcels;

public class MenuActivity extends AppCompatActivity {

    private String aseanId;

    private String aseanName;

    private String TAG = "MenuActivity";

    private Asean asean;

    private Button buttonMap, buttonGeneral, buttonTravel, buttonFood, buttonMoney, buttonBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        asean = Parcels.unwrap(getIntent().getParcelableExtra(KeyAsean.ASEAN));
        aseanName = asean.getName();
//        Log.d(TAG, asean.getMoney().getItem_money_a());
//
//        Log.d(TAG, asean.getMoney().getMoney_detail_1());
        initView();
    }

    private void initView() {
        buttonMap = (Button) findViewById(R.id.button_map);
        buttonGeneral = (Button) findViewById(R.id.button_asean_general);
        buttonTravel = (Button) findViewById(R.id.button_asean_travel);
        buttonFood = (Button) findViewById(R.id.button_asean_food);
        buttonMoney = (Button) findViewById(R.id.button_asean_money);
        buttonBackHome = (Button) findViewById(R.id.button_back_home);

        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("geo:0,0?q="+aseanName);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(Intent.createChooser(intent, "View map with"));
            }
        });

        buttonGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, AseanDetailActivity.class)
                        .putExtra(KeyAsean.DETAIL, Parcels.wrap(asean)));
            }
        });

        buttonTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, AseanListItemActivity.class)
                        .putExtra(KeyAsean.DETAIL,Parcels.wrap(asean))
                        .putExtra(KeyAsean.TYPE,KeyAsean.TRAVEL));
            }
        });

        buttonFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, AseanListItemActivity.class)
                        .putExtra(KeyAsean.DETAIL,Parcels.wrap(asean))
                        .putExtra(KeyAsean.TYPE,KeyAsean.FOOD));
            }
        });

        buttonMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, AseanListMoneyActivity.class)
                        .putExtra(KeyAsean.DETAIL,Parcels.wrap(asean)));
            }
        });

        buttonBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
