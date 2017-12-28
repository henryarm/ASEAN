package com.example.asean;

import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.asean.adapter.AseanAdapter;
import com.example.asean.adapter.AseanItemAdapter;
import com.example.asean.adapter.RealmAseanAdapter;
import com.example.asean.app.Prefs;
import com.example.asean.model.Asean;
import com.example.asean.model.AseanItem;
import com.example.asean.model.AseanItemMoney;
import com.example.asean.model.KeyAsean;
import com.example.asean.realm.RealmController;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SearchView searchView;
    private AseanAdapter adapter;
    private Realm realm;
    private RecyclerView recycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.realm = RealmController.with(this).getRealm();
        recycler = (RecyclerView) findViewById(R.id.RecyclerAsean);
        //        searchView = (SearchView) findViewById(R.id.simpleSearchView);

        if (!Prefs.with(this).getPreLoad()) {
            setRealmData();
        }

        setupRecycler();
        RealmController.with(this).refresh();
        setRealmAdapter(RealmController.with(this).getAseans());
    }

    private void setupRecycler() {
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recycler.setHasFixedSize(true);

        // use a linear layout manager since the cards are vertically scrollable
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);

        // create an empty adapter and add it to the recycler view
        adapter = new AseanAdapter(this);
        adapter.setOnclickItem(new AseanAdapter.OnClickAseanListener() {
            @Override
            public void onClick(Asean asean) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class)
                        .putExtra(KeyAsean.ASIAN, Parcels.wrap(asean)));
            }
        });
        recycler.setAdapter(adapter);

    }

    public void setRealmAdapter(RealmResults<Asean> books) {

        //        RealmAseanAdapter realmAdapter = new RealmAseanAdapter(this.getApplicationContext(), books, true);
        RealmAseanAdapter realmAdapter = new RealmAseanAdapter(realm.where(Asean.class).findAll());
        // Set the data and tell the RecyclerView to draw
        adapter.setRealmAdapter(realmAdapter);
        adapter.notifyDataSetChanged();
    }

    private void setRealmData() {
        ArrayList<Asean> aseanList = new ArrayList<>();

        try {
            JSONObject jsonAssets = new JSONObject(loadJSONFromAsset("asean.json"));
            JSONArray aseans = jsonAssets.getJSONArray("asean");

            for (int i = 0; i < aseans.length(); i++) {

                JSONObject object = aseans.getJSONObject(i);

                Asean asean = new Asean();

                asean.setId(Integer.parseInt(object.getString("id")));
                asean.setName(object.getString("name"));
                asean.setFlag_image(object.getString("flag_image"));

                asean.setCity(object.getString("city"));
                asean.setLanguage(object.getString("language"));
                asean.setPopulation(object.getString("population"));
                asean.setReligion(object.getString("religion"));
                asean.setRegime(object.getString("regime"));
                asean.setFlower_detail(object.getString("flower_detail"));
                asean.setNational_dress_detail(object.getString("national_dress_detail"));

                asean.setFlag_image(object.getString("flag_image"));
                asean.setFlower_image(object.getString("flower_image"));
                asean.setNational_dress_image(object.getString("national_dress_image"));

                try {
                    JSONObject jsonAssetsFood = new JSONObject(loadJSONFromAsset("food.json"));
                    JSONArray foods = jsonAssetsFood.getJSONArray("food");
                    RealmList<AseanItem> foodsItems = new RealmList<AseanItem>();
                    for (int j = 0; j < foods.length(); j++) {

                        JSONObject foodObject = foods.getJSONObject(j);

                        if (foodObject.getString("id").equals((object.getString("id")))) {
                          Log.d(TAG, "aaaaaaa");

                            AseanItem aseanItem = new AseanItem();


                            //JSONObject c = aseans.getJSONObject(i);

                            aseanItem.setItem_image(foodObject.getString("food_image"));
                            aseanItem.setItem_name(foodObject.getString("food_name"));
                            aseanItem.setItem_detail(foodObject.getString("food_detail"));

                            foodsItems.add(aseanItem);
                        }

                    }
                        asean.setFood(foodsItems);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonAssetsTravel = new JSONObject(loadJSONFromAsset("travel.json"));
                    JSONArray travels = jsonAssetsTravel.getJSONArray("travel");
                    RealmList<AseanItem> travelItems = new RealmList<AseanItem>();

                    for (int k = 0; k < travels.length(); k++) {

                        JSONObject travelObject = travels.getJSONObject(k);

                        if (travelObject.getString("id").equals((object.getString("id")))) {
                            AseanItem aseanItem = new AseanItem();

                            //JSONObject c = aseans.getJSONObject(i);

                            aseanItem.setItem_image(travelObject.getString("travel_image"));
                            aseanItem.setItem_name(travelObject.getString("travel_name"));
                            aseanItem.setItem_detail(travelObject.getString("travel_detail"));

                            travelItems.add(aseanItem);
                        }

                    }
                    asean.setTravel(travelItems);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject jsonAssetsMoney = new JSONObject(loadJSONFromAsset("money.json"));
                    JSONArray moneys = jsonAssetsMoney.getJSONArray("money");

                    for (int z= 0; z < moneys.length(); z++) {

                        JSONObject moneyObject = moneys.getJSONObject(z);

                        if (moneyObject.getString("id").equals((object.getString("id")))) {
                            AseanItemMoney aseanItemMoney = new AseanItemMoney();
                            aseanItemMoney.setItem_money_a(moneyObject.getString("money_image_a"));
                            aseanItemMoney.setItem_money_b(moneyObject.getString("money_image_b"));
                            aseanItemMoney.setItem_money_name(moneyObject.getString("money_detail_1")
                                    +"\n"+moneyObject.getString("money_detail_2")
                                    +"\n"+moneyObject.getString("money_detail_3")
                                    +"\n"+moneyObject.getString("money_detail_4")
                                    +"\n"+moneyObject.getString("money_detail_5")

                            );
                            asean.setMoney(aseanItemMoney);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                aseanList.add(asean);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



        for (Asean asean : aseanList) {
            // Persist your data easily
            realm.beginTransaction();
            realm.copyToRealm(asean);
            realm.commitTransaction();
        }
        Prefs.with(this).setPreLoad(true);
    }


    /* Connec Json */
    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }




}
