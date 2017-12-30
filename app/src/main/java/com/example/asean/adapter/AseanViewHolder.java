package com.example.asean.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asean.MainActivity;
import com.example.asean.R;

/**
 * Created by ARMSHARE on 30/12/2560.
 */

public class AseanViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageViewFlag;
    public TextView textViewName;

    public AseanViewHolder(View itemView) {
        // standard view holder pattern with Butterknife view injection
        super(itemView);
        imageViewFlag = (ImageView) itemView.findViewById(R.id.image_view_flag);
        textViewName = (TextView) itemView.findViewById(R.id.text_view_flage_name);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null)
                    mClickListener.onItemClick(v, getAdapterPosition());

            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mClickListener != null)
                    mClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });

    }

    private AseanViewHolder.ClickListener mClickListener;

    //Interface to send callbacks...
    public interface ClickListener {
        public void onItemClick(View view, int position);

        public void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(AseanViewHolder.ClickListener clickListener) {
        mClickListener = clickListener;
    }
}
