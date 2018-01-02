package com.example.asean.model;

import org.parceler.Parcel;


/**
 * Created by lalit on 9/12/2016.
 */
@Parcel
public class AseanItemMoney {

    private String money_detail_1;
    private String money_detail_2;
    private String money_detail_3;
    private String money_detail_4;
    private String money_detail_5;

    private String money_image_a;
    private String money_image_b;


    public AseanItemMoney() {
        // TODO Auto-generated constructor stub
    }

    public String getMoney_detail_1() {
        return money_detail_1;
    }

    public void setMoney_detail_1(String money_detail_1) {
        this.money_detail_1 = money_detail_1;
    }

    public String getMoney_detail_2() {
        return money_detail_2;
    }

    public void setMoney_detail_2(String money_detail_2) {
        this.money_detail_2 = money_detail_2;
    }

    public String getMoney_detail_3() {
        return money_detail_3;
    }

    public void setMoney_detail_3(String money_detail_3) {
        this.money_detail_3 = money_detail_3;
    }

    public String getMoney_detail_4() {
        return money_detail_4;
    }

    public void setMoney_detail_4(String money_detail_4) {
        this.money_detail_4 = money_detail_4;
    }

    public String getMoney_detail_5() {
        return money_detail_5;
    }

    public void setMoney_detail_5(String money_detail_5) {
        this.money_detail_5 = money_detail_5;
    }

    public String getMoney_image_a() {
        return money_image_a;
    }

    public void setMoney_image_a(String money_image_a) {
        this.money_image_a = money_image_a;
    }

    public String getMoney_image_b() {
        return money_image_b;
    }

    public void setMoney_image_b(String money_image_b) {
        this.money_image_b = money_image_b;
    }

    public String getDetail(){
        return
//                money_detail_2+" "+
                money_detail_3+" "+
//                money_detail_4+" "+
                money_detail_5+" ";
    }
}
