package com.elfan.mytask.helper;

import android.content.Context;
import android.widget.Toast;

import com.elfan.mytask.model.NoteModel;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class RealmHelper {
    private Context context;
    private Realm realm;

    public RealmHelper(Context context) {
        this.context = context;
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public void insertData(NoteModel noteModel){
        realm.beginTransaction();
        realm.copyToRealm(noteModel);
        realm.commitTransaction();
        /*realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                Toast.makeText(context, "Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
            }
        });*/
        realm.close();
    }

    public NoteModel showOneData(Integer id){
        NoteModel noteModel = realm.where(NoteModel.class).equalTo("id", id).findFirst();
        return  noteModel;
    }

    public void updateData(NoteModel noteModel){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(noteModel);
        realm.commitTransaction();
        /*realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                Toast.makeText(context, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
            }
        });*/
        realm.close();
    }

    public void deleteData(Integer id){
        realm.beginTransaction();
        NoteModel noteModel = realm.where(NoteModel.class).equalTo("id", id).findFirst();
        noteModel.deleteFromRealm();
        realm.commitTransaction();
        /*realm.addChangeListener(new RealmChangeListener<Realm>() {
            @Override
            public void onChange(Realm realm) {
                Toast.makeText(context, "Data Berhasil DiHapus", Toast.LENGTH_SHORT).show();
            }
        });*/
        realm.close();
    }

    public List<NoteModel> showData(){
        RealmResults<NoteModel> datahasil = realm.where(NoteModel.class).findAll();
        List<NoteModel> datalist = new ArrayList<>();
        datalist.addAll(realm.copyFromRealm(datahasil));
        return datalist;
    }

    private List<NoteModel>filterData(List<NoteModel> noteModels, String newQuery){
        String lowercasequery = newQuery.toLowerCase();
        List<NoteModel> filterData = new ArrayList<>();
        for (int i = 0; i < noteModels.size(); i++) {
            String text = noteModels.get(i).getJudul().toLowerCase();
            if (text.contains(lowercasequery)){
                filterData.add(noteModels.get(i));
            }
        }
        return filterData;

    }

    public long getNextId(){
        if (realm.where(NoteModel.class).count() != 0){
            long id = realm.where(NoteModel.class).max("id").longValue();
            return id+1;
        }else{
            return 1;
        }
    }
}
