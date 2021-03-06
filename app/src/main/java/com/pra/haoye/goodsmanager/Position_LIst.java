package com.pra.haoye.goodsmanager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class Position_LIst extends Fragment {
    private Button update,add,delete,choose,imgchoose,clear,Upposition_choose;
    private EditText PositionName,Upposition,RangeX1,RangeX2,RangeY1,RangeY2,NodeX,NodeY;
    private ImageView img;
    private Intent Position_search,Img_choose;
    private String Imgpath = "";
    private int ID,Rotate = 0;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("位置資訊");

        update = getView().findViewById(R.id.PL_update);
        add = getView().findViewById(R.id.PL_add);
        delete = getView().findViewById(R.id.PL_delete);
        choose = getView().findViewById(R.id.PL_choose);
        clear = getView().findViewById(R.id.PL_Clear);
        imgchoose = getView().findViewById(R.id.PL_imgchoose);
        Upposition_choose = getView().findViewById(R.id.PL_Upposition_choose);
        PositionName = getView().findViewById(R.id.PL_PositionName);
        Upposition = getView().findViewById(R.id.PL_Upposition);
        RangeX1 = getView().findViewById(R.id.PL_RX);
        RangeX2 = getView().findViewById(R.id.PL_RX2);
        RangeY1 = getView().findViewById(R.id.PL_RY);
        RangeY2 = getView().findViewById(R.id.PL_RY2);
        NodeX = getView().findViewById(R.id.PL_NDX);
        NodeY = getView().findViewById(R.id.PL_NDY);
        img = getView().findViewById(R.id.PL_img);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDBHelper Postion_item = new MyDBHelper(getActivity());
                if("".equals(PositionName.getText().toString().trim())){
                    Toast.makeText(getActivity(), "欄目不得為空", Toast.LENGTH_SHORT).show();
                }
                else {
                    long ER = Postion_item.insert(PositionName.getText().toString(),
                            Upposition.getText().toString(),
                            Imgpath,
                            Float.parseFloat(RangeX1.getText().toString()),
                            Float.parseFloat(RangeY1.getText().toString()),
                            Float.parseFloat(RangeX2.getText().toString()),
                            Float.parseFloat(RangeY2.getText().toString()),
                            Integer.parseInt(NodeX.getText().toString()),
                            Integer.parseInt(NodeY.getText().toString()),
                            Rotate);
                    if (ER == -1) {
                        Toast Err = Toast.makeText(getActivity(), "新增失敗", Toast.LENGTH_LONG);
                        Err.show();
                    } else {
                        Toast OK = Toast.makeText(getActivity(), "新增成功", Toast.LENGTH_LONG);
                        PositionName.setText("");
                        Upposition.setText("");
                        RangeX1.setText("0");
                        RangeY1.setText("0");
                        RangeX2.setText("0");
                        RangeY2.setText("0");
                        NodeX.setText("0");
                        NodeY.setText("0");
                        img.setImageResource(android.R.color.transparent);
                        Imgpath = "";
                        Rotate = 0;
                        delete.setEnabled(false);
                        update.setEnabled(false);
                        OK.show();
                    }
                    Postion_item.close();
                }
            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Position_search = new Intent(getActivity(),Position_Search.class);
                Position_search.putExtra("ERF",1);
                startActivityForResult(Position_search,1);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDBHelper Postion_item = new MyDBHelper(getContext());
                Postion_item.delete(ID);
                Toast OK = Toast.makeText(getActivity(), "刪除成功", Toast.LENGTH_LONG);
                PositionName.setText("");
                Upposition.setText("");
                RangeX1.setText("0");
                RangeY1.setText("0");
                RangeX2.setText("0");
                RangeY2.setText("0");
                NodeX.setText("0");
                NodeY.setText("0");
                img.setImageResource(android.R.color.transparent);
                Imgpath = "";
                Rotate = 0;
                delete.setEnabled(false);
                update.setEnabled(false);
                OK.show();
                Postion_item.close();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDBHelper Postion_item = new MyDBHelper(getContext());
                long ERRU=Postion_item.update(PositionName.getText().toString(),
                        Upposition.getText().toString(),
                        Imgpath,
                        Float.parseFloat(RangeX1.getText().toString()),
                        Float.parseFloat(RangeY1.getText().toString()),
                        Float.parseFloat(RangeX2.getText().toString()),
                        Float.parseFloat(RangeY2.getText().toString()),
                        Integer.parseInt(NodeX.getText().toString()),
                        Integer.parseInt(NodeY.getText().toString()),
                        Rotate,
                        ID);
                if(ERRU != 0) {
                    Toast OK = Toast.makeText(getActivity(), "更新成功", Toast.LENGTH_LONG);
                    PositionName.setText("");
                    Upposition.setText("");
                    RangeX1.setText("0");
                    RangeY1.setText("0");
                    RangeX2.setText("0");
                    RangeY2.setText("0");
                    NodeX.setText("0");
                    NodeY.setText("0");
                    img.setImageResource(android.R.color.transparent);
                    Imgpath = "";
                    Rotate = 0;
                    delete.setEnabled(false);
                    update.setEnabled(false);
                    OK.show();
                    Postion_item.close();
                }
                else{
                    Toast.makeText(getActivity(),"更新失敗",Toast.LENGTH_LONG).show();
                }
            }
        });

        imgchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Img_choose = new Intent(getActivity(),Image_Choose.class);
                if(!Imgpath.equals("")) Img_choose.putExtra("Imgpath",Imgpath);
                else Img_choose.putExtra("Imgpath","null");
                if(!"".equals(RangeX1.getText())){
                    Img_choose.putExtra("RangeX1",Float.parseFloat(RangeX1.getText().toString()));
                    Img_choose.putExtra("RangeY1",Float.parseFloat(RangeY1.getText().toString()));
                    Img_choose.putExtra("RangeX2",Float.parseFloat(RangeX2.getText().toString()));
                    Img_choose.putExtra("RangeY2",Float.parseFloat(RangeY2.getText().toString()));
                    Img_choose.putExtra("Rotate",Rotate);
                }
                startActivityForResult(Img_choose,2);
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PositionName.setText("");
                Upposition.setText("");
                RangeX1.setText("0");
                RangeY1.setText("0");
                RangeX2.setText("0");
                RangeY2.setText("0");
                NodeX.setText("0");
                NodeY.setText("0");
                img.setImageResource(android.R.color.transparent);
                Imgpath = "";
                Rotate = 0;
                update.setEnabled(false);
                delete.setEnabled(false);
            }
        });

        Upposition_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Position_search = new Intent(getActivity(),Position_Search.class);
                Position_search.putExtra("ERF",3);
                startActivityForResult(Position_search,3);
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.position_list,container,false);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 1:
                ID = data.getExtras().getInt("_ID",0);
                Log.e("ID",Integer.toString(ID));
                MyDBHelper Postion_item = new MyDBHelper(getContext());
                Cursor cursor = Postion_item.selectfromID(ID);
                cursor.moveToFirst();
                PositionName.setText(cursor.getString(cursor.getColumnIndexOrThrow("_PositionName")));
                Upposition.setText(cursor.getString(cursor.getColumnIndexOrThrow("_Upposition")));
                RangeX1.setText(Float.toString(cursor.getFloat(cursor.getColumnIndexOrThrow("_RangeX1"))));
                RangeY1.setText(Float.toString(cursor.getFloat(cursor.getColumnIndexOrThrow("_RangeY1"))));
                RangeX2.setText(Float.toString(cursor.getFloat(cursor.getColumnIndexOrThrow("_RangeX2"))));
                RangeY2.setText(Float.toString(cursor.getFloat(cursor.getColumnIndexOrThrow("_RangeY2"))));
                NodeX.setText(Integer.toString(cursor.getInt(cursor.getColumnIndexOrThrow("_NodeX"))));
                NodeY.setText(Integer.toString(cursor.getInt(cursor.getColumnIndexOrThrow("_NodeY"))));
                Imgpath = cursor.getString(cursor.getColumnIndexOrThrow("_Imgpath"));
                Rotate = cursor.getInt(cursor.getColumnIndexOrThrow("_Rotate"));
                cursor.close();
                Postion_item.close();
                File file1 = new File(Imgpath);
                if(file1.exists()){
                    Bitmap bm = BitmapFactory.decodeFile(Imgpath);
                    img.setImageBitmap(bm);
                }
                else{
                    img.setImageResource(android.R.color.transparent);
                }
                delete.setEnabled(true);
                update.setEnabled(true);
                break;
            case 2:
                Imgpath = data.getStringExtra("imgpath");
                File file2 = new File(Imgpath);
                if(file2.exists()){
                    Bitmap bm = BitmapFactory.decodeFile(Imgpath);
                    RangeX1.setText(Float.toString(data.getFloatExtra("RangeX1",0)));
                    RangeY1.setText(Float.toString(data.getFloatExtra("RangeY1",0)));
                    RangeX2.setText(Float.toString(data.getFloatExtra("RangeX2",0)));
                    RangeY2.setText(Float.toString(data.getFloatExtra("RangeY2",0)));
                    Rotate = data.getIntExtra("Rotate",0);
                    img.setImageBitmap(bm);
                }
                else{
                    img.setImageResource(android.R.color.transparent);
                }
                break;
            case 3:
                int id = data.getExtras().getInt("_ID",0);
                MyDBHelper Postion_item3 = new MyDBHelper(getContext());
                Cursor cursor3 = Postion_item3.selectfromID(id);
                cursor3.moveToFirst();
                Upposition.setText(cursor3.getString(cursor3.getColumnIndexOrThrow("_PositionName")));
                cursor3.close();
                Postion_item3.close();
                break;
            default:
                break;
        }
    }
}
