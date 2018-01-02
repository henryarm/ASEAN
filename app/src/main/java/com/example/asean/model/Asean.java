package com.example.asean.model;

import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.IgnoreExtraProperties;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import io.realm.AseanRealmProxy;

/**
 * Created by lalit on 9/12/2016.
 */
@Parcel
@IgnoreExtraProperties
public class Asean {

    //    @PrimaryKey
    private int id;
    private String name;
    private String flag_image;
    private String city;
    private String language;
    private String population;
    private String religion;
    private String regime;
    private String flower_name;
    private String flower_image;
    private String flower_detail;
    private String national_dress_image;
    private String national_dress_detail;
    private Map<String, AseanItem> travel;
    private List<AseanItem> food;
    private AseanItemMoney money;

    public Asean() {
    }

    public int getId() {
        return id-1;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag_image() {
        return flag_image;
    }

    public void setFlag_image(String flag_image) {
        this.flag_image = flag_image;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getRegime() {
        return regime;
    }

    public void setRegime(String regime) {
        this.regime = regime;
    }

    public String getFlower_name() {
        return flower_name;
    }

    public void setFlower_name(String flower_name) {
        this.flower_name = flower_name;
    }

    public String getFlower_image() {
        return flower_image;
    }

    public void setFlower_image(String flower_image) {
        this.flower_image = flower_image;
    }

    public String getFlower_detail() {
        return flower_detail;
    }

    public void setFlower_detail(String flower_detail) {
        this.flower_detail = flower_detail;
    }

    public String getNational_dress_image() {
        return national_dress_image;
    }

    public void setNational_dress_image(String national_dress_image) {
        this.national_dress_image = national_dress_image;
    }

    public String getNational_dress_detail() {
        return national_dress_detail;
    }

    public void setNational_dress_detail(String national_dress_detail) {
        this.national_dress_detail = national_dress_detail;
    }


//    public ArrayList<AseanItem> getTravel() {
//        ArrayList<AseanItem> aseanItems = new ArrayList<>();
//        aseanItems.addAll(travel.values());
//        return aseanItems;
//    }

//    public void setTravel(ArrayList<AseanItem> travel) {
//        this.travel = travel;
////    }
//
//    public ArrayList<AseanItem> getFood() {
//
//        return new ArrayList<AseanItem>(food);
//    }
//
//    public void setFood(ArrayList<AseanItem> food) {
//        this.food = food;
//    }
//
//    public void addFood(AseanItem aseanItem) {
//        this.food.add(aseanItem);
//    }

/*    public void addTravel(AseanItem aseanItem) {
        this.travel.add(aseanItem);
    }*/

    public AseanItemMoney getMoney() {
        return money;
    }
}