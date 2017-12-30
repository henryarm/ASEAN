package com.example.asean.model;

import com.google.firebase.database.IgnoreExtraProperties;

import org.parceler.Parcel;

//import io.realm.AseanRealmProxy;
/**
 * Created by lalit on 9/12/2016.
 */
@Parcel
@IgnoreExtraProperties
public class AseanItem{

    private int id;
    private String item_name;
    private String item_detail;
    private String item_image;


    public AseanItem() {
        // TODO Auto-generated constructor stub
    }

    public AseanItem(String item_name, String item_detail, String item_image) {

        super();
        this.item_name = item_name;
        this.item_detail = item_detail;
        this.item_image = item_image;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_detail() {
        return item_detail;
    }

    public void setItem_detail(String item_detail) {
        this.item_detail = item_detail;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

