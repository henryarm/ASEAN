package com.example.asean.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;


public class RealmModelAdapter<T extends RealmObject> extends RealmBaseAdapter<T> {
    public RealmModelAdapter(@Nullable OrderedRealmCollection<T> data) {
        super(data);
    }

//    public RealmModelAdapter(Context context, RealmResults<T> realmResults, boolean automaticUpdate) {
//
//        super();
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;
    }
}