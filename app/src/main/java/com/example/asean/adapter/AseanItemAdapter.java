package com.example.asean.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asean.R;
import com.example.asean.ShowImageActivity;
import com.example.asean.model.Asean;
import com.example.asean.model.AseanItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lalit on 10/10/2016.
 */

public class AseanItemAdapter extends ArrayAdapter<AseanItem> {

    ArrayList<AseanItem> actorList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;
    Context context;
//    public interface OnClickAseanItemListener{
//        void onClick(Asean asean);
//    }
    public AseanItemAdapter(Context context, int resource, ArrayList<AseanItem> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        actorList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        String urlImg = actorList.get(position).getItem_image();

        final int resID = getContext().getResources().getIdentifier(urlImg, "drawable", getContext().getPackageName());

        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.imageViewImage = (ImageView) v.findViewById(R.id.image_view_list_image);
            holder.textViewTitle = (TextView) v.findViewById(R.id.text_view_list_title);
            holder.textViewDescription = (TextView) v.findViewById(R.id.text_view_list_description);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        if (resID != 0){
            try {
                Log.i("Asean adpater","Jay check resID : " + resID);
                Picasso.with(getContext()).load(resID).into(holder.imageViewImage);
            } catch (Error err) {
                holder.imageViewImage.setImageResource(R.drawable.icon_image);
            }
        }
        else{
            holder.imageViewImage.setImageResource(R.drawable.icon_image);
        }


        holder.imageViewImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getContext().startActivity(new Intent(getContext(), ShowImageActivity.class).putExtra("image_show",resID));
                //Toast.makeText(getContext(), "Imagr res : " + resID, Toast.LENGTH_SHORT).show();
            }
        });
        holder.textViewTitle.setText(actorList.get(position).getItem_name());
        holder.textViewDescription.setText(actorList.get(position).getItem_detail());
        return v;

    }

    static class ViewHolder {
        public ImageView imageViewImage;
        public TextView textViewTitle;
        public TextView textViewDescription;

    }
}