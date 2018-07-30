package com.pra.haoye.goodsmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Position_Search extends AppCompatActivity {
    private int getId , ERF;
    private ListView PositionlistV;
    private List<Position_item> Position_listvt = new ArrayList<Position_item>();
    private MyAdapterP adapter;
    private Intent intent;
    private Button choose;
    private View viewF = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position__search);
        setTitle("位置搜尋與選擇");
        intent = getIntent();
        ERF=intent.getIntExtra("ERF",0);
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
            P.setID(cursor.getInt((cursor.getColumnIndexOrThrow("_id"))));
            Position_listvt.add(P);
        }
        cursor.close();
        adapter = new MyAdapterP(Position_Search.this,Position_listvt);
        PositionlistV.setAdapter(adapter);
        choose = findViewById(R.id.PS_Position_choose);

        PositionlistV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(viewF != view || viewF==null){
                    if(viewF != null) viewF.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    view.setBackgroundColor(Color.parseColor("#9E9E9E"));
                    viewF = view;
                    choose.setEnabled(true);
                    getId = Position_listvt.get(i).getID();
                    Log.e("getID",Integer.toString(getId));
                }
                else {
                    viewF.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    viewF = null;
                    choose.setEnabled(false);
                }
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (ERF) {
                    case 1:
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putInt("_ID",getId);
                        intent.putExtras(bundle);
                        setResult(1, intent);
                        finish();
                        break;
                }
            }
        });
    }
}
