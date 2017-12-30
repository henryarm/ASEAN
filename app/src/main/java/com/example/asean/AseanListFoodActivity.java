package com.example.asean;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.asean.adapter.AseanItemAdapter;
import com.example.asean.model.Asean;
import com.example.asean.model.AseanItem;
import com.example.asean.model.KeyAsean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AseanListFoodActivity extends AppCompatActivity {

    private static final String TAG = "AseanListFoodActivity";
    private static final int REQUEST_CODE_ADD_ITEM = 99;
    private ListView listViewItem;
    private Asean asean;
    private AseanItemAdapter adapter;
    private ArrayList<AseanItem> foods;
    private int id = -1;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asean_list_food);

//        foods = Parcels.unwrap(getIntent().getParcelableExtra(KeyAsean.FOOD));
//        asean = Parcels.unwrap(getIntent().getParcelableExtra(KeyAsean.DETAIL));
        id = ((Asean) Parcels.unwrap(getIntent().getParcelableExtra(KeyAsean.DETAIL))).getId();

        foods = asean.getFood();
        listViewItem = (ListView) findViewById(R.id.list_view_food);

        adapter = new AseanItemAdapter(getApplicationContext(), R.layout.custom_item_asean_detail, foods);
        listViewItem.setAdapter(adapter);



        listViewItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(AseanListFoodActivity.this, "on long click",Toast.LENGTH_SHORT).show();
                alertDialog(position);
                return false;
            }
        });

    }

    protected void alertDialog(int position){
        final AseanItem food = foods.get(position);
        new MaterialDialog.Builder(AseanListFoodActivity.this)
                .items(R.array.action)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        switch (position){
                            case 0:
                                showToast("EDIT");
                                break;
                            case 1:
//                                RealmController.getInstance().removeAseanFood(id,food,adapter);
                                showToast("DELETE SUCCESS");

                                break;
                            default:
                                break;
                        }
                    }
                })
                .show();
    }

    private void showToast(String message) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        asean = RealmController.getInstance().getAseans(id);
        foods = asean.getFood();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                startActivityForResult(new Intent(this,AddItemActivity.class),REQUEST_CODE_ADD_ITEM);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_ITEM) {
            if(resultCode == Activity.RESULT_OK){
                AseanItem aseanItem = Parcels.unwrap(data.getParcelableExtra(KeyAsean.ASEANITEM));

//                RealmController.getInstance().addAseanFood(asean.getId(),aseanItem,adapter);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.e(TAG, "ERROR  onActivityResult");

            }
        }
    }



}
