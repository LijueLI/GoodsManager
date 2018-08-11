package com.pra.haoye.goodsmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Range;

public class MyDBHelper extends SQLiteOpenHelper {
    //Database名稱
    public final static String DATABASE_NAME="Position.db";
    //Database版本
    public final static int DATASE_VERSION=1;

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +"POSITION" +
                "(_id INTEGER PRIMARY KEY  NOT NULL , " +
                "_PositionName VARCHAR UNIQUE NOT NULL,"+
                "_Upposition VARCHAR,"+
                "_Imgpath VARCHAR,"+
                "_RangeX1 INTEGER,"+
                "_RangeY1 INTEGER,"+
                "_RangeX2 INTEGER,"+
                "_RangeY2 INTEGER,"+
                "_NodeX INTEGER,"+
                "_NodeY INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public Cursor select(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query("POSITION",null,null,null,null,null,null);
        return cursor;
    }
    public Cursor selectfromID(int ID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("POSITION",null,"_id = "+Integer.toString(ID),null,null,null,null);
        return cursor;
    }
    public Cursor selectfromPosition_name(String Position_name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM POSITION WHERE _PositionName like ?",new String[]{"%"+Position_name+"%"});
        return cursor;
    }
    public long insert(String PositionName,String Upposition,String Imgpath,int RangeX1,int RangeY1,int RangeX2,int RangeY2,int NodeX,int NodeY){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        cv.put("_PositionName",PositionName);
        cv.put("_Upposition",Upposition);
        cv.put("_Imgpath",Imgpath);
        cv.put("_RangeX1", RangeX1);
        cv.put("_RangeY1",RangeY1);
        cv.put("_RangeX2",RangeX2);
        cv.put("_RangeY2",RangeY2);
        cv.put("_NodeX",NodeX);
        cv.put("_NodeY",NodeY);
        return db.insert("POSITION",null,cv);
    }
    public void delete(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = "_id" + " = " + Integer.toString(id) ;
        db.delete("POSITION", where, null);
    }
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + "WORKLIST" );
    }
}
