package com.pra.haoye.goodsmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Position_LIst extends Fragment {
    private Button update,add,delete,choose;
    private EditText PositionName,Upposition,RangeX1,RangeX2,RangeY1,RangeY2,NodeX,NodeY;
    private Intent Position_search;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("位置資訊");

        update = getActivity().findViewById(R.id.PL_update);
        add = getActivity().findViewById(R.id.PL_add);
        delete = getActivity().findViewById(R.id.PL_delete);
        choose = getActivity().findViewById(R.id.PL_choose);
        PositionName = getActivity().findViewById(R.id.PL_PositionName);
        Upposition = getActivity().findViewById(R.id.PL_Upposition);
        RangeX1 = getActivity().findViewById(R.id.PL_RX);
        RangeX2 = getActivity().findViewById(R.id.PL_RX2);
        RangeY1 = getActivity().findViewById(R.id.PL_RY);
        RangeY2 = getActivity().findViewById(R.id.PL_RY2);
        NodeX = getActivity().findViewById(R.id.PL_NDX);
        NodeY = getActivity().findViewById(R.id.PL_NDY);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDBHelper Postion_item = new MyDBHelper(getActivity());
                long ER=Postion_item.insert(PositionName.getText().toString(),Upposition.getText().toString(),
                        "",
                        Integer.parseInt(RangeX1.getText().toString()),
                        Integer.parseInt(RangeY1.getText().toString()),
                        Integer.parseInt(RangeX2.getText().toString()),
                        Integer.parseInt(RangeY2.getText().toString()),
                        Integer.parseInt(NodeX.getText().toString()),
                        Integer.parseInt(NodeY.getText().toString()));
                if(ER == -1) {
                    Toast Err = Toast.makeText(getActivity(),"新增失敗", Toast.LENGTH_LONG);
                    Err.show();
                }
                Postion_item.close();
            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Position_search = new Intent(getActivity(),Position_Search.class);
                Position_search.putExtra("PL",1);
                startActivity(Position_search);
            }
        });

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.position_list,container,false);
    }
}
