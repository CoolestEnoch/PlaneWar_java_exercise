package com.planeWar;

import java.awt.*;

public class GameObject {
    Image img = null;//ͼƬ
    double x,y;//��������
    int speed;//�ƶ��ٶ�
    int width,height;//������

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

    //�������嶼�Ǿ��Σ���ȡ����Ӧ���κ������ز���
    public Rectangle getRec(){
        return new Rectangle((int)x,(int)y,width,height);
    }
}
