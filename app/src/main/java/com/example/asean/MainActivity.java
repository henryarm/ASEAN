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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.example.asean.adapter.AseanAdapter;
import com.example.asean.adapter.AseanItemAdapter;
//import com.example.asean.adapter.RealmAseanAdapter;
import com.example.asean.adapter.AseanViewHolder;
import com.example.asean.app.Prefs;
import com.example.asean.model.Asean;
import com.example.asean.model.AseanItem;
import com.example.asean.model.AseanItemMoney;
import com.example.asean.model.KeyAsean;
//import com.example.asean.realm.RealmController;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
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
import java.util.HashMap;

//import io.realm.Realm;
//import io.realm.RealmList;
//import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private SearchView searchView;
    //    private AseanAdapter adapter;
//    private Realm realm;
    private RecyclerView recycler;
    private FirebaseRecyclerAdapter<Asean, AseanViewHolder> mFirebaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        this.realm = RealmController.with(this).getRealm();
        recycler = (RecyclerView) findViewById(R.id.RecyclerAsean);
        //        searchView = (SearchView) findViewById(R.id.simpleSearchView);

        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mRef = mRootRef.child("asean");
        final FirebaseStorage storage = FirebaseStorage.getInstance();

//        mRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
////                String value = dataSnapshot.getValue(String.class);
////                Log.d(TAG,"NAME"+ dataSnapshot.getChildren().toString());
//                for (DataSnapshot data:dataSnapshot.getChildren()) {
//                    AseanTest name = data.getValue(AseanTest.class);
//                    Log.d(TAG, name.getName());
//                    for (DataSnapshot foods: data.child("food").getChildren()){
//                        AseanItemTest food = foods.getValue(AseanItemTest.class);
//                    }
//                }
////                mTextView.setText(value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
////                mTextView.setText("Failed: " + databaseError.getMessage());
//            }
//        });

        FirebaseRecyclerOptions<Asean> options = new FirebaseRecyclerOptions.Builder<Asean>()
                .setQuery(mRef, Asean.class)
                .build();
        final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
//        mLinearLayoutManager.setStackFromEnd(true);
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Asean, AseanViewHolder>(options) {
            @Override
            protected void onBindViewHolder(AseanViewHolder viewHolder, int position, final Asean asean) {
                viewHolder.textViewName.setText(asean.getName());
                StorageReference storageRef = storage.getReference();
                final StorageReference image = storageRef.child(asean.getFlag_image()+".png");
                Glide.with(viewHolder.itemView.getContext())
                        .using(new FirebaseImageLoader())
                        .load(image)
                        .into(viewHolder.imageViewFlag);
                viewHolder.setOnClickListener(new AseanViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        startActivity(new Intent(MainActivity.this, MenuActivity.class)
                                .putExtra(KeyAsean.ASEAN, Parcels.wrap(asean)));
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {

                    }
                });

            }

            @Override
            public AseanViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new AseanViewHolder(inflater.inflate(R.layout.custom_item_asean, viewGroup, false));
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                // If the recycler view is initially being loaded or the user is at the bottom of the list, scroll
                // to the bottom of the list to show the newly added message.
                if (lastVisiblePosition == -1 || (positionStart >= (friendlyMessageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    recycler.scrollToPosition(positionStart);
                }
            }
        });
        recycler.setLayoutManager(mLinearLayoutManager);
        recycler.setAdapter(mFirebaseAdapter);


    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mFirebaseAdapter != null) {
            mFirebaseAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mFirebaseAdapter != null) {
            mFirebaseAdapter.stopListening();
        }
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
