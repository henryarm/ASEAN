package com.example.asean.realm;


import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.example.asean.adapter.AseanItemAdapter;
import com.example.asean.model.Asean;
import com.example.asean.model.AseanItem;
import com.example.asean.model.KeyAsean;

import org.parceler.Parcels;

import io.realm.Realm;
import io.realm.RealmResults;


public class RealmController {

    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //find all objects in the Book.class
    public RealmResults<Asean> getAseans() {

        return realm.where(Asean.class).findAll();
    }

    public Asean getAseans(int id){
        return realm.where(Asean.class).equalTo("id",id).findFirst();
    }

    public void addAseanFood(int id, AseanItem aseanItem, AseanItemAdapter adapter){
        Asean query = realm.where(Asean.class).equalTo("id",id).findFirst();

        realm.beginTransaction();
        if (query != null) {
            query.addFood(aseanItem);
            realm.copyToRealmOrUpdate(query);
        }
        realm.commitTransaction();
        adapter.add(aseanItem);
        adapter.notifyDataSetChanged();
    }

    public void addAseanTravel(int id, AseanItem aseanItem, AseanItemAdapter adapter){
        Asean query = realm.where(Asean.class).equalTo("id",id).findFirst();

        realm.beginTransaction();
        if (query != null) {
            query.addTravel(aseanItem);
            realm.copyToRealmOrUpdate(query);
        }
        realm.commitTransaction();
        adapter.add(aseanItem);
        adapter.notifyDataSetChanged();
    }

    public void removeAseanFood(int id,AseanItem aseanItem,AseanItemAdapter adapter){
        Asean query = realm.where(Asean.class).equalTo("id",id).findFirst();

        realm.beginTransaction();
        if (query != null) {
            query.removeFood(aseanItem);
            realm.copyToRealmOrUpdate(query);
        }
        realm.commitTransaction();
        adapter.remove(aseanItem);
        adapter.notifyDataSetChanged();

    }

    public void removeAseanTravel(int id,AseanItem aseanItem,AseanItemAdapter adapter){
        Asean query = realm.where(Asean.class).equalTo("id",id).findFirst();

        realm.beginTransaction();
        if (query != null) {
            query.removeTravel(aseanItem);
            realm.copyToRealmOrUpdate(query);
        }
        realm.commitTransaction();
        adapter.remove(aseanItem);
        adapter.notifyDataSetChanged();

    }
//    //clear all objects from Book.class
//    public void clearAll() {
//
//        realm.beginTransaction();
//        realm.clear(Book.class);
//        realm.commitTransaction();
//    }
//
//    //find all objects in the Book.class
//    public RealmResults<Book> getBooks() {
//
//        return realm.where(Book.class).findAll();
//    }
//
//    //query a single item with the given id
//    public Book getBook(String id) {
//
//        return realm.where(Book.class).equalTo("id", id).findFirst();
//    }
//
//    //check if Book.class is empty
//    public boolean hasBooks() {
//
//        return !realm.allObjects(Book.class).isEmpty();
//    }
//
//    //query example
//    public RealmResults<Book> queryedBooks() {
//
//        return realm.where(Book.class)
//                .contains("author", "Author 0")
//                .or()
//                .contains("title", "Realm")
//                .findAll();
//
//    }
}
