package com.planeWar;

//�ɻ���ը��

import java.awt.*;

public class Explode {

    double x, y;//λ��

    static Image[] imgs = new Image[16];

    int count;//��ͼ֡������

    static {
        for (int i = 0; i < 16; i++) {
            imgs[i] = GameUtil.getImage("images/explode/e" + (i + 1) + ".gif");
            imgs[i].getWidth(null);//��ֹ�����أ�����ֻ����һ��
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
