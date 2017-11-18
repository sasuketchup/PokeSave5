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
        db.execSQL("alter table NameTable2 add ncName text");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }


}
