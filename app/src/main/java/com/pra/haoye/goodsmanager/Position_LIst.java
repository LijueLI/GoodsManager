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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Position_LIst extends Fragment {
    private ListView positionlistV;
    private Button update,add,delete;
    List<Position_LIst> positioninfo = new ArrayList<Position_LIst>();
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("位置列表");

        positionlistV = getActivity().findViewById(R.id.positionV);
        update = getActivity().findViewById(R.id.update);
        add = getActivity().findViewById(R.id.add);
        delete = getActivity().findViewById(R.id.delete);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),PositionADD.class);
                startActivity(intent);
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.position_list,container,false);
    }
}
