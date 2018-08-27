package com.pra.haoye.goodsmanager;

public class Position_item {
    private Boolean choose = false;
    private String Positionname,Upposition,Imgpath;
    private int NodeX,NodeY,ID,Rotate;
    private float RangeX1,RangeY1,RangeX2,RangeY2;
    public Position_item(String Positionname,String UpPosition,String Imgpath,float RangeX1,float RangeY1,float RangeX2,float RangeY2,int NodeX,int NodeY,int Rotate) {
        this.Positionname = Positionname;
        this.Upposition = UpPosition;
        this.Imgpath = Imgpath;
        this.RangeX1 = RangeX1;
        this.RangeX2 = RangeX2;
        this.RangeY1 = RangeY1;
        this.RangeY2 = RangeY2;
        this.NodeX = NodeX;
        this.NodeY = NodeY;
        this.Rotate = Rotate;
    }
    public String getPositionname(){
            return Positionname;
        }
    public void setPositionname(String Postionname){
            this.Positionname = Postionname;
        }

    public String getUpposition(){
            return Upposition;
        }
    public void setUpposition(String Upposition){
            this.Upposition = Upposition;
        }

    public String getimgpath(){
            return Imgpath;
        }
    public void setImgpath(String Imgpath){
            this.Imgpath = Imgpath;
        }

    public float[] getRange(){
            float[] Range = {RangeX1,RangeY1,RangeX2,RangeY2};
            return Range;
        }
    public void setRange(int[] Range){
            RangeX1 = Range[0];
            RangeY1 = Range[1];
            RangeX2 = Range[2];
            RangeY2 = Range[3];
        }

    public int[] getNode(){
            int[] Node={NodeX,NodeY};
            return Node;
        }
    public void setNode(int[] Node){
            NodeX = Node[0];
            NodeY = Node[1];
        }

    public void setID(int ID){
            this.ID = ID;
        }
    public int getID(){
            return ID;
        }

    public int getRotate() {
        return Rotate;
    }
    public void setRotate(int rotate) {
        Rotate = rotate;
    }

    public Boolean getChoose() {
        return choose;
    }

    public void setChoose(Boolean choose) {
        this.choose = choose;
    }
}
