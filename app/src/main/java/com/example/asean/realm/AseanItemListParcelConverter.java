package com.example.asean.realm;

import android.os.Parcel;

import com.example.asean.model.AseanItem;

import org.parceler.Parcels;

/**
 * Created by armshare on 23/12/2017 AD.
 */

public class AseanItemListParcelConverter extends RealmListParcelConverter<AseanItem> {
    @Override
    public void itemToParcel(AseanItem input, Parcel parcel) {
        parcel.writeParcelable(Parcels.wrap(input), 0);
    }

    @Override
    public AseanItem itemFromParcel(Parcel parcel) {
        return Parcels.unwrap(parcel.readParcelable(AseanItem.class.getClassLoader()));
    }
}
