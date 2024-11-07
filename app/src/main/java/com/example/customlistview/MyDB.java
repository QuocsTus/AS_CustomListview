package com.example.customlistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {

    public static final String TableName = "ContactTable";
    public static final String Id = "Id";
    public static final String Name = "Name";
    public static final String Phone = "Phone";
    public static final String Image = "Image";



    public MyDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MyDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public MyDB(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String sqlCreate = "Create table if not exists " + TableName + "("
                + Id + " Integer Primary Key, "
                + Image + " Text, "
                + Name + " Text, "
                + Phone + " Text)";
        database.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("Drop Table if exists " + TableName);
        onCreate(database);
    }

    public ArrayList<Contact> getAllContact(){
        ArrayList<Contact> list = new ArrayList<>();

        String sql = "Select * from " + TableName;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor!=null){
            while (cursor.moveToNext()){
                Contact contact = new Contact(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3));
                list.add(contact);
            }
        }
        return list;
    }

    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Id, contact.getId());
        value.put(Image, contact.getImages());
        value.put(Name, contact.getName());
        value.put(Phone, contact.getPhone());
        db.insert(TableName, null,value);
        db.close();
    }

    public void updateContact(int id, Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Image, contact.getImages());
        values.put(Name, contact.getName());
        values.put(Phone, contact.getPhone());

        // Update the row where the Id matches the specified id
        db.update(TableName, values, Id + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteContact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ContactTable", "Id = ?", new String[]{String.valueOf(id)});
        db.close();
    }


}
