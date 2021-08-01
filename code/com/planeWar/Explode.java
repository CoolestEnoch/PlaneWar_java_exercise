package com.planeWar;

//飞机爆炸类

import java.awt.*;

public class Explode {

    double x, y;//位置

    static Image[] imgs = new Image[16];

    int count;//画图帧数计数

    static {
        for (int i = 0; i < 16; i++) {
            imgs[i] = GameUtil.getImage("images/explode/e" + (i + 1) + ".gif");
            imgs[i].getWidth(null);//防止懒加载，就是只加载一半
        }
    }

    public void drawMySelf(Graphics g) {
        if (count < imgs.length) {
            g.drawImage(imgs[count], (int) x, (int) y, null);
            count++;
        }
    }

    public Explode(){}

    public Explode(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
