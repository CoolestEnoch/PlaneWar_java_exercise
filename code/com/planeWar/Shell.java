package com.planeWar;

//炮弹类

import java.awt.*;

public class Shell extends GameObject {
    double degree;//炮弹沿着指定角度飞行

    public Shell(int x,int y) {//生成炮弹
        //生成位置
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

        //根据自己算法制定移动路径
        x += speed * Math.cos(degree);
        y += speed * Math.sin(degree);

        //碰到边界改方向
        if (y > Constant.GAME_HEIGHT - this.height || y < 40) {
            degree = -degree;
        }
        if (x < 0 || x > Constant.GAME_WIDTH - this.width) {
            degree = Math.PI - degree;
        }
    }
}
