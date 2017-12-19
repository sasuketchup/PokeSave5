package com.example.pokesave5;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sousuke on 2017/09/19.
 */

public class MyOpenHelper extends SQLiteOpenHelper {

    public MyOpenHelper(Context context) {
        super(context, "NameDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table NameTable2(" + "id text primary key," + "pkName text," + "ncName text,"+"tokusei text,"+"Item text,"+"eggGroup1 text,"+"eggGroup2 text"+");");
        db.execSQL("create table Vkotai(" + "id integer," + "kotaiH integer," + "kotaiA integer," + "kotaiB integer," + "kotaiC integer," + "kotaiD integer," + "kotaiS integer" + ");");
        db.execSQL("create table Character(" + "id integer," +
                "upA integer," + "upB integer," + "upC integer," + "upD integer," + "upS integer," +
                "dnA integer," + "dnB integer," + "dnC integer," + "dnD integer," + "dnS integer," +
                "non integer" + ");");
//        db.execSQL("alter table NameTable2 add ncName text");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }


}
