package com.pra.haoye.goodsmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PositionADD extends AppCompatActivity {
    private Button positionADD,positionCancel;
    private EditText PositionName,Upposition,RangeX1,RangeX2,RangeY1,RangeY2,NodeX,NodeY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_add);
        setTitle("新增位置");

        PositionName = findViewById(R.id.PA_PositionName);
        Upposition = findViewById(R.id.PA_Upposition);
        RangeX1 = findViewById(R.id.PA_RX1);
        RangeX2 = findViewById(R.id.PA_RX2);
        RangeY1 = findViewById(R.id.PA_RY1);
        RangeY2 = findViewById(R.id.PA_RY2);
        NodeX = findViewById(R.id.PA_NDX);
        NodeY = findViewById(R.id.PA_NDY);

        positionADD = findViewById(R.id.PA_Add);
        positionADD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDBHelper Postion_item = new MyDBHelper(PositionADD.this);
                Postion_item.insert(PositionName.getText().toString(),Upposition.getText().toString(),
                                    "",
                                    Integer.parseInt(RangeX1.getText().toString()),
                                    Integer.parseInt(RangeY1.getText().toString()),
                                    Integer.parseInt(RangeX2.getText().toString()),
                                    Integer.parseInt(RangeY2.getText().toString()),
                                    Integer.parseInt(NodeX.getText().toString()),
                                    Integer.parseInt(NodeY.getText().toString()));
                Postion_item.close();
                finish();
            }
        });
        positionCancel = findViewById(R.id.PA_Cancel);
        positionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
