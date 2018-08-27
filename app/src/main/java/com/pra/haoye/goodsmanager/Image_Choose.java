package com.pra.haoye.goodsmanager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class Image_Choose extends AppCompatActivity {
    private Button openimgdb,ok,clear,openphoto,rotate;
    private ImageView img;
    private Bitmap bm = null;
    private String path = "";
    private Intent IN;
    private Uri uri;
    private float x1 = 0,x2 = 0,y1 = 0,y2 = 0,touchX,touchY,mdx=0,mdy=0;
    private int Rotate = 0;
    private static final String[] permissioncameras = new String[]{Manifest.permission.CAMERA,WRITE_EXTERNAL_STORAGE};
    private static final String[] PERMISSION_EXTERNAL_STORAGE = new String[] {
            Manifest.permission.WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE};
    private PointF p1 = new PointF(0,0);//笫一點
    private PointF p2 = new PointF(0,0);//第二點
    private PointF move_p = new PointF(0,0);//移動初始位置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image__choose);

        IN=getIntent();
        path = IN.getStringExtra("Imgpath");
        Rotate = IN.getIntExtra("Rotate",0);

        openimgdb = findViewById(R.id.ImC_imgdb);
        openphoto = findViewById(R.id.IMC_Photo);
        ok = findViewById(R.id.IMC_ok);
        clear = findViewById(R.id.IMC_clear);
        rotate = findViewById(R.id.IMC_Rotate);
        img = findViewById(R.id.imageView2);

        openimgdb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionWrite = ActivityCompat.checkSelfPermission(Image_Choose.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE);
                if(permissionWrite != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Image_Choose.this, PERMISSION_EXTERNAL_STORAGE,
                            100);
                }
                else{
                    Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
                    getAlbum.setType("image/*");
                    startActivityForResult(getAlbum, 0);
                }
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("imgpath",path);
                intent.putExtra("RangeX1",x1);
                intent.putExtra("RangeX2",x2);
                intent.putExtra("RangeY1",y1);
                intent.putExtra("RangeY2",y2);
                intent.putExtra("Rotate",Rotate);
                setResult(2,intent);
                finish();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                path = "";
                bm = null;
                x1 = 0;
                x2 = 0;
                y1 = 0;
                y2 = 0;
                Rotate = 0;
                img.setImageResource(android.R.color.transparent);
            }
        });
        openphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissioncamera = ActivityCompat.checkSelfPermission(Image_Choose.this,
                        Manifest.permission.CAMERA);
                if(permissioncamera != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(Image_Choose.this,permissioncameras ,131);
                }
                else {
                     uri = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            new ContentValues());
                    Intent IntentObj = new Intent("android.media.action.IMAGE_CAPTURE");
                    IntentObj.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(IntentObj, 13);
                }
            }
        });
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bm != null) {
                    /* 建立 Matrix 物件 */
                    Matrix matrix = new Matrix();
                    /* 設定旋轉角度 */
                    matrix.postRotate(90);
                    /* 用原來的 Bitmap 產生一個新的 Bitmap */
                    Bitmap bmp = bm;
                    bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
                    img.setImageBitmap(bm);
                    bmp.recycle();
                    if(Rotate < 4) Rotate++;
                    else Rotate = 0;
                }
            }
        });
        if(!path.equals("null")){
            File file1 = new File(path);
            if(file1.exists()){
                bm = BitmapFactory.decodeFile(path);
                img.setImageBitmap(bm);
                for(int i =0 ;i<Rotate;i++){
                    /* 建立 Matrix 物件 */
                    Matrix matrix = new Matrix();
                    /* 設定旋轉角度 */
                    matrix.postRotate(90);
                    Bitmap bmp = bm;
                    /* 用原來的 Bitmap 產生一個新的 Bitmap */
                    bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
                    bmp.recycle();
                }
                img.setImageBitmap(bm);
                x1 = IN.getFloatExtra("RangeX1",0);
                y1 = IN.getFloatExtra("RangeY1",0);
                x2 = IN.getFloatExtra("RangeX2",0);
                y2 = IN.getFloatExtra("RangeY2",0);
                ImgDrawRect();
            }
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        //廖柏州的原程式碼
        img.setOnTouchListener(new View.OnTouchListener() {
            float scalValH,scalValW,scalVal;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if(bm != null) {
                    touchX = event.getX();       // 觸控的 X 軸位置
                    touchY = event.getY();  // 觸控的 Y 軸位置
                    // 判斷觸控動作
                    switch (event.getAction()) {

                        case MotionEvent.ACTION_DOWN:  // 按下
                            if (p1.x == 0 && p1.y == 0) {
                                p1 = new PointF(touchX, touchY);
                                p2 = new PointF(touchX, touchY);
                            } else move_p = new PointF(touchX, touchY);
                            scalValW=((float)bm.getWidth()/(float)img.getWidth());
                            scalValH = ((float)bm.getHeight()/(float)img.getHeight());
                            scalVal = scalValH < scalValW ? scalValH : scalValW;
                            x1 =  (scalVal * (p1.x + mdx));
                            x2 =  (scalVal * (p2.x + mdx));
                            y1 =  (scalVal * (p1.y + mdy));
                            y2 =  (scalVal * (p2.y + mdy));
                            ImgDrawRect();//畫框
                            break;

                        case MotionEvent.ACTION_MOVE:  // 拖曳移動
                            if (move_p.x == 0 && move_p.y == 0) p2 = new PointF(touchX, touchY);
                            else {
                                mdx = touchX - move_p.x;
                                mdy = touchY - move_p.y;
                            }
                            scalValW=((float)bm.getWidth()/(float)img.getWidth());
                            scalValH = ((float)bm.getHeight()/(float)img.getHeight());
                            scalVal = scalValH < scalValW ? scalValH : scalValW;
                            x1 =  (scalVal * (p1.x + mdx));
                            x2 =  (scalVal * (p2.x + mdx));
                            y1 =  (scalVal * (p1.y + mdy));
                            y2 =  (scalVal * (p2.y + mdy));
                            ImgDrawRect();//畫框
                            break;

                        case MotionEvent.ACTION_UP:  // 放開 //設定完成

                            p1.x = p1.x + mdx;
                            p1.y = p1.y + mdy;
                            p2.x = p2.x + mdx;
                            p2.y = p2.y + mdy;
                            mdx = 0;
                            mdy = 0;
                            scalValW=((float)bm.getWidth()/(float)img.getWidth());
                            scalValH = ((float)bm.getHeight()/(float)img.getHeight());
                            scalVal = scalValH < scalValW ? scalValH : scalValW;
                            x1 =  (scalVal * (p1.x + mdx));
                            x2 =  (scalVal * (p2.x + mdx));
                            y1 =  (scalVal * (p1.y + mdy));
                            y2 =  (scalVal * (p2.y + mdy));
                            ImgDrawRect();//畫框
                            p1 = new PointF(0,0);//笫一點
                            p2 = new PointF(0,0);//第二點
                            move_p = new PointF(0,0);//移動初始位置
                            break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) { //此處的 RESULT_OK 是系統自定義得一個常量
            Log.e("resultERR", "ActivityResult resultCode error");
            return;
        }
        //外界的程式訪問ContentProvider所提供數據 可以通過ContentResolver介面
        ContentResolver resolver = getContentResolver();
        //此處的用於判斷接收的Activity是不是你想要的那個
        if (requestCode == 0) {
            try {
                Uri originalUri = data.getData(); //獲得圖片的uri
                Log.e("fileuri", originalUri.toString());
                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri); //顯得到bitmap圖片
                //這裏開始的第二部分，獲取圖片的路徑：
                path = getPathByUri4kitkat(this, originalUri);
                img.setImageBitmap(bm);
            } catch (IOException e) {
                Log.e("err", e.toString());
            }
        }
        else if(requestCode == 13){
            try {
                Log.e("fileuri", uri.toString());
                bm = MediaStore.Images.Media.getBitmap(resolver, uri); //顯得到bitmap圖片
                //這裏開始的第二部分，獲取圖片的路徑：
                path = getPathByUri4kitkat(this, uri);
                img.setImageBitmap(bm);
            } catch (IOException e) {
                Log.e("err", e.toString());
            }
        }
    }

    @SuppressLint("NewApi")
    public String getPathByUri4kitkat(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {// ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Log.e("SDcard","NOP");
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                else{
                    int len = uri.getPath().split("/").length;
                    String path = "/storage";
                    for(int i = 2;i<len;i++) path += "/"+uri.getPath().split("/")[i];
                    path = path.split(":")[0]+"/"+path.split(":")[1];
                    Log.e("SDcard",path);
                    Toast.makeText(this,"目前不支援SDcard檔案",Toast.LENGTH_LONG).show();
                    return path;
                }
            } else if (isDownloadsDocument(uri)) {// DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {// MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore
            // (and
            // general)
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());

    }

    public void   ImgDrawRect( ) {
        Paint mpaint = new Paint();
        Paint mpaintStr = new Paint();
        mpaint.setColor(Color.RED);
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setStrokeWidth(10);
        mpaintStr.setStrokeWidth(4);
        mpaintStr.setTextSize(60);

        mpaintStr.setColor(Color.GREEN);
        Bitmap vBitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), bm.getConfig());
        Paint paint = new Paint();
        Canvas canvas = new Canvas(vBitmap);
        canvas.drawBitmap(bm, new Matrix(), paint);
        Canvas vBitmapCanvas = new Canvas(vBitmap);
        vBitmapCanvas.drawRect(x1, y1, x2, y2, mpaint);
        //canvas.drawText("X:" + x1 + "Y::" + y1, x1 - 10, y2 + 70, mpaintStr);
        //canvas.drawText("X:" + x2 + "Y::" + y2, x1 - 10, y2, mpaintStr);
        img.setImageBitmap(vBitmap);
    }
}
