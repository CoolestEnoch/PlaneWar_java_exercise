package com.planeWar;

//��Ϸ����

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

    Explode explode;//��ը

    Date start = new Date();//��Ϸ��ʼʱ��
    Date end;//��Ϸ����ʱ��
    long period = 0;//���˶�����

    //�ڵ���ʼ������
    static int explode_x = (int) ((Math.random() * (Constant.GAME_WIDTH - 20)) + 20);
    static int explode_y = (int) ((Math.random() * (Constant.GAME_WIDTH - 20)) + 20);

    @Override
    public void paint(Graphics g) {

        //System.out.println("���ƴ��ڴ��� = " + count);

        g.drawImage(bg, 0, 0, 500, 500, null);

        //дʱ��
        drawTime(g);

        //���ɻ�
        p1.drawMyself(g);

        //���ڵ�
        for (int i = 0; i < shells.length; i++) {
            shells[i].drawMyself(g);

            //��ײ��⣺�������ڵ��ͷɻ����о��μ��
            boolean hit = shells[i].getRec().intersects(p1.getRec());
//�޵�ģʽ
            if (hit && !Constant.no_enemy_mode) {
                //System.out.println("����");
                p1.live = false;
                if (explode == null) {
                    explode = new Explode(p1.x, p1.y);//��ը��Чλ���趨
                }
                explode.drawMySelf(g);//���Ʊ�ը��Ч
            }
        }


//        Color c = g.getColor();
////        g.setColor(Color.RED);
//        g.setColor(new Color(255,0,255));
//        g.drawLine(100,100,400,400);
//        g.drawRect(100,100,300,200);
//        g.drawOval(100,100,300,200);
//        g.drawString("������",300,300);
//
//        g.setColor(c);
    }

    public void drawTime(Graphics g) {
        Color c = g.getColor();
        Font f = g.getFont();
        g.setColor(Color.GREEN);
        if (p1.live) {
            period = (System.currentTimeMillis() - start.getTime()) / 1000;
            g.drawString("�����" + period + "��", 30, 50);
        } else {
            if (end == null) {
                end = new Date();
                period = (end.getTime() - start.getTime()) / 1000;
            }
            g.setColor(Color.BLUE);
            g.setFont(new Font("΢���ź�", Font.BOLD, 30));
            g.drawString("�����" + period + "��", 200, 200);
        }
        g.setFont(f);
        g.setColor(c);
    }

    public void launchFrame() {
        shells = new Shell[Constant.bubbles];
        this.setTitle("��ɻ�");
        if (Constant.monster_mode && !Constant.settings_not_default) {
            this.setTitle("�ߣ��ߣ���������������������������������");
            bg = GameUtil.getImage("images/ysxb.jpg");
        } else if (Constant.monster_mode && Constant.settings_not_default) {
            this.setTitle("�������±����м");
            bg = GameUtil.getImage("images/lbw.jpg");
        }
        setVisible(true);

        setSize(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);

        setLocation(100, 100);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);//0���������˳�
            }
        });
        new PaintThreat().start();//�����߳��ػ�����
        this.addKeyListener(new KeyMonitor());//�����̼߳�������

        //��ʼ��50���ڵ�����
        for (int i = 0; i < shells.length; i++) {
            shells[i] = new Shell(explode_x, explode_y);
        }
    }

    //�ػ������߳�
    //����Ϊ�ڲ���Ϊ�˷���ֱ��ʹ�ô��ڵ���ط���
    class PaintThreat extends Thread {
        @Override
        public void run() {
            while (true) {
                repaint();//�ڲ������ֱ��ʹ���ⲿ���Ա
                try {
                    Thread.sleep(50);//1s=1000ms
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //�ڲ��࣬ʵ�ּ��̼�������
    class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            //System.out.println("���� " + e.getKeyCode());
            p1.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //System.out.println("̧�� " + e.getKeyCode());
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
        System.out.println("���� \"start��������Ϸ\"");
        Scanner s = new Scanner(System.in);
        String inp = s.next().toLowerCase();
        while (true) {
            if (inp.equals("start")) {
                inp = null;
                MyGameFrame gameFrame = new MyGameFrame();
                gameFrame.launchFrame();
                break;
            } else if (inp.equals("debug")) {
                System.out.println("�����޵�ģʽ��(Y/N)");
//                s.nextLine();
                inp = s.next().toLowerCase();
                if (inp.equals("y")) {
                    Constant.no_enemy_mode = true;
                    Constant.settings_not_default = true;
                } else if (inp.equals("n")) {
                    Constant.no_enemy_mode = false;
                    Constant.settings_not_default = true;
                } else {
                    System.out.println("�Ƿ����룡�޵�ģʽ��ǰ״̬ = " + Constant.no_enemy_mode);
                }
                System.out.println("�޵�ģʽ��ǰ״̬ = " + Constant.no_enemy_mode);

                System.out.println("���������ӵ�����");
//                s.nextLine();
                inp = s.next();
                try {
                    int tmp = Integer.valueOf(inp);
                    if (tmp > 0) {
                        Constant.bubbles = tmp;
                        Constant.settings_not_default = true;
                    } else {
                        System.out.println("�ӵ������Ǹ�������ǰ���ӵ��� = " + Constant.bubbles);
                    }
                } catch (Exception e) {
                    System.out.println("�Ƿ����룡��ǰ���ӵ��� = " + Constant.bubbles);
                }
                System.out.println("\n\n�޵�ģʽ��ǰ״̬ = " + Constant.no_enemy_mode);
                System.out.println("���ӵ��� = " + Constant.bubbles);


                System.out.println("\n\n\n���� \"start��������Ϸ\"");
                inp = s.next();
            } else if (!inp.equals(null)) {
                try {
                    System.out.println(
                            "                �z�}���~�{?\n" +
                                    "����     ��?��?������������\n" +
                                    "������������   ?��������?��\n" +
                                    "��?���������y��??����������\n" +
                                    "������?�|?���{��?���� ������\n" +
                                    "?��������?������ ��   ������\n" +
                                    "�������� �|��?����   ?������\n" +
                                    "?��?�y������      ��������\n" +
                                    "�����������������{��\n" +
                                    "������?�����|�~?");
                    System.out.println("�������������ɱʱ��!!!");
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
