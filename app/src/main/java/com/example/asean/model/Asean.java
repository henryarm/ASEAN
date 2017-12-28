package com.example.asean.model;

import com.example.asean.realm.AseanItemListParcelConverter;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.util.ArrayList;

import io.realm.AseanRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by lalit on 9/12/2016.
 */
@Parcel(implementations = { AseanRealmProxy.class }, value = Parcel.Serialization.BEAN, analyze = { Asean.class })
public class Asean extends RealmObject{

    @PrimaryKey
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
    private RealmList<AseanItem> travel;
    private RealmList<AseanItem> food;
    private AseanItemMoney money;

    public Asean() {
        // TODO Auto-generated constructor stub
    }

//    public Asean(int id, String name, String flag_image, String city,
//                        String language, String population, String religion, String regime, String flower_name,
//                        String flower_image, String flower_detail, String national_dress_image,
//                        String national_dress_detail, AseanItem travel, AseanItem food, AseanItemMoney money) {
//        super();
//        this.id = id;
//        this.name = name;
//        this.flag_image = flag_image;
//        this.city = city;
//        this.language = language;
//        this.population = population;
//        this.religion = religion;
//        this.regime = regime;
//        this.flower_name = flower_name;
//        this.flower_image = flower_image;
//        this.flower_detail = flower_detail;
//        this.national_dress_image = national_dress_image;
//        this.national_dress_detail = national_dress_detail;
//        this.travel = travel;
//        this.food = food;
//        this.money = money;
//    }

    public int getId() {
        return id;
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


    public ArrayList<AseanItem> getTravel() {
        return new ArrayList<>(travel);
    }

    @ParcelPropertyConverter(AseanItemListParcelConverter.class)
    public void setTravel(RealmList<AseanItem>  travel) {
        this.travel = travel;
    }

    public ArrayList<AseanItem>  getFood() {
        return new ArrayList<>(food);
    }

    @ParcelPropertyConverter(AseanItemListParcelConverter.class)
    public void setFood(RealmList<AseanItem>  food) {
        this.food = food;
    }

    public void addFood(AseanItem aseanItem){
        this.food.add(aseanItem);
    }

    public void addTravel(AseanItem aseanItem){
        this.travel.add(aseanItem);
    }

    public void removeFood(AseanItem aseanItem){
        this.food.remove(aseanItem);
    }

    public void removeTravel(AseanItem aseanItem){
        this.travel.remove(aseanItem);
    }
    public AseanItemMoney getMoney() {
        return money;
    }

    public void setMoney(AseanItemMoney money) {
        this.money = money;
    }

}
