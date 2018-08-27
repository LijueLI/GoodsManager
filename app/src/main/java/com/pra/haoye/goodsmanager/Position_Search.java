package com.pra.haoye.goodsmanager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

public class Position_Search extends AppCompatActivity {
    private int getId , ERF;
    private EditText ST;
    private ListView PositionlistV;
    private List<Position_item> Position_listvt = new ArrayList<Position_item>();
    private MyAdapterP adapter;
    private Intent intent;
    private Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position__search);
        setTitle("位置搜尋與選擇");
        intent = getIntent();
        ERF=intent.getIntExtra("ERF",0);
        PositionlistV=findViewById(R.id.PS_LV);
        ST = findViewById(R.id.PS_Searchtext);

        MyDBHelper Postion_item = new MyDBHelper(this);
        Cursor cursor = Postion_item.select();
        while(cursor.moveToNext()){
            Position_item P = new Position_item(cursor.getString(cursor.getColumnIndexOrThrow("_PositionName")),
                                                cursor.getString(cursor.getColumnIndexOrThrow("_Upposition")),
                                                cursor.getString(cursor.getColumnIndexOrThrow("_Imgpath")),
                                                cursor.getFloat(cursor.getColumnIndexOrThrow("_RangeX1")),
                                                cursor.getFloat(cursor.getColumnIndexOrThrow("_RangeY1")),
                                                cursor.getFloat(cursor.getColumnIndexOrThrow("_RangeX2")),
                                                cursor.getFloat(cursor.getColumnIndexOrThrow("_RangeY2")),
                                                cursor.getInt(cursor.getColumnIndexOrThrow("_NodeX")),
                                                cursor.getInt(cursor.getColumnIndexOrThrow("_NodeY")),
                                                cursor.getInt(cursor.getColumnIndexOrThrow("_Rotate")));
            P.setID(cursor.getInt((cursor.getColumnIndexOrThrow("_id"))));
            Position_listvt.add(P);
        }
        cursor.close();
        Postion_item.close();
        adapter = new MyAdapterP(Position_Search.this,Position_listvt);
        PositionlistV.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        search = findViewById(R.id.PS_Position_search);

        PositionlistV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                getId = Position_listvt.get(i).getID();
                switch (ERF) {
                    case 1:
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putInt("_ID",getId);
                        intent.putExtras(bundle);
                        setResult(1, intent);
                        finish();
                        break;
                    case 3:
                        Intent intent3 = new Intent();
                        Bundle bundle3 = new Bundle();
                        bundle3.putInt("_ID",getId);
                        intent3.putExtras(bundle3);
                        setResult(3, intent3);
                        finish();
                        break;
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ST.getText().toString().equals("")){
                    List<Position_item> PLI2 = new ArrayList<Position_item>();
                    MyDBHelper Postion_item = new MyDBHelper(Position_Search.this);
                    Cursor cursor = Postion_item.select();
                    while(cursor.moveToNext()){
                        Position_item P = new Position_item(cursor.getString(cursor.getColumnIndexOrThrow("_PositionName")),
                                cursor.getString(cursor.getColumnIndexOrThrow("_Upposition")),
                                cursor.getString(cursor.getColumnIndexOrThrow("_Imgpath")),
                                cursor.getFloat(cursor.getColumnIndexOrThrow("_RangeX1")),
                                cursor.getFloat(cursor.getColumnIndexOrThrow("_RangeY1")),
                                cursor.getFloat(cursor.getColumnIndexOrThrow("_RangeX2")),
                                cursor.getFloat(cursor.getColumnIndexOrThrow("_RangeY2")),
                                cursor.getInt(cursor.getColumnIndexOrThrow("_NodeX")),
                                cursor.getInt(cursor.getColumnIndexOrThrow("_NodeY")),
                                cursor.getInt(cursor.getColumnIndexOrThrow("_Rotate")));
                        P.setID(cursor.getInt((cursor.getColumnIndexOrThrow("_id"))));
                        PLI2.add(P);
                    }
                    cursor.close();
                    Postion_item.close();
                    Position_listvt.clear();
                    Position_listvt.addAll(PLI2);
                }
                else{
                    List<Position_item> PLI = new ArrayList<Position_item>();
                    MyDBHelper Postion_item = new MyDBHelper(Position_Search.this);
                    Cursor cursor = Postion_item.selectfromPosition_name(ST.getText().toString());
                    while(cursor.moveToNext()){
                        Position_item P = new Position_item(cursor.getString(cursor.getColumnIndexOrThrow("_PositionName")),
                                cursor.getString(cursor.getColumnIndexOrThrow("_Upposition")),
                                cursor.getString(cursor.getColumnIndexOrThrow("_Imgpath")),
                                cursor.getFloat(cursor.getColumnIndexOrThrow("_RangeX1")),
                                cursor.getFloat(cursor.getColumnIndexOrThrow("_RangeY1")),
                                cursor.getFloat(cursor.getColumnIndexOrThrow("_RangeX2")),
                                cursor.getFloat(cursor.getColumnIndexOrThrow("_RangeY2")),
                                cursor.getInt(cursor.getColumnIndexOrThrow("_NodeX")),
                                cursor.getInt(cursor.getColumnIndexOrThrow("_NodeY")),
                                cursor.getInt(cursor.getColumnIndexOrThrow("_Rotate")));
                        P.setID(cursor.getInt((cursor.getColumnIndexOrThrow("_id"))));
                        PLI.add(P);
                    }
                    cursor.close();
                    Postion_item.close();
                    Position_listvt.clear();
                    Position_listvt.addAll(PLI);
                }
                adapter.notifyDataSetChanged();
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = LeakCanaryApplication.getRefWatcher(this);//1
        refWatcher.watch(this);
    }
}
