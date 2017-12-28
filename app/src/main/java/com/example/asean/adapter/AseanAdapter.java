package com.example.asean.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asean.MainActivity;
import com.example.asean.R;
import com.example.asean.model.Asean;
import com.example.asean.realm.RealmController;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by lalit on 10/10/2016.
 */

public class AseanAdapter extends RealmRecyclerViewAdapter<Asean> {

    private Context context;
    private Realm realm;
    private OnClickAseanListener listener;

    public interface OnClickAseanListener{
        void onClick(Asean asean);
    }


    public AseanAdapter(Context context){
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_asean, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        realm = RealmController.getInstance().getRealm();
        final Asean asean = getItem(position);
        final ViewHolder vh = (ViewHolder) holder;

        int resID = context.getResources().getIdentifier(asean.getFlag_image(), "drawable", context.getPackageName());

        vh.textViewName.setText(asean.getName());

        try {
            Log.i("Asean adpater","Jay check resID : " + resID);
            Picasso.with(context).load(resID).into(vh.imageViewFlag);
        } catch (Error err) {
            vh.imageViewFlag.setImageResource(R.mipmap.ic_launcher);
        }
    }

    @Override
    public int getItemCount() {
        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }
        return 0;
    }

    public void setOnclickItem(OnClickAseanListener listener){
        this.listener = listener;
    }
//
//    public AseanAdapter(Context context, int resource, ArrayList<Asean> objects) {
//        super(context, resource, objects);
//        vi = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        Resource = resource;
//        actorList = objects;
//    }


//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // convert view = design
//        View v = convertView;
//        String urlImg = actorList.get(position).getFlag_image();
//        Log.i("Asean adpater","Jay check url image : " + urlImg);
//        //String mDrawableName = "myimg";
//        //int resID = v.getResources().getIdentifier(urlImg , "drawable", vi.getContext().getPackageName());
//        //int resID = v.getResources().getIdentifier(urlImg , "drawable", vi.getContext().getPackageName());
//        int resID = getContext().getResources().getIdentifier(urlImg, "drawable", getContext().getPackageName());
//
//        if (v == null) {
//            holder = new ViewHolder();
//            v = vi.inflate(Resource, null);
//            holder.imageViewFlag = (ImageView) v.findViewById(R.id.image_view_flag);
//            holder.textViewName = (TextView) v.findViewById(R.id.text_view_flage_name);
//            v.setTag(holder);
//        } else {
//            holder = (ViewHolder) v.getTag();
//        }
//
//        try {
//            Log.i("Asean adpater","Jay check resID : " + resID);
//            Picasso.with(getContext()).load(resID).into(holder.imageViewFlag);
//        } catch (Error err) {
//            holder.imageViewFlag.setImageResource(R.mipmap.ic_launcher);
//        }
//        holder.textViewName.setText(actorList.get(position).getName());
//        return v;
//
//    }


    private class ViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener  {
        private ImageView imageViewFlag;
        private TextView textViewName;

        private ViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);
            imageViewFlag = (ImageView) itemView.findViewById(R.id.image_view_flag);
            textViewName = (TextView) itemView.findViewById(R.id.text_view_flage_name);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            listener.onClick(getItem(getAdapterPosition()));
        }
    }
}