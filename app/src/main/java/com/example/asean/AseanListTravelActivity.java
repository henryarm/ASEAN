package com.example.asean;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
//import com.example.asean.adapter.AseanAdapter;
import com.bumptech.glide.Glide;
import com.example.asean.adapter.AseanItemAdapter;
import com.example.asean.adapter.AseanItemViewHolder;
import com.example.asean.adapter.AseanViewHolder;
import com.example.asean.model.Asean;
import com.example.asean.model.AseanItem;
import com.example.asean.model.KeyAsean;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
//import com.example.asean.realm.RealmController;
//import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class AseanListTravelActivity extends AppCompatActivity {

    private static final String TAG = "AseanListTravelActivity";
    private static final int REQUEST_CODE_ADD_ITEM = 99;
    private RecyclerView recyclerView;
    private Asean asean;
    private AseanItemAdapter adapter;
    private ArrayList<AseanItem> travels;
    private int id = -1;
    private Toast toast;
    private RecyclerView recycler;
    private FirebaseRecyclerAdapter<AseanItem, AseanItemViewHolder> mFirebaseAdapter;
    private ArrayList<String> keys = new ArrayList<>();
    private DatabaseReference mRef;
    private FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asean_list_travel);

        asean = ((Asean) Parcels.unwrap(getIntent().getParcelableExtra(KeyAsean.DETAIL)));
        id = asean.getId();

        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mRef = mRootRef.child("asean").child(String.valueOf(id)).child("travel");

        storage = FirebaseStorage.getInstance();


        recycler = (RecyclerView) findViewById(R.id.rc_travel);

        adapter = new AseanItemAdapter(getApplicationContext(), R.layout.custom_item_asean_detail, travels);
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                keys.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                storage.getReference().child(dataSnapshot.getKey()+".png").delete();
                keys.remove(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        FirebaseRecyclerOptions<AseanItem> options = new FirebaseRecyclerOptions.Builder<AseanItem>()
                .setQuery(mRef, AseanItem.class)
                .build();
        final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        mFirebaseAdapter = new FirebaseRecyclerAdapter<AseanItem, AseanItemViewHolder>(options) {
            @Override
            protected void onBindViewHolder(AseanItemViewHolder viewHolder, int position, final AseanItem aseanItem) {
                viewHolder.textViewTitle.setText(aseanItem.getItem_name());
                viewHolder.textViewDescription.setText(aseanItem.getItem_detail());
                StorageReference storageRef = storage.getReference();
                final StorageReference image = storageRef.child(aseanItem.getItem_image()+".png");
                Glide.with(viewHolder.itemView.getContext())
                        .using(new FirebaseImageLoader())
                        .load(image)
                        .into(viewHolder.imageViewImage);

                viewHolder.setOnClickListener(new AseanViewHolder.ClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        alertDialog(position);
                    }
                });

            }

            @Override
            public AseanItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new AseanItemViewHolder(inflater.inflate(R.layout.custom_item_asean_detail, viewGroup, false));
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



    protected void alertDialog(int position){
//        final AseanItem travel = travels.get(position);
        final String key = keys.get(position);
        new MaterialDialog.Builder(AseanListTravelActivity.this)
                .items(R.array.action)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        switch (position){
                            case 0:
                                showToast("EDIT");
                                break;
                            case 1:
                                showToast("DELETE SUCCESS");
                                mRef.child(key).removeValue();
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
                startActivityForResult(
                        new Intent(this,AddItemActivity.class)
                        .putExtra(KeyAsean.DETAIL,Parcels.wrap(asean))
                        ,REQUEST_CODE_ADD_ITEM);
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
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.e(TAG, "ERROR  onActivityResult");

            }
        }
    }






}
