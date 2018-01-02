package com.example.asean;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Parcel;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.asean.model.Asean;
import com.example.asean.model.AseanItem;
import com.example.asean.model.KeyAsean;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.firebasedevday.library.BaseActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import siclo.com.ezphotopicker.api.EZPhotoPick;
import siclo.com.ezphotopicker.api.EZPhotoPickStorage;
import siclo.com.ezphotopicker.api.models.EZPhotoPickConfig;
import siclo.com.ezphotopicker.api.models.PhotoSource;

public class AddItemActivity extends BaseActivity {

    private static final String TAG = "AddItemActivity";
    private static final int REQUEST_CODE_CHOOSE_PICTURE_FROM_GALLARY = 99;
    private EditText edName, edDetail;
    private AseanItem aseanItem;
    private ImageView imageView;
    DatabaseReference mRef;
    private StorageReference storageReference;
    int id;
    String key;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        id = ((Asean) Parcels.unwrap(getIntent().getParcelableExtra(KeyAsean.DETAIL))).getId();
        key = getIntent().getStringExtra(KeyAsean.KEY);

        String type = getIntent().getStringExtra(KeyAsean.TYPE);

        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
        mRef = mRootRef.child("asean").child(String.valueOf(id)).child(type);
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        aseanItem = new AseanItem();


        initView();

    }

    @SuppressLint("ShowToast")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                if (isEmpty(edName) || isEmpty(edDetail)) {
                    showAlert("กรุณากรอกข้อความให้ครบถ้วน");
                } else {
                    showLoading();
                    aseanItem.setId(id);
                    aseanItem.setItem_name(edName.getText().toString());
                    aseanItem.setItem_detail(edDetail.getText().toString());

                    imageView.setDrawingCacheEnabled(true);
                    imageView.buildDrawingCache(true);
                    Bitmap bitmap = Bitmap.createBitmap(imageView.getDrawingCache());
                    imageView.setDrawingCacheEnabled(false);
                    putImage(bitmap);
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return true;
    }

    public void initView() {

        edName = (EditText) findViewById(R.id.name);
        edDetail = (EditText) findViewById(R.id.detail);
        imageView = (ImageView) findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        if (key != null) {
            DatabaseReference reference = mRef.child(key);
            showLoading();
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    hideLoading();
                    dataSnapshot.getRef().removeEventListener(this);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    hideLoading();
                    showAlert(R.string.load_failure);
                }
            });
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    aseanItem = dataSnapshot.getValue(AseanItem.class);
                    edName.setText(aseanItem.getItem_name());
                    edDetail.setText(aseanItem.getItem_detail());
                    final StorageReference image = storageReference.child(aseanItem.getItem_image()+".jpg");
                    Glide.with(AddItemActivity.this)
                            .using(new FirebaseImageLoader())
                            .load(image)
                            .into(imageView);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, databaseError.toString());
                }
            });
        }

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {
//                case REQUEST_CODE_CHOOSE_PICTURE_FROM_GALLARY:
//                    Uri selectedImage = data.getData();
//                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
//                    Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    String picturePath = cursor.getString(columnIndex);
//                    cursor.close();
//                    Log.d(TAG, picturePath);
//                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//                    break;
//                default:
//                    break;
//
//            }
//        }
//    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }

    private void chooseImage() {
        EZPhotoPickConfig config = new EZPhotoPickConfig();
        config.photoSource = PhotoSource.GALLERY;
        config.exportingSize = 900;
        EZPhotoPick.startPhotoPickActivity(this, config);
    }

    private void putImage(Bitmap bitmap) {
        DatabaseReference databaseReference = mRef.push();
        String key = databaseReference.getKey();
        uploadFileToStorage(databaseReference, bitmap, key);
    }

    private void uploadFileToStorage(final DatabaseReference databaseReference, Bitmap bitmap, String filename) {
        StorageReference photoReference = storageReference.child(filename + ".jpg");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] data = byteArrayOutputStream.toByteArray();
        UploadTask uploadTask = photoReference.putBytes(data);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                onUploadPhotoSuccess(databaseReference, taskSnapshot);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                onUploadPhotoFailure();
            }
        });
    }

    private void onUploadPhotoSuccess(DatabaseReference databaseReference, UploadTask.TaskSnapshot taskSnapshot) {
        hideLoading();
        aseanItem.setItem_image(databaseReference.getKey());
        databaseReference.setValue(aseanItem);
        Intent returnIntent = new Intent();
        returnIntent.putExtra(KeyAsean.ASEANITEM, Parcels.wrap(aseanItem));
        returnIntent.putExtra(KeyAsean.KEY, key);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void onUploadPhotoFailure() {
        hideLoading();
        showAlert("Can\\'t upload photo to server. Please try again later.");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EZPhotoPick.PHOTO_PICK_GALLERY_REQUEST_CODE &&
                resultCode == RESULT_OK) {
            try {
                Bitmap pickedPhoto = new EZPhotoPickStorage(this).loadLatestStoredPhotoBitmap();
//                bitmap = pickedPhoto;
                imageView.setImageBitmap(pickedPhoto);
//                putImage(pickedPhoto);
            } catch (IOException e) {
                e.printStackTrace();
//                onUploadPhotoFailure();
            }
        }
    }


}
