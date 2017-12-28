package com.example.asean.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.asean.model.Asean;

import io.realm.OrderedRealmCollection;
import io.realm.RealmResults;

public class RealmAseanAdapter extends RealmModelAdapter<Asean> {
    public RealmAseanAdapter(@Nullable OrderedRealmCollection<Asean> data) {
        super(data);
    }

//    public RealmAseanAdapter(Context context, RealmResults<Asean> realmResults, boolean automaticUpdate) {
//
//        super(context, realmResults, automaticUpdate);
//    }
}