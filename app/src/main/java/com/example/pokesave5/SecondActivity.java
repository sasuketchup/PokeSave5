package com.example.pokesave5;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sousuke on 2017/09/19.
 */

public class SecondActivity extends AppCompatActivity {

    private EditText pokeName;
    private EditText nickName;
    private EditText tokusei;
    private EditText Item;
    private TextView egg1;
    private TextView egg2;
    private TextView[] statV = new TextView[6];

    private TextView test1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        // 受け取った必要V箇所の状態を格納する配列
        int[] catchV = new int[6];
//        for(int k=0;k<6;k++){
//            Vsave[k] = 0;
//        }

        // メインアクティビティから値を受け取る
        Intent i = getIntent();
        final int catchId = i.getIntExtra("passId", 0);
        final String catchName = i.getStringExtra("passName");
        final String catchnick = i.getStringExtra("passnick");
        final String catchtoku = i.getStringExtra("passtoku");
        final String catchItem = i.getStringExtra("passItem");
        final String catchegg1 = i.getStringExtra("passegg1");
        final String catchegg2 = i.getStringExtra("passegg2");

        catchV = i.getIntArrayExtra("passkotai");

        MyOpenHelper helper =new MyOpenHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();

        pokeName = (EditText) findViewById(R.id.editText);
        nickName = (EditText) findViewById(R.id.editText2);
        tokusei = (EditText) findViewById(R.id.tokusei);
        Item = (EditText) findViewById(R.id.Item);
        egg1 = (TextView) findViewById(R.id.eggGroup1);
        egg2 = (TextView) findViewById(R.id.eggGroup2);

        test1 = (TextView) findViewById(R.id.test);

        statV[0] = (TextView) findViewById(R.id.tvVH);
        statV[1] = (TextView) findViewById(R.id.tvVA);
        statV[2] = (TextView) findViewById(R.id.tvVB);
        statV[3] = (TextView) findViewById(R.id.tvVC);
        statV[4] = (TextView) findViewById(R.id.tvVD);
        statV[5] = (TextView) findViewById(R.id.tvVS);

        // 必要V箇所の状態を格納する配列
        final int[] Vsave = new int[6];
        for(int k=0;k<6;k++){
            Vsave[k] = 0;
        }

        // 受け取った値を表示
        if(catchName != null) {
            pokeName.setText(catchName);
            nickName.setText(catchnick);
            tokusei.setText(catchtoku);
            Item.setText(catchItem);
            egg1.setText(catchegg1);
            egg2.setText(catchegg2);

            for(int j=0;j<6;j++){
                Vsave[j] = catchV[j];
                if(Vsave[j] == 0){
                    statV[j].setTextColor(Color.parseColor("#a9a9a9"));
                }else if(Vsave[j] == 1){
                    statV[j].setTextColor(Color.parseColor("#87ceeb"));
                }else{
                    statV[j].setTextColor(Color.parseColor("#ff69b4"));
                }
            }

//            test1.setText(catchV);
        }

        // タマゴグループ1の選択
        egg1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // ダイアログに表示するタマゴグループの種類を配列に格納
                        final String[] eggGp = {"陸上", "虫", "怪獣", "植物", "飛行", "人型", "妖精", "ドラゴン", "鉱物", "不定形", "水中1", "水中2", "水中3", "メタモン", "未発見"};
                        // アラートダイアログを生成
                        new AlertDialog.Builder(SecondActivity.this).setTitle(null).setItems(eggGp, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                egg1.setText(eggGp[which]);
                                switch (which) {
                                    case 0:
                                        egg1.setText("陸上");
                                        break;
                                    case 1:
                                        egg1.setText("虫");
                                        break;
                                    case 2:
                                        egg1.setText("怪獣");
                                        break;
                                    case 3:
                                        egg1.setText("植物");
                                        break;
                                    case 4:
                                        egg1.setText("飛行");
                                        break;
                                    case 5:
                                        egg1.setText("人型");
                                        break;
                                    case 6:
                                        egg1.setText("妖精");
                                        break;
                                    case 7:
                                        egg1.setText("ドラゴン");
                                        break;
                                    case 8:
                                        egg1.setText("鉱物");
                                        break;
                                    case 9:
                                        egg1.setText("不定形");
                                        break;
                                    case 10:
                                        egg1.setText("水中1");
                                        break;
                                    case 11:
                                        egg1.setText("水中2");
                                        break;
                                    case 12:
                                        egg1.setText("水中3");
                                        break;
                                    case 13:
                                        egg1.setText("メタモン");
                                        break;
                                    case 14:
                                        egg1.setText("未発見");
                                        break;

                                }
                            }
                        }).show();
                    }
                }
        );

        // タマゴグループ2の選択
        egg2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // ダイアログに表示するタマゴグループの種類を配列に格納
                        final String[] eggGp = {"陸上", "虫", "怪獣", "植物", "飛行", "人型", "妖精", "ドラゴン", "鉱物", "不定形", "水中1", "水中2", "水中3", "メタモン", "未発見"};
                        // アラートダイアログを生成
                        new AlertDialog.Builder(SecondActivity.this).setTitle(null).setItems(eggGp, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                switch (which) {
                                    case 0:
                                        egg2.setText("陸上");
                                        break;
                                    case 1:
                                        egg2.setText("虫");
                                        break;
                                    case 2:
                                        egg2.setText("怪獣");
                                        break;
                                    case 3:
                                        egg2.setText("植物");
                                        break;
                                    case 4:
                                        egg2.setText("飛行");
                                        break;
                                    case 5:
                                        egg2.setText("人型");
                                        break;
                                    case 6:
                                        egg2.setText("妖精");
                                        break;
                                    case 7:
                                        egg2.setText("ドラゴン");
                                        break;
                                    case 8:
                                        egg2.setText("鉱物");
                                        break;
                                    case 9:
                                        egg2.setText("不定形");
                                        break;
                                    case 10:
                                        egg2.setText("水中1");
                                        break;
                                    case 11:
                                        egg2.setText("水中2");
                                        break;
                                    case 12:
                                        egg2.setText("水中3");
                                        break;
                                    case 13:
                                        egg2.setText("メタモン");
                                        break;
                                    case 14:
                                        egg2.setText("未発見");
                                        break;

                                }
                            }
                        }).show();
                    }
                }
        );

        // 必要V箇所を選択
        for(int j=0;j<6;j++){
            final int finalJ = j;
            statV[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Vsave[finalJ] == 0) {
                        statV[finalJ].setTextColor(Color.parseColor("#87ceeb"));
                        Vsave[finalJ] = 1;
                    }else if(Vsave[finalJ] == 1){
                        statV[finalJ].setTextColor(Color.parseColor("#ff69b4"));
                        Vsave[finalJ] = 2;
                    }else{
                        statV[finalJ].setTextColor(Color.parseColor("#a9a9a9"));
                        Vsave[finalJ] = 0;
                    }
                }
            });
        }

        // 保存されているポケモン数を取得
        final long idNumber = DatabaseUtils.queryNumEntries(db, "NameTable2");

        final ContentValues insertValues = new ContentValues();
        final ContentValues insertValues2 = new ContentValues();

        // 保存ボタン
        findViewById(R.id.savebtn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TrainingPokemon tr = new TrainingPokemon();
                        tr.setPkName(pokeName.getText().toString());


                        String writepoke = pokeName.getText().toString();
                        String writenick = nickName.getText().toString();
                        String writetoku = tokusei.getText().toString();
                        String writeItem = Item.getText().toString();
                        String writeegg1 = egg1.getText().toString();
                        String writeegg2 = egg2.getText().toString();

                        insertValues.put("pkName", writepoke);
                        insertValues.put("ncName", writenick);
                        insertValues.put("tokusei", writetoku);
                        insertValues.put("Item", writeItem);
                        insertValues.put("eggGroup1", writeegg1);
                        insertValues.put("eggGroup2", writeegg2);

                        insertValues2.put("kotaiH", Vsave[0]);
                        insertValues2.put("kotaiA", Vsave[1]);
                        insertValues2.put("kotaiB", Vsave[2]);
                        insertValues2.put("kotaiC", Vsave[3]);
                        insertValues2.put("kotaiD", Vsave[4]);
                        insertValues2.put("kotaiS", Vsave[5]);

                        if(catchName == null) {
                            insertValues.put("id", idNumber+1);
                            insertValues2.put("id", idNumber+1);
                            db.insert("NameTable2", null, insertValues);
                            db.insert("Vkotai", null, insertValues2);
                        }else{
                            db.update("NameTable2", insertValues, "id="+catchId, null);
                            db.update("Vkotai", insertValues2, "id="+catchId, null);
                        }

                        finish();
//                        Intent dbIntent = new Intent(SecondActivity.this,MainActivity.class);
//                        startActivity(dbIntent);
                    }
                }
        );

        // 削除ボタン
        findViewById(R.id.deletebtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete("NameTable2", "id="+catchId, null);
                db.delete("Vkotai", "id="+catchId, null);

                if(catchName != null) {
                    for (int i = catchId + 1; i < idNumber + 1; i++) {
                        insertValues.put("id", i - 1);
                        insertValues2.put("id", i - 1);
                        db.update("NameTable2", insertValues, "id=" + i, null);
                        db.update("Vkotai", insertValues2, "id=" + i, null);
                    }
                }

                finish();
                // Intent delIntent = new Intent(SecondActivity.this,MainActivity.class);
                // startActivity(delIntent);
            }
        });
    }
}

class TrainingPokemon {
    private HashMap<String ,String > m_hash;

    public void setPkName(String value){
        this.m_hash.put("pkName", value);
    }
    public void setNcName(String value){
        this.m_hash.put("ncName", value);
    }
    public void setTokusei(String value){
        this.m_hash.put("tokusei", value);
    }
    public void setItem(String value){
        this.m_hash.put("Item", value);
    }
    public void setEggGroup1(String value){
        this.m_hash.put("eggGroup1", value);
    }
    public void setEggGroup2(String value){
        this.m_hash.put("eggGroup2", value);
    }
    public void setKotaiH(String value){
        this.m_hash.put("kotaiH", value);
    }
    public void setKotaiA(String value){
        this.m_hash.put("kotaiA", value);
    }
    public void setKotaiB(String value){
        this.m_hash.put("kotaiB", value);
    }
    public void setKotaiC(String value){
        this.m_hash.put("kotaiC", value);
    }
    public void setKotaiD(String value){
        this.m_hash.put("kotaiD", value);
    }
    public void setKotaiS(String value){
        this.m_hash.put("kotaiS", value);
    }
    public String getPkName(){
        return this.m_hash.get("pkName");
    }
    public String getNcName(){
        return this.m_hash.get("ncName");
    }
    public String getTokusei(){
        return this.m_hash.get("tokusei");
    }
    public String getItem(){
        return this.m_hash.get("Item");
    }
    public String getEggGroup1(){
        return this.m_hash.get("eggGroup1");
    }
    public String getEggGroup2(){
        return this.m_hash.get("eggGroup2");
    }
    public String getKotaiH(){
        return this.m_hash.get("kotaiH");
    }
    public String getKotaiA(){
        return this.m_hash.get("kotaiA");
    }
    public String getKotaiB(){
        return this.m_hash.get("kotaiB");
    }
    public String getKotaiC(){
        return this.m_hash.get("kotaiC");
    }
    public String getKotaiD(){
        return this.m_hash.get("kotaiD");
    }
    public String getKotaiS(){
        return this.m_hash.get("kotaiS");
    }
    public Map<String,String> getHashMap(){
        return Collections.unmodifiableMap(m_hash);
    }

}