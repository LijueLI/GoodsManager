package com.pra.haoye.goodsmanager;

public class Position_item {
        private int type;
        private String Positionname,Upposition,Imgpath;
        private int RangeX1,RangeY1,RangeX2,RangeY2,NodeX,NodeY;
        public Position_item(String Positionname,String UpPosition,String Imgpath,int RangeX1,int RangeY1,int RangeX2,int RangeY2,int NodeX,int NodeY) {
            this.Positionname = Positionname;
            this.Upposition = UpPosition;
            this.Imgpath = Imgpath;
            this.RangeX1 = RangeX1;
            this.RangeX2 = RangeX2;
            this.RangeY1 = RangeY1;
            this.RangeY2 = RangeY2;
            this.NodeX = NodeX;
            this.NodeY = NodeY;
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
        public int[] getRange(){
            int[] Range = {RangeX1,RangeY1,RangeX2,RangeY2};
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

}
