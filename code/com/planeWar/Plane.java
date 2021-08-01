package com.planeWar;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.security.Key;

public class Plane extends GameObject {

    boolean left, right, up, down;//飞机方向控制

    boolean live = true;//飞机生命值状态


    @Override
    public void drawMyself(Graphics g) {
        if (live) {
            super.drawMyself(g);

            //修改每次的位置——飞机飞行的算法

            if (left) {
                x -= speed;
            }
            if (right) {
                x += speed;
            }
            if (up) {
                y -= speed;
            }
            if (down) {
                y += speed;
            }
            boolean b = isInScreen();
        }
    }

    public boolean isInScreen() {
/*        if (this.x >= 9 && this.x <= 417 && this.y >= 30 && this.y <= 457) {
            return true;
        }*/
        if(x < 9){
            x = 9;
        }
        if(x > 461){
            x = 461;
        }
        if(y < 30){
            y = 30;
        }
        if(y > 457){
            y = 457;
        }
        return true;
    }

    public void addDirection(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                left = isInScreen();
                break;
            case KeyEvent.VK_RIGHT:
                right = isInScreen();
                break;
            case KeyEvent.VK_UP:
                up = isInScreen();
                break;
            case KeyEvent.VK_DOWN:
                down = isInScreen();
                break;
        }
    }

    public void minusDircetion(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
        }
    }

    public Plane(Image img, double x, double y, int speed) {
        super(img, x, y, speed);
    }
}
