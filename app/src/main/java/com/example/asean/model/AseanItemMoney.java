package com.example.asean.model;

import org.parceler.Parcel;


/**
 * Created by lalit on 9/12/2016.
 */
@Parcel
public class AseanItemMoney {

    private String item_money_name;
    private String item_money_a;
    private String item_money_b;


    public AseanItemMoney() {
        // TODO Auto-generated constructor stub
    }

    public AseanItemMoney(String item_money_name, String item_money_a, String item_money_b) {

        super();
        this.item_money_name = item_money_name;
        this.item_money_a = item_money_a;
        this.item_money_b = item_money_b;
    }

    public String getItem_money_name() {
        return item_money_name;
    }

    public void setItem_money_name(String item_money_name) {
        this.item_money_name = item_money_name;
    }

    public String getItem_money_a() {
        return item_money_a;
    }

    public void setItem_money_a(String item_money_a) {
        this.item_money_a = item_money_a;
    }

    public String getItem_money_b() {
        return item_money_b;
    }

    public void setItem_money_b(String item_money_b) {
        this.item_money_b = item_money_b;
    }

}
