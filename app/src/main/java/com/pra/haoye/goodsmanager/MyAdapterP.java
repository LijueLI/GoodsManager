package com.pra.haoye.goodsmanager;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
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
    private List<Position_item> PI;
    private WeakReference<Context> contextWeakReference;

    public MyAdapterP(Context context, List<Position_item> PI) {
        contextWeakReference = new WeakReference<Context>(context);
        this.PI = PI;
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
        public viewHolder(TextView TPN,ImageView TPImg){
            this.TPN = TPN;
            this.TPImg = TPImg;
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view;
        viewHolder holder = null;
        Position_item P = (Position_item)getItem(position);
        if(convertView == null){
            view = LayoutInflater.from(contextWeakReference.get()).inflate(R.layout.position_listview,parent,false);
            holder = new viewHolder(
                    (TextView) view.findViewById(R.id.position_listview),
                    (ImageView) view.findViewById(R.id.position_img)
            );
             view.setTag(holder);
        }
        else{
            view = convertView;
            holder = (viewHolder) convertView.getTag();
        }
        holder.TPN.setText("位置名稱 : " + P.getPositionname() + "\n" + "上層位置 : " + P.getUpposition() + "\n");
        TaskItem T = new TaskItem(holder.TPImg.getLayoutParams().width, holder.TPImg.getLayoutParams().height, P.getRotate(), P.getimgpath());
        loadBitmap(T, holder.TPImg);
        Log.d("tete","done");
        return view;
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

    class BitmapWorktask extends AsyncTask<TaskItem,Void,Bitmap>{
        private final WeakReference<ImageView> imageViewWeakReference;
        TaskItem TI = null;

        public BitmapWorktask(ImageView imageView){
            imageViewWeakReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(TaskItem... TIs){
            TI = TIs[0];
            BitmapFactory.Options options = new BitmapFactory.Options();
            WeakReference<BitmapFactory.Options> optionsWeakReference = new WeakReference<>(options);
            options.inJustDecodeBounds =true;
            BitmapFactory.decodeFile(TI.getPath(),options);
            options.inSampleSize=calculateInSampleSize(optionsWeakReference,TI.getReqWidth(),TI.getReqHeight());
            options.inJustDecodeBounds = false;
            Bitmap bm = BitmapFactory.decodeFile(TI.getPath(),options);
            options = null;

            int Rotate = TI.getRotate();
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
            return bm;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            if (isCancelled()) {
                bitmap = null;
            }
            if (imageViewWeakReference != null && bitmap != null) {
                final ImageView imageView = imageViewWeakReference.get();
                final BitmapWorktask bitmapWorkerTask =
                        getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorktask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap,
                             BitmapWorktask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference =
                    new WeakReference<BitmapWorktask>(bitmapWorkerTask);
        }

        public BitmapWorktask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }

    public void loadBitmap(TaskItem T, ImageView imageView) {
        if (cancelPotentialWork(T, imageView)) {
            final BitmapWorktask task = new BitmapWorktask(imageView);
            final AsyncDrawable asyncDrawable =
                    new AsyncDrawable(contextWeakReference.get().getResources(),null, task);
            imageView.setImageDrawable(asyncDrawable);
            File file2 = new File(T.getPath());
            WeakReference<File> fileWeakReference = new WeakReference<>(file2);
            if(file2.exists()){
                task.execute(T);
            }
            file2 =null;
            System.gc();
        }
    }

    public static boolean cancelPotentialWork(TaskItem T, ImageView imageView) {
        final BitmapWorktask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final TaskItem TI = bitmapWorkerTask.TI;
            if (TI!=T) {
                // 取消之前的任务
                bitmapWorkerTask.cancel(true);
            } else {
                // 相同任务已经存在，直接返回false，不再进行重复的加载
                return false;
            }
        }
        // 没有Task和ImageView进行绑定，或者Task由于加载资源不同而被取消，返回true
        return true;
    }

    private static BitmapWorktask getBitmapWorkerTask(ImageView imageView) {
        if (imageView != null) {
            final Drawable drawable = imageView.getDrawable();
            if (drawable instanceof AsyncDrawable) {
                final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
                return asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }

    private class TaskItem{
        private int reqWidth,reqHeight,Rotate,ID;
        private String path;

        public TaskItem(int reqWidth, int reqHeight, int rotate, String path) {
            this.reqWidth = reqWidth;
            this.reqHeight = reqHeight;
            Rotate = rotate;
            this.path = path;
        }

        public int getReqWidth() {
            return reqWidth;
        }

        public int getReqHeight() {
            return reqHeight;
        }

        public int getRotate() {
            return Rotate;
        }

        public String getPath() {
            return path;
        }
    }
}
