package com.pra.haoye.goodsmanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;


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
        ImageView TPImg;
        private viewHolder(TextView TPN,ImageView TPImg){
            this.TPN = TPN;
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
                    (ImageView) convertView.findViewById(R.id.position_img)
            );
             convertView.setTag(holder);
        }
        else{
            holder = (viewHolder) convertView.getTag();
        }
        if(scrollstatus == 0) {
            holder.TPN.setText("位置名稱 : "+P.getPositionname() + "\n" +"上層位置 : " + P.getUpposition() + "\n");
            getimage(P, holder);
        }
        else if(scrollstatus == 2)
        {
            holder.TPImg.setImageResource(android.R.color.white);
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
                WeakReference<File> fileWeakReference = new WeakReference<>(file2);
                if(file2.exists()){
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    WeakReference<BitmapFactory.Options> optionsWeakReference = new WeakReference<>(options);
                    options.inJustDecodeBounds =true;
                    BitmapFactory.decodeFile(P.getimgpath(),options);
                    options.inSampleSize=calculateInSampleSize(optionsWeakReference,holder.TPImg.getWidth(),holder.TPImg.getHeight());
                    options.inJustDecodeBounds = false;
                    bm = BitmapFactory.decodeFile(P.getimgpath(),options);
                    options = null;
                    System.gc();
                    int Rotate = P.getRotate();
                    for(int i =0 ;i<Rotate;i++){
                        /* 建立 Matrix 物件 */
                        Matrix matrix = new Matrix();
                        /* 設定旋轉角度 */
                        matrix.postRotate(90);
                        /* 用原來的 Bitmap 產生一個新的 Bitmap */
                        Bitmap bmp = bm;
                        bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
                        bmp.recycle();
                        System.gc();
                    }
                }
                else End = true;
                file2 = null;
                ((Position_Search)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(End) {
                            holder.TPImg.setImageResource(android.R.color.white);
                        }
                        else{
                            holder.TPImg.setImageBitmap(bm);
                        }
                        MyAdapterP.this.notifyDataSetChanged();
                    }
                });
            }
        }).start();
        System.gc();
    }
    public int calculateInSampleSize(WeakReference<BitmapFactory.Options> optionsWeakReference, int reqWidth, int reqHeight){

        final int height = optionsWeakReference.get().outHeight;
        final int width = optionsWeakReference.get().outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
