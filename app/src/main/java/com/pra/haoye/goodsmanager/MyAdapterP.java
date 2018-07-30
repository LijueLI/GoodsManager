package com.pra.haoye.goodsmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

public class MyAdapterP extends BaseAdapter {
    private LayoutInflater myInflater;
    private List<Position_item> PI;

    public MyAdapterP(Context context,List<Position_item> PI) {
        myInflater = LayoutInflater.from(context);
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
        public viewHolder(TextView TPN,TextView TPR){
            this.TPN = TPN;
            this.TPR = TPR;
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        viewHolder holder = null;
        if(convertView == null){
            convertView = myInflater.inflate(R.layout.position_listview,null);
            holder = new viewHolder(
                    (TextView) convertView.findViewById(R.id.position_listview),
                    (TextView) convertView.findViewById(R.id.position_range)
            );
             convertView.setTag(holder);
        }
        else{
            holder = (viewHolder) convertView.getTag();
        }
        Position_item P = (Position_item)getItem(position);
        holder.TPN.setText(P.getPositionname()+"\n"+P.getUpposition()+"\n"+P.getimgpath());
        holder.TPR.setText("RangeX1="+P.getRange()[0]+"\n"+
                            "RangeY1="+P.getRange()[1]+"\n"+
                            "RangeX2="+P.getRange()[2]+"\n"+
                            "RangeY2="+P.getRange()[3]+"\n"+
                            "NodeX="+P.getNode()[0]+"\n"+
                            "NodeY="+P.getNode()[1]);
        return convertView;
    }
}
