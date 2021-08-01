package com.planeWar;

//游戏主类

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class MyGameFrame extends Frame {

    Image planeImg = GameUtil.getImage("images/plane.png");
    static Image bg = GameUtil.getImage("images/bg.jpg");

    Plane p1 = new Plane(planeImg, (int) ((Math.random() * (Constant.GAME_WIDTH - 20)) + 20), (int) ((Math.random() * (Constant.GAME_WIDTH - 20)) + 20), 7);

    //Shell[] shells = new Shell[50];
    Shell[] shells = null;

    Explode explode;//爆炸

    Date start = new Date();//游戏开始时间
    Date end;//游戏结束时间
    long period = 0;//玩了多少秒

    //炮弹初始化坐标
    static int explode_x = (int) ((Math.random() * (Constant.GAME_WIDTH - 20)) + 20);
    static int explode_y = (int) ((Math.random() * (Constant.GAME_WIDTH - 20)) + 20);

    @Override
    public void paint(Graphics g) {

        //System.out.println("绘制窗口次数 = " + count);

        g.drawImage(bg, 0, 0, 500, 500, null);

        //写时间
        drawTime(g);

        //画飞机
        p1.drawMyself(g);

        //画炮弹
        for (int i = 0; i < shells.length; i++) {
            shells[i].drawMyself(g);

            //碰撞检测：将所有炮弹和飞机进行矩形检测
            boolean hit = shells[i].getRec().intersects(p1.getRec());
//无敌模式
            if (hit && !Constant.no_enemy_mode) {
                //System.out.println("击中");
                p1.live = false;
                if (explode == null) {
                    explode = new Explode(p1.x, p1.y);//爆炸特效位置设定
                }
                explode.drawMySelf(g);//绘制爆炸特效
            }
        }


//        Color c = g.getColor();
////        g.setColor(Color.RED);
//        g.setColor(new Color(255,0,255));
//        g.drawLine(100,100,400,400);
//        g.drawRect(100,100,300,200);
//        g.drawOval(100,100,300,200);
//        g.drawString("哈哈哈",300,300);
//
//        g.setColor(c);
    }

    public void drawTime(Graphics g) {
        Color c = g.getColor();
        Font f = g.getFont();
        g.setColor(Color.GREEN);
        if (p1.live) {
            period = (System.currentTimeMillis() - start.getTime()) / 1000;
            g.drawString("坚持了" + period + "秒", 30, 50);
        } else {
            if (end == null) {
                end = new Date();
                period = (end.getTime() - start.getTime()) / 1000;
            }
            g.setColor(Color.BLUE);
            g.setFont(new Font("微软雅黑", Font.BOLD, 30));
            g.drawString("坚持了" + period + "秒", 200, 200);
        }
        g.setFont(f);
        g.setColor(c);
    }

    public void launchFrame() {
        shells = new Shell[Constant.bubbles];
        this.setTitle("打飞机");
        if (Constant.monster_mode && !Constant.settings_not_default) {
            this.setTitle("哼，哼，啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
            bg = GameUtil.getImage("images/ysxb.jpg");
        } else if (Constant.monster_mode && Constant.settings_not_default) {
            this.setTitle("开挂来下北泽的屑");
            bg = GameUtil.getImage("images/lbw.jpg");
        }
        setVisible(true);

        setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);

        setLocation(100, 100);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);//0代表正常退出
            }
        });
        new PaintThreat().start();//启动线程重画窗口
        this.addKeyListener(new KeyMonitor());//启动线程监听按键

        //初始化50个炮弹对象
        for (int i = 0; i < shells.length; i++) {
            shells[i] = new Shell(explode_x, explode_y);
        }
    }

    //重画窗口线程
    //定义为内部类为了方便直接使用窗口的相关方法
    class PaintThreat extends Thread {
        @Override
        public void run() {
            while (true) {
                repaint();//内部类可以直接使用外部类成员
                try {
                    Thread.sleep(50);//1s=1000ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //内部类，实现键盘监听处理
    class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            //System.out.println("按下 " + e.getKeyCode());
            p1.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //System.out.println("抬起 " + e.getKeyCode());
            p1.minusDircetion(e);
        }
    }

    private Image offScreanImage = null;

    public void update(Graphics g) {
        if (offScreanImage == null)
            offScreanImage = this.createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);
        Graphics gOff = offScreanImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreanImage, 0, 0, null);
    }

    public static void main(String[] args) {
        System.out.println("输入 \"start来启动游戏\"");
        Scanner s = new Scanner(System.in);
        String inp = s.next().toLowerCase();
        while (true) {
            if (inp.equals("start")) {
                inp = null;
                MyGameFrame gameFrame = new MyGameFrame();
                gameFrame.launchFrame();
                break;
            } else if (inp.equals("debug")) {
                System.out.println("开启无敌模式？(Y/N)");
//                s.nextLine();
                inp = s.next().toLowerCase();
                if (inp.equals("y")) {
                    Constant.no_enemy_mode = true;
                    Constant.settings_not_default = true;
                } else if (inp.equals("n")) {
                    Constant.no_enemy_mode = false;
                    Constant.settings_not_default = true;
                } else {
                    System.out.println("非法输入！无敌模式当前状态 = " + Constant.no_enemy_mode);
                }
                System.out.println("无敌模式当前状态 = " + Constant.no_enemy_mode);

                System.out.println("请输入总子弹数：");
//                s.nextLine();
                inp = s.next();
                try {
                    int tmp = Integer.valueOf(inp);
                    if (tmp > 0) {
                        Constant.bubbles = tmp;
                        Constant.settings_not_default = true;
                    } else {
                        System.out.println("子弹不能是负数！当前总子弹数 = " + Constant.bubbles);
                    }
                } catch (Exception e) {
                    System.out.println("非法输入！当前总子弹数 = " + Constant.bubbles);
                }
                System.out.println("\n\n无敌模式当前状态 = " + Constant.no_enemy_mode);
                System.out.println("总子弹数 = " + Constant.bubbles);


                System.out.println("\n\n\n输入 \"start来启动游戏\"");
                inp = s.next();
            } else if (!inp.equals(null)) {
                try {
                    System.out.println(
                            "                ▃▆█▇▄?\n" +
                                    "　　     　?◤?　　　◥█▎\n" +
                                    "　　　◢◤　   ?　　　　?▉\n" +
                                    "　?◤　　　▂　??　　▕█▎\n" +
                                    "　◤　?▅?◥▄　?◣　 　█▊\n" +
                                    "?　▕▎◥?◣◤　 　   ◢██\n" +
                                    "█◣　◥ ▅█?　　   ?██◤\n" +
                                    "?█?▂　　　      ◢██◤\n" +
                                    "　◥██◣　　◢▄◤\n" +
                                    "　　　?██▅▇?");
                    System.out.println("即将开启随机猎杀时刻!!!");
                    Thread.sleep(1000);
                    System.out.println("5");
                    Thread.sleep(1000);
                    System.out.println("4");
                    Thread.sleep(1000);
                    System.out.println("3");
                    Thread.sleep(1000);
                    System.out.println("2");
                    Thread.sleep(1000);
                    System.out.println("1");

                    Constant.bubbles = (int) (Math.random() * 114514);
                    Constant.monster_mode = true;
                    MyGameFrame gameFrame = new MyGameFrame();
                    gameFrame.launchFrame();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
