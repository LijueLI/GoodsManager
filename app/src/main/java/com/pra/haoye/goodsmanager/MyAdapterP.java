package com.pra.haoye.goodsmanager;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.io.File;
import java.util.List;


import static com.pra.haoye.goodsmanager.Position_LIst.calculateInSampleSize;

public class MyAdapterP extends BaseAdapter {
    private LayoutInflater myInflater;
    private List<Position_item> PI;
    private Context context;
    private int scrollstatus = 0;

    public MyAdapterP(Context context, List<Position_item> PI, ListView lv) {
        myInflater = LayoutInflater.from(context);
        this.context = context;
        this.PI=PI;

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i){
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        MyAdapterP.this.notifyDataSetChanged();
                        scrollstatus = 0;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
                        scrollstatus = 1;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        scrollstatus = 2;
                        break;
                }
            }
            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
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
        private viewHolder(TextView TPN,TextView TPR,ImageView TPImg){
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
        if(scrollstatus == 0) {
            holder.TPN.setText(P.getPositionname() + "\n" + P.getUpposition() + "\n" + P.getimgpath());
            holder.TPR.setText("RangeX1=" + P.getRange()[0] + "\n" +
                    "RangeY1=" + P.getRange()[1] + "\n" +
                    "RangeX2=" + P.getRange()[2] + "\n" +
                    "RangeY2=" + P.getRange()[3] + "\n" +
                    "NodeX=" + P.getNode()[0] + "\n" +
                    "NodeY=" + P.getNode()[1]);
            getimage(P, holder);
        }
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
