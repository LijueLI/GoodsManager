package com.pra.haoye.goodsmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PositionADD extends AppCompatActivity {
    private Button positionADD;
    private EditText PositionName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_add);
        setTitle("新增位置");

        positionADD = findViewById(R.id.ADD);
        positionADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PositionName = findViewById(R.id.PositionName);
                MyDBHelper Postion_item = new MyDBHelper(PositionADD.this);
                Postion_item.insert(PositionName.getText().toString(),"","",0,0,0,0,0,0);
                finishActivity(0);
            }
        });
    }
}
