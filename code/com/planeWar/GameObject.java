package com.planeWar;

import java.awt.*;

public class GameObject {
    Image img = null;//图片
    double x,y;//物体坐标
    int speed;//移动速度
    int width,height;//物体宽高

    public GameObject(){};

    public GameObject(Image img, double x, double y, int speed, int width, int height) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    public GameObject(Image img, double x, double y, int speed) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;

        this.width = img.getWidth(null);
        this.height = img.getHeight(null);
    }

    public void drawMyself(Graphics g){
        g.drawImage(img, (int)x, (int)y, width, height, null);
    }

    //所有物体都是矩形，获取到对应矩形后进行相关操作
    public Rectangle getRec(){
        return new Rectangle((int)x,(int)y,width,height);
    }
}
