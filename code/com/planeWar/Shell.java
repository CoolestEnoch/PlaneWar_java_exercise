package com.planeWar;

//�ڵ���

import java.awt.*;

public class Shell extends GameObject {
    double degree;//�ڵ�����ָ���Ƕȷ���

    public Shell(int x,int y) {//�����ڵ�
        //����λ��
        /*x = 200;
        y = 200;
        x = (int)((Math.random()*(Constant.GAME_WIDTH-20))+20);
        y = (int)((Math.random()*(Constant.GAME_WIDTH-20))+20);*/
        this.x = x;
        this.y = y;

        degree = Math.random() * Math.PI * 2;

        width = 5;
        height = 5;
        speed = 3;
    }

    @Override
    public void drawMyself(Graphics g) {

        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.fillOval((int) x, (int) y, width, height);
        g.setColor(c);

        //�����Լ��㷨�ƶ��ƶ�·��
        x += speed * Math.cos(degree);
        y += speed * Math.sin(degree);

        //�����߽�ķ���
        if (y > Constant.GAME_HEIGHT - this.height || y < 40) {
            degree = -degree;
        }
        if (x < 0 || x > Constant.GAME_WIDTH - this.width) {
            degree = Math.PI - degree;
        }
    }
}
