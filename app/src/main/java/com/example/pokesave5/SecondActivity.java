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
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
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

    // チェックボックスを参照するためのフィールド
    public CheckBox[] varup = new CheckBox[5];
    public CheckBox[] vardn = new CheckBox[5];
    public CheckBox varnon;

    // レイアウトを参照するためのフィールド
    private LinearLayout varChaLay;

    // チェックの状態を記録する配列
    int[] ckdup = {0, 0, 0, 0, 0};
    int[] ckddn = {0, 0, 0, 0, 0};
    int ckdnon = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

//        final Character shChara = new Character();

        // カスタムビューを設定
        final LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        // レイアウトのインスタンス
        varChaLay = (LinearLayout) findViewById(R.id.charaLilay);

//        final View layout = inflater.inflate(R.layout.chara_dlg, (ViewGroup) findViewById(R.id.layout_root));

        // 受け取った必要V箇所の状態を格納する配列
        int[] catchV = new int[6];
//        for(int k=0;k<6;k++){
//            Vsave[k] = 0;
//        }

        // メインアクティビティから値を受け取る
        Intent itnt = getIntent();
        final int catchId = itnt.getIntExtra("passId", 0);
        final String catchName = itnt.getStringExtra("passName");

        if (catchName != null) {
            final String catchnick = itnt.getStringExtra("passnick");
            final String catchtoku = itnt.getStringExtra("passtoku");
            final String catchItem = itnt.getStringExtra("passItem");
            final String catchegg1 = itnt.getStringExtra("passegg1");
            final String catchegg2 = itnt.getStringExtra("passegg2");

            catchV = itnt.getIntArrayExtra("passkotai");

            ckdup = itnt.getIntArrayExtra("passupChara");
            ckddn = itnt.getIntArrayExtra("passdnChara");
            ckdnon = itnt.getIntExtra("passnonChara", 0);

            // 受け取った値を表示
            pokeName.setText(catchName);
            nickName.setText(catchnick);
            tokusei.setText(catchtoku);
            Item.setText(catchItem);
            egg1.setText(catchegg1);
            egg2.setText(catchegg2);
        }

        MyOpenHelper helper = new MyOpenHelper(this);
        final SQLiteDatabase db = helper.getWritableDatabase();

        pokeName = (EditText) findViewById(R.id.editText);
        nickName = (EditText) findViewById(R.id.editText2);
        tokusei = (EditText) findViewById(R.id.tokusei);
        Item = (EditText) findViewById(R.id.Item);
        egg1 = (TextView) findViewById(R.id.eggGroup1);
        egg2 = (TextView) findViewById(R.id.eggGroup2);

        statV[0] = (TextView) findViewById(R.id.tvVH);
        statV[1] = (TextView) findViewById(R.id.tvVA);
        statV[2] = (TextView) findViewById(R.id.tvVB);
        statV[3] = (TextView) findViewById(R.id.tvVC);
        statV[4] = (TextView) findViewById(R.id.tvVD);
        statV[5] = (TextView) findViewById(R.id.tvVS);

        // フィールドにチェックボックスのインスタンスを代入
//        varup[0] = (CheckBox) layout.findViewById(R.id.upA);
//        varup[1] = (CheckBox) layout.findViewById(R.id.upB);
//        varup[2] = (CheckBox) layout.findViewById(R.id.upC);
//        varup[3] = (CheckBox) layout.findViewById(R.id.upD);
//        varup[4] = (CheckBox) layout.findViewById(R.id.upS);
//        vardn[0] = (CheckBox) layout.findViewById(R.id.downA);
//        vardn[1] = (CheckBox) layout.findViewById(R.id.downB);
//        vardn[2] = (CheckBox) layout.findViewById(R.id.downC);
//        vardn[3] = (CheckBox) layout.findViewById(R.id.downD);
//        vardn[4] = (CheckBox) layout.findViewById(R.id.downS);
//        varnon = (CheckBox) layout.findViewById(R.id.non);

        // 必要V箇所の状態を格納する配列
        final int[] Vsave = new int[6];
        for (int k = 0; k < 6; k++) {
            Vsave[k] = 0;
        }

        // (性格)保存データからチェックボックスへ変換
        for (int i = 0; i < 5; i++) {
            if (ckdup[i] == 1) {
                varup[i].setChecked(true);
            }
            if (ckddn[i] == 1) {
                vardn[i].setChecked(true);
            }
        }
        if (ckdnon == 1) {
            varnon.setChecked(true);
        }

        // 受け取った値を表示
        if (catchName != null) {

            // 個体値
            for (int j = 0; j < 6; j++) {
                Vsave[j] = catchV[j];
                if (Vsave[j] == 0) {
                    statV[j].setTextColor(Color.parseColor("#696969"));
                } else if (Vsave[j] == 1) {
                    statV[j].setTextColor(Color.parseColor("#00bfff"));
                } else {
                    statV[j].setTextColor(Color.parseColor("#ff69b4"));
                }
            }

            // 性格
            Character shChara = new Character();
            Character value = shChara.setChara(varup, vardn, varnon);
            ckdup = value.ckdCup;
            ckddn = value.ckdCdn;
            ckdnon = value.ckdCnon;

//            // 子ビューを削除し結果をリセット
//            varChaLay.removeAllViews();
//
//            // 性格を表示するテキストビューにstringsから格納
//            String[] stchara = getResources().getStringArray(R.array.chara1);
//            TextView[] tvchara = new TextView[20];
//            for (int i=0;i<20;i++) {
//                tvchara[i] = new TextView(getApplication());
//                tvchara[i].setText(stchara[i]);
////                tvchara[i].setTextColor(Color.parseColor("#000000"));
//            }
//
//            // トーストのための結果の変数定義&リセット
//            // int reslut2 = 0;
//
//            // チェックに対応する性格を加算しながら表示
//            if (varup[0].isChecked()) {
//                ckdup[0] = 1;
//                if (vardn[1].isChecked()) {
//                    ckddn[1] = 1;
//                    varChaLay.addView(tvchara[0]);
//                    // reslut2 = 1;
//                }
//                if (vardn[2].isChecked()) {
//                    ckddn[2] = 1;
//                    varChaLay.addView(tvchara[1]);
//                    // reslut2 = 1;
//                }
//                if (vardn[3].isChecked()) {
//                    ckddn[3] = 1;
//                    varChaLay.addView(tvchara[2]);
//                    // reslut2 = 1;
//                }
//                if (vardn[4].isChecked()) {
//                    ckddn[4] = 1;
//                    varChaLay.addView(tvchara[3]);
//                    // reslut2 = 1;
//                }
//            }
//
//            if (varup[1].isChecked()) {
//                ckdup[1] = 1;
//                if (vardn[0].isChecked()) {
//                    ckddn[0] = 1;
//                    varChaLay.addView(tvchara[4]);
//                    // reslut2 = 1;
//                }
//                if (vardn[2].isChecked()) {
//                    ckddn[2] = 1;
//                    varChaLay.addView(tvchara[5]);
//                    // reslut2 = 1;
//                }
//                if (vardn[3].isChecked()) {
//                    ckddn[3] = 1;
//                    varChaLay.addView(tvchara[6]);
//                    // reslut2 = 1;
//                }
//                if (vardn[4].isChecked()) {
//                    ckddn[4] = 1;
//                    varChaLay.addView(tvchara[7]);
//                    // reslut2 = 1;
//                }
//            }
//
//            if (varup[2].isChecked()) {
//                ckdup[2] = 1;
//                if (vardn[0].isChecked()) {
//                    ckddn[0] = 1;
//                    varChaLay.addView(tvchara[8]);
//                    // reslut2 = 1;
//                }
//                if (vardn[1].isChecked()) {
//                    ckddn[1] = 1;;
//                    varChaLay.addView(tvchara[9]);
//                    // reslut2 = 1;
//                }
//                if (vardn[3].isChecked()) {
//                    ckddn[3] = 1;
//                    varChaLay.addView(tvchara[10]);
//                    // reslut2 = 1;
//                }
//                if (vardn[4].isChecked()) {
//                    ckddn[4] = 1;
//                    varChaLay.addView(tvchara[11]);
//                    // reslut2 = 1;
//                }
//            }
//
//            if (varup[3].isChecked()) {
//                ckdup[3] = 1;
//                if (vardn[0].isChecked()) {
//                    ckddn[0] = 1;
//                    varChaLay.addView(tvchara[12]);
//                    // reslut2 = 1;
//                }
//                if (vardn[1].isChecked()) {
//                    ckddn[1] = 1;
//                    varChaLay.addView(tvchara[13]);
//                    // reslut2 = 1;
//                }
//                if (vardn[2].isChecked()) {
//                    ckddn[2] = 1;
//                    varChaLay.addView(tvchara[14]);
//                    // reslut2 = 1;
//                }
//                if (vardn[4].isChecked()) {
//                    ckddn[4] = 1;
//                    varChaLay.addView(tvchara[15]);
//                    // reslut2 = 1;
//                }
//            }
//
//            if (varup[4].isChecked()) {
//                ckdup[4] = 1;
//                if (vardn[0].isChecked()) {
//                    ckddn[0] = 1;
//                    varChaLay.addView(tvchara[16]);
//                    // reslut2 = 1;
//                }
//                if (vardn[1].isChecked()) {
//                    ckddn[1] = 1;
//                    varChaLay.addView(tvchara[17]);
//                    // reslut2 = 1;
//                }
//                if (vardn[2].isChecked()) {
//                    ckddn[2] = 1;
//                    varChaLay.addView(tvchara[18]);
//                    // reslut2 = 1;
//                }
//                if (vardn[3].isChecked()) {
//                    ckddn[3] = 1;
//                    varChaLay.addView(tvchara[19]);
//                    // reslut2 = 1;
//                }
//            }
//
//            if (varnon.isChecked()) {
//                ckdnon = 1;
//                TextView tvnon = new TextView(getApplication());
//                tvnon.setText(getString(R.string.chanon));
//                tvnon.setTextColor(Color.parseColor("#000000"));
//                varChaLay.addView(tvnon);
//                // reslut2 = 1;
//            }
//
//            // 上がるステータスが選択されてないパターンの処理
//            for (int i=0;i<5;i++) {
//                if (vardn[i].isChecked()) {
//                    ckddn[i] = 1;
//                }
//            }
//
//            // チェック配列を整理
//            for (int i=0;i<5;i++) {
//                if (!varup[i].isChecked()) {
//                    ckdup[i] = 0;
//                }
//                if (!vardn[i].isChecked()) {
//                    ckddn[i] = 0;
//                }
//            }
//            if (!varnon.isChecked()) {
//                ckdnon = 0;
//            }
        }

        // タマゴグループ1の選択
        egg1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // ダイアログに表示するタマゴグループの種類を配列に格納
                        final String[] eggGp1 = {"陸上", "虫", "怪獣", "植物", "飛行", "人型", "妖精", "ドラゴン", "鉱物", "不定形", "水中1", "水中2", "水中3", "メタモン", "未発見"};
                        // アラートダイアログを生成
                        new AlertDialog.Builder(SecondActivity.this).setTitle(null).setItems(eggGp1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                egg1.setText(eggGp1[which]);
//                                switch (which) {
//                                    case 0:
//                                        egg1.setText("陸上");
//                                        break;
//                                    case 1:
//                                        egg1.setText("虫");
//                                        break;
//                                    case 2:
//                                        egg1.setText("怪獣");
//                                        break;
//                                    case 3:
//                                        egg1.setText("植物");
//                                        break;
//                                    case 4:
//                                        egg1.setText("飛行");
//                                        break;
//                                    case 5:
//                                        egg1.setText("人型");
//                                        break;
//                                    case 6:
//                                        egg1.setText("妖精");
//                                        break;
//                                    case 7:
//                                        egg1.setText("ドラゴン");
//                                        break;
//                                    case 8:
//                                        egg1.setText("鉱物");
//                                        break;
//                                    case 9:
//                                        egg1.setText("不定形");
//                                        break;
//                                    case 10:
//                                        egg1.setText("水中1");
//                                        break;
//                                    case 11:
//                                        egg1.setText("水中2");
//                                        break;
//                                    case 12:
//                                        egg1.setText("水中3");
//                                        break;
//                                    case 13:
//                                        egg1.setText("メタモン");
//                                        break;
//                                    case 14:
//                                        egg1.setText("未発見");
//                                        break;
//
//                                }
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
                        final String[] eggGp2 = {"-", "陸上", "虫", "怪獣", "植物", "飛行", "人型", "妖精", "ドラゴン", "鉱物", "不定形", "水中1", "水中2", "水中3", "メタモン", "未発見"};
                        // アラートダイアログを生成
                        new AlertDialog.Builder(SecondActivity.this).setTitle(null).setItems(eggGp2, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                egg2.setText(eggGp2[which]);
//                                switch (which) {
//                                    case 0:
//                                        egg2.setText("陸上");
//                                        break;
//                                    case 1:
//                                        egg2.setText("虫");
//                                        break;
//                                    case 2:
//                                        egg2.setText("怪獣");
//                                        break;
//                                    case 3:
//                                        egg2.setText("植物");
//                                        break;
//                                    case 4:
//                                        egg2.setText("飛行");
//                                        break;
//                                    case 5:
//                                        egg2.setText("人型");
//                                        break;
//                                    case 6:
//                                        egg2.setText("妖精");
//                                        break;
//                                    case 7:
//                                        egg2.setText("ドラゴン");
//                                        break;
//                                    case 8:
//                                        egg2.setText("鉱物");
//                                        break;
//                                    case 9:
//                                        egg2.setText("不定形");
//                                        break;
//                                    case 10:
//                                        egg2.setText("水中1");
//                                        break;
//                                    case 11:
//                                        egg2.setText("水中2");
//                                        break;
//                                    case 12:
//                                        egg2.setText("水中3");
//                                        break;
//                                    case 13:
//                                        egg2.setText("メタモン");
//                                        break;
//                                    case 14:
//                                        egg2.setText("未発見");
//                                        break;
//
//                                }
                            }
                        }).show();
                    }
                }
        );

        // 必要V箇所を選択
        for (int j = 0; j < 6; j++) {
            final int finalJ = j;
            statV[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Vsave[finalJ] == 0) {
                        statV[finalJ].setTextColor(Color.parseColor("#00bfff"));
                        Vsave[finalJ] = 1;
                    } else if (Vsave[finalJ] == 1) {
                        statV[finalJ].setTextColor(Color.parseColor("#ff69b4"));
                        Vsave[finalJ] = 2;
                    } else {
                        statV[finalJ].setTextColor(Color.parseColor("#696969"));
                        Vsave[finalJ] = 0;
                    }
                }
            });
        }

        // 性格候補の変更ボタンを押したときに実行
        ((Button) findViewById(R.id.charabtn)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final View layout = inflater.inflate(R.layout.chara_dlg, (ViewGroup) findViewById(R.id.layout_root));

                        // フィールドにチェックボックスのインスタンスを代入
                        varup[0] = (CheckBox) layout.findViewById(R.id.upA);
                        varup[1] = (CheckBox) layout.findViewById(R.id.upB);
                        varup[2] = (CheckBox) layout.findViewById(R.id.upC);
                        varup[3] = (CheckBox) layout.findViewById(R.id.upD);
                        varup[4] = (CheckBox) layout.findViewById(R.id.upS);
                        vardn[0] = (CheckBox) layout.findViewById(R.id.downA);
                        vardn[1] = (CheckBox) layout.findViewById(R.id.downB);
                        vardn[2] = (CheckBox) layout.findViewById(R.id.downC);
                        vardn[3] = (CheckBox) layout.findViewById(R.id.downD);
                        vardn[4] = (CheckBox) layout.findViewById(R.id.downS);
                        varnon = (CheckBox) layout.findViewById(R.id.non);

                        // チェックの状態をキープ
                        for (int i = 0; i < 5; i++) {
                            if (ckdup[i] == 1) {
                                varup[i].setChecked(true);
                            }
                            if (ckddn[i] == 1) {
                                vardn[i].setChecked(true);
                            }
                        }
                        if (ckdnon == 1) {
                            varnon.setChecked(true);
                        }

                        // アラートダイアログを生成
                        AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
                        builder.setTitle("項目をチェックしOKをタップ！");
                        builder.setView(layout);

                        // すべてチェック
                        ((Button) layout.findViewById(R.id.allset)).setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        for (int i=0;i<5;i++) {
                                            varup[i].setChecked(true);
                                            vardn[i].setChecked(true);
                                        }
                                        varnon.setChecked(true);
                                    }
                                }
                        );

                        // すべて解除
                        ((Button) layout.findViewById(R.id.reset)).setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        for (int i=0;i<5;i++) {
                                            varup[i].setChecked(false);
                                            vardn[i].setChecked(false);
                                        }
                                        varnon.setChecked(false);
                                    }
                                }
                        );

                        // ダイアログを閉じて結果を表示
                        builder.setPositiveButton(
                                "OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // 性格を表示
                                        Character shChara = new Character();
//                                        shChara.setChara(varup, vardn, varnon);
                                        Character value = shChara.setChara(varup, vardn, varnon);
                                        ckdup = value.ckdCup;
                                        ckddn = value.ckdCdn;
                                        ckdnon = value.ckdCnon;


//                                        // 子ビューを削除し結果をリセット
//                                        varChaLay.removeAllViews();
//
//                                        // 性格を表示するテキストビューにstringsから格納
//                                        String[] stchara = getResources().getStringArray(R.array.chara1);
//                                        TextView[] tvchara = new TextView[20];
//                                        for (int i=0;i<20;i++) {
//                                            tvchara[i] = new TextView(getApplication());
//                                            tvchara[i].setText(stchara[i]);
////                                            tvchara[i].setTextColor(Color.parseColor("#000000"));
//                                        }
//
//                                        // トーストのための結果の変数定義&リセット
//                                        // int reslut2 = 0;
//
//                                        // チェックに対応する性格を加算しながら表示
//                                        if (varup[0].isChecked()) {
//                                            ckdup[0] = 1;
//                                            if (vardn[1].isChecked()) {
//                                                ckddn[1] = 1;
//                                                varChaLay.addView(tvchara[0]);
//                                                // reslut2 = 1;
//                                            }
//                                            if (vardn[2].isChecked()) {
//                                                ckddn[2] = 1;
//                                                varChaLay.addView(tvchara[1]);
//                                                // reslut2 = 1;
//                                            }
//                                            if (vardn[3].isChecked()) {
//                                                ckddn[3] = 1;
//                                                varChaLay.addView(tvchara[2]);
//                                                // reslut2 = 1;
//                                            }
//                                            if (vardn[4].isChecked()) {
//                                                ckddn[4] = 1;
//                                                varChaLay.addView(tvchara[3]);
//                                                // reslut2 = 1;
//                                            }
//                                        }
//
//                                        if (varup[1].isChecked()) {
//                                            ckdup[1] = 1;
//                                            if (vardn[0].isChecked()) {
//                                                ckddn[0] = 1;
//                                                varChaLay.addView(tvchara[4]);
//                                                // reslut2 = 1;
//                                            }
//                                            if (vardn[2].isChecked()) {
//                                                ckddn[2] = 1;
//                                                varChaLay.addView(tvchara[5]);
//                                                // reslut2 = 1;
//                                            }
//                                            if (vardn[3].isChecked()) {
//                                                ckddn[3] = 1;
//                                                varChaLay.addView(tvchara[6]);
//                                                // reslut2 = 1;
//                                            }
//                                            if (vardn[4].isChecked()) {
//                                                ckddn[4] = 1;
//                                                varChaLay.addView(tvchara[7]);
//                                                // reslut2 = 1;
//                                            }
//                                        }
//
//                                        if (varup[2].isChecked()) {
//                                            ckdup[2] = 1;
//                                            if (vardn[0].isChecked()) {
//                                                ckddn[0] = 1;
//                                                varChaLay.addView(tvchara[8]);
//                                                // reslut2 = 1;
//                                            }
//                                            if (vardn[1].isChecked()) {
//                                                ckddn[1] = 1;;
//                                                varChaLay.addView(tvchara[9]);
//                                                // reslut2 = 1;
//                                            }
//                                            if (vardn[3].isChecked()) {
//                                                ckddn[3] = 1;
//                                                varChaLay.addView(tvchara[10]);
//                                                // reslut2 = 1;
//                                            }
//                                            if (vardn[4].isChecked()) {
//                                                ckddn[4] = 1;
//                                                varChaLay.addView(tvchara[11]);
//                                                // reslut2 = 1;
//                                            }
//                                        }
//
//                                        if (varup[3].isChecked()) {
//                                            ckdup[3] = 1;
//                                            if (vardn[0].isChecked()) {
//                                                ckddn[0] = 1;
//                                                varChaLay.addView(tvchara[12]);
//                                                // reslut2 = 1;
//                                            }
//                                            if (vardn[1].isChecked()) {
//                                                ckddn[1] = 1;
//                                                varChaLay.addView(tvchara[13]);
//                                                // reslut2 = 1;
//                                            }
//                                            if (vardn[2].isChecked()) {
//                                                ckddn[2] = 1;
//                                                varChaLay.addView(tvchara[14]);
//                                                // reslut2 = 1;
//                                            }
//                                            if (vardn[4].isChecked()) {
//                                                ckddn[4] = 1;
//                                                varChaLay.addView(tvchara[15]);
//                                                // reslut2 = 1;
//                                            }
//                                        }
//
//                                        if (varup[4].isChecked()) {
//                                            ckdup[4] = 1;
//                                            if (vardn[0].isChecked()) {
//                                                ckddn[0] = 1;
//                                                varChaLay.addView(tvchara[16]);
//                                                // reslut2 = 1;
//                                            }
//                                            if (vardn[1].isChecked()) {
//                                                ckddn[1] = 1;
//                                                varChaLay.addView(tvchara[17]);
//                                                // reslut2 = 1;
//                                            }
//                                            if (vardn[2].isChecked()) {
//                                                ckddn[2] = 1;
//                                                varChaLay.addView(tvchara[18]);
//                                                // reslut2 = 1;
//                                            }
//                                            if (vardn[3].isChecked()) {
//                                                ckddn[3] = 1;
//                                                varChaLay.addView(tvchara[19]);
//                                                // reslut2 = 1;
//                                            }
//                                        }
//
//                                        if (varnon.isChecked()) {
//                                            ckdnon = 1;
//                                            TextView tvnon = new TextView(getApplication());
//                                            tvnon.setText(getString(R.string.chanon));
//                                            tvnon.setTextColor(Color.parseColor("#000000"));
//                                            varChaLay.addView(tvnon);
//                                            // reslut2 = 1;
//                                        }
//
//                                        // 上がるステータスが選択されてないパターンの処理
//                                        for (int i=0;i<5;i++) {
//                                            if (vardn[i].isChecked()) {
//                                                ckddn[i] = 1;
//                                            }
//                                        }
//
//                                        // チェック配列を整理
//                                        for (int i=0;i<5;i++) {
//                                            if (!varup[i].isChecked()) {
//                                                ckdup[i] = 0;
//                                            }
//                                            if (!vardn[i].isChecked()) {
//                                                ckddn[i] = 0;
//                                            }
//                                        }
//                                        if (!varnon.isChecked()) {
//                                            ckdnon = 0;
//                                        }
                                    }
                                }
                        );

                        // キャンセル
                        builder.setNegativeButton(
                                "キャンセル",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }
                        );

                        builder.show();

                    }
                }
        );

        // 保存されているポケモン数を取得
        final long idNumber = DatabaseUtils.queryNumEntries(db, "NameTable2");

        final ContentValues insertValues = new ContentValues();
        final ContentValues insertValues2 = new ContentValues();
        final ContentValues insertValues3 = new ContentValues();

        // 保存ボタン
        findViewById(R.id.savebtn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        TrainingPokemon tr = new TrainingPokemon();
//                        tr.setPkName(pokeName.getText().toString());

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

                        insertValues3.put("upA", ckdup[0]);
                        insertValues3.put("upB", ckdup[1]);
                        insertValues3.put("upC", ckdup[2]);
                        insertValues3.put("upD", ckdup[3]);
                        insertValues3.put("upS", ckdup[4]);

                        insertValues3.put("dnA", ckddn[0]);
                        insertValues3.put("dnB", ckddn[1]);
                        insertValues3.put("dnC", ckddn[2]);
                        insertValues3.put("dnD", ckddn[3]);
                        insertValues3.put("dnS", ckddn[4]);

                        insertValues3.put("non", ckdnon);

                        if (catchName == null) {
                            insertValues.put("id", idNumber + 1);
                            insertValues2.put("id", idNumber + 1);
                            insertValues3.put("id", idNumber + 1);
                            db.insert("NameTable2", null, insertValues);
                            db.insert("Vkotai", null, insertValues2);
                            db.insert("Character", null, insertValues3);
                        } else {
                            db.update("NameTable2", insertValues, "id=" + catchId, null);
                            db.update("Vkotai", insertValues2, "id=" + catchId, null);
                            db.update("Character", insertValues3, "id=" + catchId, null);
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
                db.delete("NameTable2", "id=" + catchId, null);
                db.delete("Vkotai", "id=" + catchId, null);

                if (catchName != null) {
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

// 性格表示に関するクラス
class Character extends AppCompatActivity{

//    AppCompatActivity ACA = new AppCompatActivity();

    // チェックの状態を記録する配列
    int[] ckdCup = {0, 0, 0, 0, 0};
    int[] ckdCdn = {0, 0, 0, 0, 0};
    int ckdCnon = 0;

    // レイアウトを参照するためのフィールド
    private LinearLayout varChaLay;

    // チェックに対応する性格を表示するメソッド
    public Character setChara(CheckBox setup[], CheckBox setdn[], CheckBox setnon){

//        setContentView(R.layout.second);

        Character Chara = new Character();

        // レイアウトのインスタンス
        varChaLay = (LinearLayout) /* ACA. */ findViewById(R.id.charaLilay);

        // 子ビューを削除し結果をリセット
        varChaLay.removeAllViews();

        // 性格を表示するテキストビューにstringsから格納
        String[] stchara = /* ACA. */getResources().getStringArray(R.array.chara1);
        TextView[] tvchara = new TextView[20];
        for (int i=0;i<20;i++) {
            tvchara[i] = new TextView(/* ACA. */ this);
            tvchara[i].setText(stchara[i]);
            tvchara[i].setTextColor(Color.parseColor("#000000"));
        }

        // トーストのための結果の変数定義&リセット
        int reslut2 = 0;

        // チェックに対応する性格を加算しながら表示
        if (setup[0].isChecked()) {
            Chara.ckdCup[0] = 1;
            if (setdn[1].isChecked()) {
                Chara.ckdCdn[1] = 1;
                varChaLay.addView(tvchara[0]);
                reslut2 = 1;
            }
            if (setdn[2].isChecked()) {
                Chara.ckdCdn[2] = 1;
                varChaLay.addView(tvchara[1]);
                reslut2 = 1;
            }
            if (setdn[3].isChecked()) {
                Chara.ckdCdn[3] = 1;
                varChaLay.addView(tvchara[2]);
                reslut2 = 1;
            }
            if (setdn[4].isChecked()) {
                Chara.ckdCdn[4] = 1;
                varChaLay.addView(tvchara[3]);
                reslut2 = 1;
            }
        }

        if (setup[1].isChecked()) {
            Chara.ckdCup[1] = 1;
            if (setdn[0].isChecked()) {
                Chara.ckdCdn[0] = 1;
                varChaLay.addView(tvchara[4]);
                reslut2 = 1;
            }
            if (setdn[2].isChecked()) {
                Chara.ckdCdn[2] = 1;
                varChaLay.addView(tvchara[5]);
                reslut2 = 1;
            }
            if (setdn[3].isChecked()) {
                Chara.ckdCdn[3] = 1;
                varChaLay.addView(tvchara[6]);
                reslut2 = 1;
            }
            if (setdn[4].isChecked()) {
                Chara.ckdCdn[4] = 1;
                varChaLay.addView(tvchara[7]);
                reslut2 = 1;
            }
        }

        if (setup[2].isChecked()) {
            Chara.ckdCup[2] = 1;
            if (setdn[0].isChecked()) {
                Chara.ckdCdn[0] = 1;
                varChaLay.addView(tvchara[8]);
                reslut2 = 1;
            }
            if (setdn[1].isChecked()) {
                Chara.ckdCdn[1] = 1;;
                varChaLay.addView(tvchara[9]);
                reslut2 = 1;
            }
            if (setdn[3].isChecked()) {
                Chara.ckdCdn[3] = 1;
                varChaLay.addView(tvchara[10]);
                reslut2 = 1;
            }
            if (setdn[4].isChecked()) {
                Chara.ckdCdn[4] = 1;
                varChaLay.addView(tvchara[11]);
                reslut2 = 1;
            }
        }

        if (setup[3].isChecked()) {
            Chara.ckdCup[3] = 1;
            if (setdn[0].isChecked()) {
                Chara.ckdCdn[0] = 1;
                varChaLay.addView(tvchara[12]);
                reslut2 = 1;
            }
            if (setdn[1].isChecked()) {
                Chara.ckdCdn[1] = 1;
                varChaLay.addView(tvchara[13]);
                reslut2 = 1;
            }
            if (setdn[2].isChecked()) {
                Chara.ckdCdn[2] = 1;
                varChaLay.addView(tvchara[14]);
                reslut2 = 1;
            }
            if (setdn[4].isChecked()) {
                Chara.ckdCdn[4] = 1;
                varChaLay.addView(tvchara[15]);
                reslut2 = 1;
            }
        }

        if (setup[4].isChecked()) {
            Chara.ckdCup[4] = 1;
            if (setdn[0].isChecked()) {
                Chara.ckdCdn[0] = 1;
                varChaLay.addView(tvchara[16]);
                reslut2 = 1;
            }
            if (setdn[1].isChecked()) {
                Chara.ckdCdn[1] = 1;
                varChaLay.addView(tvchara[17]);
                reslut2 = 1;
            }
            if (setdn[2].isChecked()) {
                Chara.ckdCdn[2] = 1;
                varChaLay.addView(tvchara[18]);
                reslut2 = 1;
            }
            if (setdn[3].isChecked()) {
                Chara.ckdCdn[3] = 1;
                varChaLay.addView(tvchara[19]);
                reslut2 = 1;
            }
        }

        if (setnon.isChecked()) {
            Chara.ckdCnon = 1;
            TextView tvnon = new TextView(/* ACA. */ this);
            tvnon.setText(/* ACA. */ getString(R.string.chanon));
            tvnon.setTextColor(Color.parseColor("#000000"));
            varChaLay.addView(tvnon);
            reslut2 = 1;
        }

        // 上がるステータスが選択されてないパターンの処理
        for (int i=0;i<5;i++) {
            if (setdn[i].isChecked()) {
                Chara.ckdCdn[i] = 1;
            }
        }

        // チェック配列を整理
        for (int i=0;i<5;i++) {
            if (!setup[i].isChecked()) {
                Chara.ckdCup[i] = 0;
            }
            if (!setdn[i].isChecked()) {
                Chara.ckdCdn[i] = 0;
            }
        }
        if (!setnon.isChecked()) {
            Chara.ckdCnon = 0;
        }

        return Chara;
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