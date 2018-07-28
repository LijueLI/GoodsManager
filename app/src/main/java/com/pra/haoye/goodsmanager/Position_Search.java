package com.pra.haoye.goodsmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Position_Search extends AppCompatActivity {
    private int IntF;
    private ListView PositionlistV;
    private List<Position_item> Position_listvt = new ArrayList<Position_item>();
    private MyAdapterP adapter;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position__search);
        setTitle("位置搜尋與選擇");
        intent = getIntent();
        IntF = intent.getIntExtra("PL",0);
        PositionlistV=findViewById(R.id.PS_LV);

        MyDBHelper Postion_item = new MyDBHelper(this);
        Cursor cursor = Postion_item.select();
        while(cursor.moveToNext()){
            Position_item P = new Position_item(cursor.getString(cursor.getColumnIndexOrThrow("_PositionName")),
                                                cursor.getString(cursor.getColumnIndexOrThrow("_Upposition")),
                                                cursor.getString(cursor.getColumnIndexOrThrow("_Imgpath")),
                                                cursor.getInt(cursor.getColumnIndexOrThrow("_RangeX1")),
                                                cursor.getInt(cursor.getColumnIndexOrThrow("_RangeY1")),
                                                cursor.getInt(cursor.getColumnIndexOrThrow("_RangeX2")),
                                                cursor.getInt(cursor.getColumnIndexOrThrow("_RangeY2")),
                                                cursor.getInt(cursor.getColumnIndexOrThrow("_NodeX")),
                                                cursor.getInt(cursor.getColumnIndexOrThrow("_NodeY")));
            Position_listvt.add(P);
        }
        cursor.close();
        adapter = new MyAdapterP(Position_Search.this,Position_listvt);
        PositionlistV.setAdapter(adapter);
    }
}
