package com.example.asean;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Parcel;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asean.model.AseanItem;
import com.example.asean.model.KeyAsean;

import org.parceler.Parcels;

public class AddItemActivity extends AppCompatActivity {

    private static final String TAG = "AddItemActivity";
    private static final int REQUEST_CODE_CHOOSE_PICTURE_FROM_GALLARY = 99;
    private EditText edName,edDetail;
    private AseanItem aseanItem;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        initView();

        aseanItem = new AseanItem();
    }

    @SuppressLint("ShowToast")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                if (isEmpty(edName) || isEmpty(edDetail)){
                    Toast.makeText(AddItemActivity.this, "กรุณากรอกข้อความให้ครบถ้วน", Toast.LENGTH_LONG).show();
//                    Log.d(TAG, "error");
                }
                else{
                    aseanItem.setItem_image(edName.getText().toString());
                    aseanItem.setItem_name(edName.getText().toString());
                    aseanItem.setItem_detail(edDetail.getText().toString());
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(KeyAsean.ASEANITEM, Parcels.wrap(aseanItem));
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu,menu);
        return true;
    }

    public void initView(){

        edName = (EditText) findViewById(R.id.name);
        edDetail = (EditText) findViewById(R.id.detail);
        imageView = (ImageView) findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_CODE_CHOOSE_PICTURE_FROM_GALLARY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOOSE_PICTURE_FROM_GALLARY:
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    Log.d(TAG, picturePath);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    break;
                default:
                    break;

            }
        }
    }

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }
}
