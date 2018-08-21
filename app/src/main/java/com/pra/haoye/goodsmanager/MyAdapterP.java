package com.pra.haoye.goodsmanager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.File;
import java.util.List;


import static com.pra.haoye.goodsmanager.Position_LIst.calculateInSampleSize;

public class MyAdapterP extends BaseAdapter {
    private LayoutInflater myInflater;
    private List<Position_item> PI;
    private Context context;

    public MyAdapterP(Context context,List<Position_item> PI) {
        myInflater = LayoutInflater.from(context);
        this.context = context;
        this.PI=PI;
    }
    @Override
    public int getCount(){
        return PI.size();
    }
    @Override
    public Object getItem(int arg0){
        return PI.get(arg0);
    }
    @Override
    public long getItemId(int position){
        return PI.indexOf(getItem(position));
    }
    private class viewHolder{
        TextView TPN;
        TextView TPR;
        ImageView TPImg;
        public viewHolder(TextView TPN,TextView TPR,ImageView TPImg){
            this.TPN = TPN;
            this.TPR = TPR;
            this.TPImg = TPImg;
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        viewHolder holder = null;
        Position_item P = (Position_item)getItem(position);
        if(convertView == null){
            convertView = myInflater.inflate(R.layout.position_listview,null);
            holder = new viewHolder(
                    (TextView) convertView.findViewById(R.id.position_listview),
                    (TextView) convertView.findViewById(R.id.position_range),
                    (ImageView) convertView.findViewById(R.id.position_img)
            );
             convertView.setTag(holder);
        }
        else{
            holder = (viewHolder) convertView.getTag();
        }
        holder.TPN.setText(P.getPositionname()+"\n"+P.getUpposition()+"\n"+P.getimgpath());
        holder.TPR.setText("RangeX1="+P.getRange()[0]+"\n"+
                            "RangeY1="+P.getRange()[1]+"\n"+
                            "RangeX2="+P.getRange()[2]+"\n"+
                            "RangeY2="+P.getRange()[3]+"\n"+
                            "NodeX="+P.getNode()[0]+"\n"+
                            "NodeY="+P.getNode()[1]);
        getimage(P,holder);
        return convertView;
    }
    private void getimage(final Position_item P,final viewHolder holder){
        new Thread(new Runnable() {
            Bitmap bm;
            Boolean End = false;
            @Override
            public void run() {
                File file2 = new File(P.getimgpath());
                if(file2.exists()){
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds =true;
                    BitmapFactory.decodeFile(P.getimgpath());
                    options.inSampleSize=calculateInSampleSize(options,holder.TPImg.getWidth(),holder.TPImg.getHeight());
                    options.inJustDecodeBounds = false;
                    bm = BitmapFactory.decodeFile(P.getimgpath(),options);
                }
                else End = true;
                ((Position_Search)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(End) {
                            holder.TPImg.setImageResource(android.R.color.transparent);
                        }
                        else{
                            holder.TPImg.setImageBitmap(bm);
                        }
                    }
                });
            }
        }).start();
    }
}
