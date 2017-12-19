package com.example.pokesave5;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView NamePoke;
    private LinearLayout varliLay;

//    public void onActivityResult(){
//        super.onCreate();
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.recreate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    // }

//    @Override
//    protected void onResume(){
//        super.onResume();

        NamePoke = ((TextView) findViewById(R.id.textView));
        varliLay = (LinearLayout) findViewById(R.id.liLay1);

        MyOpenHelper helper = new MyOpenHelper(this);
        final SQLiteDatabase db = helper.getReadableDatabase();

//        ContentValues cv = new ContentValues();
//
//        cv.put("id", "0");
//        cv.put("pkName", "Pokemon");
//
//        int numberOfColums = db.update("NameTable2", cv, null, null);
//
//        if(numberOfColums == 0) {
//            db.insert("NameTable2", null, cv);
//        }

        Cursor cursor = db.query("NameTable2", new String[] {"id","pkName"}, null, null, null, null, null);

        long idCount = DatabaseUtils.queryNumEntries(db, "NameTable2");

        // 保存されているポケモン名を表示するテキストビューの定義
        TextView[] textpkName = new TextView[(int) idCount];

        // 保存されているポケモン名を表示
        cursor.moveToFirst();
        for(int i=0;i<idCount;i++) {
            textpkName[i] = new TextView(this);
            String valueCursor = cursor.getString(1);
            textpkName[i].setText(valueCursor);
            textpkName[i].setTextSize(24);
            cursor.moveToNext();
            varliLay.addView(textpkName[i]);
        }
        cursor.close();

        // int i = 0;
//        boolean mov = cursor.moveToFirst();
//        while (mov){
//            // TextView textpkName = new TextView(this);
//            String valueCursur = cursor.getString(1);
//            textpkName[1].setText(valueCursur);
//            mov = cursor.moveToNext();
//            varliLay.addView(textpkName[1]);
//            // i++;
//        }
//        cursor.close();

//        if(valueCursor == "") {
//            valueCursor = "ポケモン名";
//        }

        // 保存されているポケモン名をタップしたときの処理
        for(int i=0;i<idCount;i++) {
            final int finalI = i+1;
            textpkName[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor2 = db.query("NameTable2", new String[] {"id","pkName","ncName","tokusei","Item","eggGroup1","eggGroup2"}, "id="+ finalI, null, null, null, null);
                    cursor2.moveToFirst();
                    int idNum = cursor2.getInt(0);
                    String getpoke = cursor2.getString(1);
                    String getnick = cursor2.getString(2);
                    String gettoku = cursor2.getString(3);
                    String getItem = cursor2.getString(4);
                    String getegg1 = cursor2.getString(5);
                    String getegg2 = cursor2.getString(6);
                    cursor2.close();

                    Cursor cursor3 = db.query("Vkotai", new String[] {"kotaiH","kotaiA","kotaiB","kotaiC","kotaiD","kotaiS"}, "id="+ finalI, null, null, null, null);
                    cursor3.moveToFirst();
                    int[] getV = new int[6];
                    for(int i=0;i<6;i++){
                        getV[i] = cursor3.getInt(i);
                    }
                    cursor3.close();

                    Cursor cursor4 = db.query("Character", new String[] {"upA", "upB", "upC", "upD", "upS", "dnA", "dnB", "dnC", "dnD", "dnS", "non"}, "id="+finalI, null, null, null, null);
                    cursor4.moveToFirst();
                    int[] getupChara = new int[5];
                    int[] getdnChara = new int[5];
                    int getnonChara =cursor4.getInt(10);
                    for(int i=0;i<11;i++){
                        getupChara[i] = cursor4.getInt(i);
                        getdnChara[i] = cursor4.getInt(i+5);
                    }
                    cursor4.close();

                    Intent dbIntent2 = new Intent(MainActivity.this,SecondActivity.class);
                    if(getnick == null){
                        getnick = "-";
                    }
                    dbIntent2.putExtra("passId", idNum);
                    dbIntent2.putExtra("passName", getpoke);
                    dbIntent2.putExtra("passnick", getnick);
                    dbIntent2.putExtra("passtoku", gettoku);
                    dbIntent2.putExtra("passItem", getItem);
                    dbIntent2.putExtra("passegg1", getegg1);
                    dbIntent2.putExtra("passegg2", getegg2);
                    dbIntent2.putExtra("passkotai", getV);
                    dbIntent2.putExtra("passupChara", getupChara);
                    dbIntent2.putExtra("passdnChara", getdnChara);
                    dbIntent2.putExtra("passnonChara", getnonChara);
                    startActivityForResult(dbIntent2,1);
                }
            });
        }

        // 追加ボタン
        ((Button) findViewById(R.id.addbtn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // long idNumber = DatabaseUtils.queryNumEntries(db, "NameTable2");
                Intent dbIntent = new Intent(MainActivity.this,SecondActivity.class);
                dbIntent.putExtra("passName", 0);
                startActivityForResult(dbIntent,1);
            }
        });
    }
}
