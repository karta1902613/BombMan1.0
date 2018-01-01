import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class MainFrame extends JFrame {
    private Container cp;
    private JLabel jlbs[][] = new JLabel[8][8];
    private int jlbsCode[][] = new int [8][8];
    // ====================物件編號======================
    //∥       0 = null                                  ∥
    //∥       1 = 不可破壞物件                          ∥
    //∥       2 = 可破壞物件                            ∥
    //∥       3 = 1p                                    ∥
    //∥       4 = 2p                                    ∥
    //∥       5 = 炸彈                                  ∥
    //∥       6 = 有炸彈&&有人                          ∥
    //∥      -1 = 增強火力道具                          ∥
    //∥      -2 = 增加炸彈道具                          ∥
    // ==================================================
    //物件圖片
    private ImageIcon imgeGrass = new ImageIcon("grass.jpg");
    private ImageIcon imgeStone = new ImageIcon("stone.jpg");
    private ImageIcon imgeStone2 = new ImageIcon("stone2.jpg");
    private ImageIcon imgeBrick = new ImageIcon("brick.jpg");
    private ImageIcon imgeBomb = new ImageIcon("bomb.jpg");
    private ImageIcon imge1pSBomb = new ImageIcon("1pSbomb.jpg");
    private ImageIcon imge2pSBomb = new ImageIcon("2pSbomb.jpg");
    private ImageIcon imgeFire = new ImageIcon("fire.jpg");
    private ImageIcon imgeFireW = new ImageIcon("firew.jpg");
    private ImageIcon imgeFireA = new ImageIcon("firea.jpg");
    private ImageIcon imgeFireS = new ImageIcon("fires.jpg");
    private ImageIcon imgeFireD = new ImageIcon("fired.jpg");
    private ImageIcon imgebombPlus = new ImageIcon("bomb++.jpg");
    private ImageIcon imgefirePlus = new ImageIcon("fire++.jpg");
    private ImageIcon imgefireWS = new ImageIcon("firews.jpg");
    private ImageIcon imgefireAD = new ImageIcon("firead.jpg");

    //1P上下左右圖片
    private ImageIcon imge1pW = new ImageIcon("1pW.jpg");
    private ImageIcon imge1pS = new ImageIcon("1pS.jpg");
    private ImageIcon imge1pA = new ImageIcon("1pA.jpg");
    private ImageIcon imge1pD = new ImageIcon("1pD.jpg");
    //2P上下左右圖片
    private ImageIcon imge2pW = new ImageIcon("2pW.jpg");
    private ImageIcon imge2pS = new ImageIcon("2pS.jpg");
    private ImageIcon imge2pA = new ImageIcon("2pA.jpg");
    private ImageIcon imge2pD = new ImageIcon("2pD.jpg");
    private JMenuBar jmb = new JMenuBar();

    private JMenu jmGame = new JMenu("Game");
    private JMenu jmAbout = new JMenu("About");

    private JMenuItem jmiNG = new JMenuItem("New Game");
    private JMenuItem jmiHelp = new JMenuItem("Help");

    private Timer BombTime1PT1;
    private Timer BombTime1PT2;
    private Timer BombTime1PT3;
    private Timer BombTime1PT4;
    private Timer BombTime1PT5;
    private Timer BombTime2PT1;
    private int onePbt1 ,onePbt2,onePbt3,onePbt4,onePbt5;
    private int twoPbt1 ,twoPbt2,twoPbt3;
    private int oxb1 , oxb2 , oxb3, oxb4 ,oxb5;
    private int oyb1 , oyb2 , oyb3, oyb4 ,oyb5;
    private int txb1 , txb2 , txb3;
    private int tyb1 , tyb2 , tyb3;
    private int reward;
    private int onebombQua = 1;
    private int onefireQua = 1;
    private int twobombQua = 1;
    private int twofireQua = 1;
    private int rTmpx;
    private int rTmpy;
    private int countBomb=0;
    private int queueBomb=1;

    private boolean b = true;
    private boolean c = true;


   private int x1 = 0, y1 = 0 ,x2 = 7, y2 = 7;

    public MainFrame() {
        init();
    }

    private void init() {

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        cp = this.getContentPane();
        cp.setLayout(new GridLayout(8, 8, 0, 0));
        setBounds(500, 0, 1000, 1010);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                jlbs[i][j]= new JLabel();

                cp.add(jlbs[i][j]);
            }
        }
        restart();
        this.setJMenuBar(jmb);
        jmb.add(jmGame);
        jmb.add(jmAbout);

        jmGame.add(jmiNG);
        jmAbout.add(jmiHelp);




        this.addKeyListener( new KeyAdapter(){
            public void keyPressed(KeyEvent e)
            {
                int i = e.getKeyCode() ;
                // System.out.println(x2);
                switch( i )
                {
                    case 37: //往左
                        if(x2==0||jlbsCode[y2][x2-1]>0) {
                            if(jlbsCode[y2][x2]!=6) {
                                jlbs[y2][x2].setIcon(imge2pA);
                                break;
                            }
                        }
                        else if(jlbsCode[y2][x2]==6){

                            x2--;
                            jlbs[y2][x2+1].setIcon(imgeBomb);
                            jlbsCode[y2][x2+1]=5;
                            jlbs[y2][x2].setIcon(imge2pA);
                            countTwo();
                            jlbsCode[y2][x2]=4;

                            break;
                        }else {
                            x2--;
                            jlbsCode[y2][x2+1]=0;
                            jlbs[y2][x2+1].setIcon(imgeGrass);
                            jlbs[y2][x2].setIcon(imge2pA);
                            countTwo();
                            jlbsCode[y2][x2]=4;
                            break;
                        }


                    case 38: //往上
                        if(y2==0||jlbsCode[y2-1][x2]>0){
                            if(jlbsCode[y2][x2]!=6) {   //防止腳色於炸彈上移動時覆蓋炸彈
                                jlbs[y2][x2].setIcon(imge2pW);
                                break;
                            }
                        }
                        else if(jlbsCode[y2][x2]==6){  //放置炸彈移動後設定炸彈位置以移動

                            y2--;
                            jlbs[y2+1][x2].setIcon(imgeBomb);
                            jlbsCode[y2+1][x2]=5;
                            jlbs[y2][x2].setIcon(imge2pW);
                            countTwo();
                            jlbsCode[y2][x2]=4;

                            break;
                        }else { //人物移動設地原本位置圖片以及移動之後位置圖片
                            y2--;
                            jlbsCode[y2+1][x2]=0;
                            jlbs[y2+1][x2].setIcon(imgeGrass);
                            jlbs[y2][x2].setIcon(imge2pW);
                            countTwo();
                            jlbsCode[y2][x2]=4;
                            break;
                        }

                    case 39: //往右
                        if(x2==7||jlbsCode[y2][x2+1]>0){
                            if(jlbsCode[y2][x2]!=6) {
                                jlbs[y2][x2].setIcon(imge2pD);
                                break;
                            }
                        }
                        else if(jlbsCode[y2][x2]==6){

                            x2++;
                            jlbs[y2][x2-1].setIcon(imgeBomb);
                            jlbsCode[y2][x2-1]=5;
                            jlbs[y2][x2].setIcon(imge2pD);
                            countTwo();
                            jlbsCode[y2][x2]=4;

                            break;
                        }else{
                            x2++;
                            jlbsCode[y2][x2-1]=0;
                            jlbs[y2][x2-1].setIcon(imgeGrass);
                            jlbs[y2][x2].setIcon(imge2pD);
                            countTwo();
                            jlbsCode[y2][x2]=4;
                            break;
                        }


                    case 40: //往下
                        if(y2==7||jlbsCode[y2+1][x2]>0){
                            if(jlbsCode[y2][x2]!=6) {
                                jlbs[y2][x2].setIcon(imge2pS);
                                break;
                            }
                        }
                        else if(jlbsCode[y2][x2]==6){

                            y2++;
                            jlbs[y2-1][x2].setIcon(imgeBomb);
                            jlbsCode[y2-1][x2]=5;
                            jlbs[y2][x2].setIcon(imge2pS);
                            countTwo();
                            jlbsCode[y2][x2]=4;

                            break;
                        }else {
                            y2++;
                            jlbsCode[y2-1][x2]=0;
                            jlbs[y2-1][x2].setIcon(imgeGrass);
                            jlbs[y2][x2].setIcon(imge2pS);
                            countTwo();
                            jlbsCode[y2][x2]=4;

                            break;
                        }
                    case 87: //W
                        if(y1==0||jlbsCode[y1-1][x1]>0){
                            if(jlbsCode[y1][x1]!=6) {   //防止腳色於炸彈上移動時覆蓋炸彈
                                jlbs[y1][x1].setIcon(imge1pW);
                                break;
                            }
                        }
                        else if(jlbsCode[y1][x1]==6){  //放置炸彈移動後設定炸彈位置以移動

                            y1--;
                            jlbs[y1+1][x1].setIcon(imgeBomb);
                            jlbsCode[y1+1][x1]=5;
                            jlbs[y1][x1].setIcon(imge1pW);
                            countOne();
                            jlbsCode[y1][x1]=3;

                            break;
                        }else { //人物移動設地原本位置圖片以及移動之後位置圖片
                            y1--;
                            jlbsCode[y1+1][x1]=0;
                            jlbs[y1+1][x1].setIcon(imgeGrass);
                            jlbs[y1][x1].setIcon(imge1pW);
                            countOne();
                            jlbsCode[y1][x1]=3;
                            break;
                        }
                    case 83: //S
                        if(y1==7||jlbsCode[y1+1][x1]>0){
                            if(jlbsCode[y1][x1]!=6) {
                                jlbs[y1][x1].setIcon(imge1pS);
                                break;
                            }
                        }
                        else if(jlbsCode[y1][x1]==6){

                            y1++;
                            jlbs[y1-1][x1].setIcon(imgeBomb);
                            jlbsCode[y1-1][x1]=5;
                            jlbs[y1][x1].setIcon(imge1pS);
                            countOne();
                            jlbsCode[y1][x1]=3;

                            break;
                        }else {
                            y1++;
                            jlbsCode[y1-1][x1]=0;
                            jlbs[y1-1][x1].setIcon(imgeGrass);
                            jlbs[y1][x1].setIcon(imge1pS);
                            countOne();
                            jlbsCode[y1][x1]=3;
                            break;
                        }
                    case 65: //A
                        if(x1==0||jlbsCode[y1][x1-1]>0) {
                            if(jlbsCode[y1][x1]!=6) {
                                jlbs[y1][x1].setIcon(imge1pA);
                                break;
                            }
                        }
                        else if(jlbsCode[y1][x1]==6){

                            x1--;
                            jlbs[y1][x1+1].setIcon(imgeBomb);
                            jlbsCode[y1][x1+1]=5;
                            jlbs[y1][x1].setIcon(imge1pA);
                            countOne();
                            jlbsCode[y1][x1]=3;

                            break;
                        }else {
                            x1--;
                            jlbsCode[y1][x1+1]=0;
                            jlbs[y1][x1+1].setIcon(imgeGrass);
                            jlbs[y1][x1].setIcon(imge1pA);
                            jlbsCode[y1][x1]=3;
                            countOne();
                            break;
                        }
                    case 68: //D
                        if(x1==7||jlbsCode[y1][x1+1]>0){
                            if(jlbsCode[y1][x1]!=6) {
                                jlbs[y1][x1].setIcon(imge1pD);
                                break;
                            }
                        }
                        else if(jlbsCode[y1][x1]==6){

                            x1++;
                            jlbs[y1][x1-1].setIcon(imgeBomb);
                            jlbsCode[y1][x1-1]=5;
                            jlbs[y1][x1].setIcon(imge1pD);
                            jlbsCode[y1][x1]=3;
                            countOne();
                            break;
                        }else{
                            x1++;
                            jlbsCode[y1][x1-1]=0;
                            jlbs[y1][x1-1].setIcon(imgeGrass);
                            jlbs[y1][x1].setIcon(imge1pD);
                            countOne();
                            jlbsCode[y1][x1]=3;
                            break;
                        }
                    case 32: //SPACE
                            if(countBomb<onebombQua&&jlbsCode[y1][x1]!=6){
                                switch (queueBomb){
                                    case 1:
                                        countBomb++;
                                        queueBomb=2;
                                        jlbs[y1][x1].setIcon(imge1pSBomb);
                                        jlbsCode[y1][x1]=6;
                                        oyb1=y1;
                                        oxb1=x1;
                                        BombTime1PT1.start();
                                        break;
                                    case 2:
                                        countBomb++;
                                        queueBomb=3;
                                        jlbs[y1][x1].setIcon(imge1pSBomb);
                                        jlbsCode[y1][x1]=6;
                                        oyb2=y1;
                                        oxb2=x1;
                                        BombTime1PT2.start();
                                        break;
                                    case 3:
                                        countBomb++;
                                        queueBomb=4;
                                        jlbs[y1][x1].setIcon(imge1pSBomb);
                                        jlbsCode[y1][x1]=6;
                                        oyb3=y1;
                                        oxb3=x1;
                                        BombTime1PT3.start();
                                        break;
                                    case 4:
                                        countBomb++;
                                        queueBomb=5;
                                        jlbs[y1][x1].setIcon(imge1pSBomb);
                                        jlbsCode[y1][x1]=6;
                                        oyb4=y1;
                                        oxb4=x1;
                                        BombTime1PT4.start();
                                        break;
                                    case 5:
                                        countBomb++;
                                        queueBomb=1;
                                        jlbs[y1][x1].setIcon(imge1pSBomb);
                                        jlbsCode[y1][x1]=6;
                                        oyb5=y1;
                                        oxb5=x1;
                                        BombTime1PT5.start();
                                        break;
                                }
                            }else{
                                break;
                            }
                            break;


                    case 96: //0
                        if(c == true){
                            jlbs[y2][x2].setIcon(imge2pSBomb);
                            jlbsCode[y2][x2]=6;
                            tyb1=y2;
                            txb1=x2;
                            c=false;
                            BombTime2PT1.start();
                        }


                }
            }
        }) ;

        BombTime1PT1 = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                onePbt1++;
                if(onePbt1>6){
                    jlbs[oyb1][oxb1].setIcon(imgeFire);

                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb1+i<=7) { //往下炸
                            if (jlbsCode[oyb1 + i][oxb1] == 0 || jlbsCode[oyb1 + i][oxb1] > 2||jlbsCode[oyb1+i][oxb1]<0) {
                                jlbs[oyb1 + i][oxb1].setIcon(imgefireWS);
                            } else if (jlbsCode[oyb1 + i][oxb1] == 2) {
                                jlbs[oyb1 + i][oxb1].setIcon(imgefireWS);
                                break;
                            }else if(jlbsCode[oyb1 +i][oxb1]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb1-i>=0) { //往上炸
                            if (jlbsCode[oyb1 - i][oxb1] == 0 || jlbsCode[oyb1 - i][oxb1] > 2||jlbsCode[oyb1-i][oxb1]<0) {
                                jlbs[oyb1 - i][oxb1].setIcon(imgefireWS);
                            } else if (jlbsCode[oyb1 - i][oxb1] == 2) {
                                jlbs[oyb1 - i][oxb1].setIcon(imgefireWS);
                                break;
                            }else if(jlbsCode[oyb1 -i][oxb1]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb1-i>=0) { //往左炸
                            if (jlbsCode[oyb1][oxb1-i] == 0 || jlbsCode[oyb1][oxb1-i] > 2||jlbsCode[oyb1][oxb1-i]<0) {
                                jlbs[oyb1][oxb1-i].setIcon(imgefireAD);
                            } else if (jlbsCode[oyb1][oxb1-i] == 2) {
                                jlbs[oyb1][oxb1-i].setIcon(imgefireAD);
                                break;
                            }else if(jlbsCode[oyb1][oxb1-i]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb1+i<=7) { //往右炸
                            if (jlbsCode[oyb1][oxb1+i] == 0 || jlbsCode[oyb1][oxb1+i] > 2||jlbsCode[oyb1][oxb1+i]<0) {
                                jlbs[oyb1][oxb1+i].setIcon(imgefireAD);
                            } else if (jlbsCode[oyb1][oxb1+i] == 2) {
                                jlbs[oyb1][oxb1+i].setIcon(imgefireAD);
                                break;
                            }else if(jlbsCode[oyb1][oxb1+i]==1){
                                break;
                            }
                        }
                    }

                    //判定腳色死亡
                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb1+i<=7) { //往下炸
                            if (jlbsCode[oyb1 + i][oxb1] == 0 || jlbsCode[oyb1 + i][oxb1] > 2) {
                                if (jlbsCode[oyb1 + i][oxb1] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb1 + i][oxb1] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb1 + i][oxb1] == 2||jlbsCode[oyb1 +i][oxb1]==1) {
                                break;

                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb1-i>=0) { //往上炸
                            if (jlbsCode[oyb1 - i][oxb1] == 0 || jlbsCode[oyb1 - i][oxb1] > 2) {
                                if (jlbsCode[oyb1 - i][oxb1] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb1 - i][oxb1] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb1 - i][oxb1] == 2||jlbsCode[oyb1 - i][oxb1]==1) {
                                break;

                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb1-i>=0) { //往左炸
                            if (jlbsCode[oyb1][oxb1-i] == 0 || jlbsCode[oyb1][oxb1-i] > 2) {
                                if (jlbsCode[oyb1][oxb1-i] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb1][oxb1-i] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb1][oxb1-i] == 2||jlbsCode[oyb1][oxb1-i]==1) {
                                break;

                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb1+i<=7) { //往右炸
                            if (jlbsCode[oyb1][oxb1+i] == 0 || jlbsCode[oyb1][oxb1+i] > 2) {
                                if (jlbsCode[oyb1][oxb1+i] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb1][oxb1+i] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb1][oxb1+i] == 2||jlbsCode[oyb1][oxb1+i]==1) {
                                break;

                            }
                        }
                    }
                    if (jlbsCode[oyb1][oxb1] == 6) { //中
                        oPdie();
                    }
                }
                if(onePbt1>7){
                    //設定編碼 回復草地
                    jlbsCode[oyb1][oxb1]=0;
                    jlbs[oyb1][oxb1].setIcon(imgeGrass);

                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb1+i<=7) { //往下炸
                            if (jlbsCode[oyb1 + i][oxb1] == 0 || jlbsCode[oyb1 + i][oxb1] > 2||jlbsCode[oyb1+i][oxb1]<0) {
                                jlbs[oyb1 + i][oxb1].setIcon(imgeGrass);
                                jlbsCode[oyb1+i][oxb1] = 0;
                            } else if (jlbsCode[oyb1 + i][oxb1] == 2) {

                                oyb1+=i;
                                rTmpx=oxb1;
                                rTmpy=oyb1;
                                rewardOne();
                                oyb1-=i;
                                break;
                            } else if(jlbsCode[oyb1+i][oxb1]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb1-i>=0) { //往上炸
                            if (jlbsCode[oyb1 - i][oxb1] == 0 || jlbsCode[oyb1 - i][oxb1] > 2||jlbsCode[oyb1-i][oxb1]<0) {
                                jlbs[oyb1 - i][oxb1].setIcon(imgeGrass);
                                jlbsCode[oyb1-i][oxb1] = 0;
                            } else if (jlbsCode[oyb1 - i][oxb1] == 2) {
                                oyb1-=i;
                                rTmpx=oxb1;
                                rTmpy=oyb1;
                                rewardOne();
                                oyb1+=i;
                                break;
                            } else if(jlbsCode[oyb1-i][oxb1]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb1-i>=0) { //往左炸
                            if (jlbsCode[oyb1][oxb1-i] == 0 || jlbsCode[oyb1][oxb1-i] > 2||jlbsCode[oyb1][oxb1-i]<0) {
                                jlbs[oyb1][oxb1-i].setIcon(imgeGrass);
                                jlbsCode[oyb1][oxb1-i] = 0;
                            } else if (jlbsCode[oyb1][oxb1-i] == 2) {
                                oxb1-=i;
                                rTmpx=oxb1;
                                rTmpy=oyb1;
                                rewardOne();
                                oxb1+=i;
                                break;
                            } else if(jlbsCode[oyb1][oxb1-i]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb1+i<=7) { //往右炸
                            if (jlbsCode[oyb1][oxb1+i] == 0 || jlbsCode[oyb1][oxb1+i] > 2||jlbsCode[oyb1][oxb1+i]<0) {
                                jlbs[oyb1][oxb1+i].setIcon(imgeGrass);
                                jlbsCode[oyb1][oxb1+i] = 0;
                            } else if (jlbsCode[oyb1][oxb1+i] == 2) {
                                oxb1+=i;
                                rTmpx=oxb1;
                                rTmpy=oyb1;
                                rewardOne();
                                oxb1-=i;
                                break;
                            } else if(jlbsCode[oyb1][oxb1+i]==1){
                                break;
                            }
                        }
                    }

                    b=true;

                    BombTime1PT1.stop();
                    onePbt1=0;
                    countBomb--;

                }
            }
        });
        BombTime1PT2 = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                onePbt2++;
                if(onePbt2>6){
                    jlbs[oyb2][oxb2].setIcon(imgeFire);

                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb2+i<=7) { //往下炸
                            if (jlbsCode[oyb2 + i][oxb2] == 0 || jlbsCode[oyb2 + i][oxb2] > 2||jlbsCode[oyb2+i][oxb2]<0) {
                                jlbs[oyb2 + i][oxb2].setIcon(imgefireWS);
                            } else if (jlbsCode[oyb2 + i][oxb2] == 2) {
                                jlbs[oyb2 + i][oxb2].setIcon(imgefireWS);
                                break;
                            }else if(jlbsCode[oyb2 +i][oxb2]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb2-i>=0) { //往上炸
                            if (jlbsCode[oyb2 - i][oxb2] == 0 || jlbsCode[oyb2 - i][oxb2] > 2||jlbsCode[oyb2-i][oxb2]<0) {
                                jlbs[oyb2 - i][oxb2].setIcon(imgefireWS);
                            } else if (jlbsCode[oyb2 - i][oxb2] == 2) {
                                jlbs[oyb2 - i][oxb2].setIcon(imgefireWS);
                                break;
                            }else if(jlbsCode[oyb2 -i][oxb2]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb2-i>=0) { //往左炸
                            if (jlbsCode[oyb2][oxb2-i] == 0 || jlbsCode[oyb2][oxb2-i] > 2||jlbsCode[oyb2][oxb2-i]<0) {
                                jlbs[oyb2][oxb2-i].setIcon(imgefireAD);
                            } else if (jlbsCode[oyb2][oxb2-i] == 2) {
                                jlbs[oyb2][oxb2-i].setIcon(imgefireAD);
                                break;
                            }else if(jlbsCode[oyb2][oxb2-i]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb2+i<=7) { //往右炸
                            if (jlbsCode[oyb2][oxb2+i] == 0 || jlbsCode[oyb2][oxb2+i] > 2||jlbsCode[oyb2][oxb2+i]<0) {
                                jlbs[oyb2][oxb2+i].setIcon(imgefireAD);
                            } else if (jlbsCode[oyb2][oxb2+i] == 2) {
                                jlbs[oyb2][oxb2+i].setIcon(imgefireAD);
                                break;
                            }else if(jlbsCode[oyb2][oxb2+i]==1){
                                break;
                            }
                        }
                    }

                    //判定腳色死亡
                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb2+i<=7) { //往下炸
                            if (jlbsCode[oyb2 + i][oxb2] == 0 || jlbsCode[oyb2 + i][oxb2] > 2) {
                                if (jlbsCode[oyb2 + i][oxb2] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb2 + i][oxb2] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb2 + i][oxb2] == 2||jlbsCode[oyb2 +i][oxb2]==1) {
                                break;

                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb2-i>=0) { //往上炸
                            if (jlbsCode[oyb2 - i][oxb2] == 0 || jlbsCode[oyb2 - i][oxb2] > 2) {
                                if (jlbsCode[oyb2 - i][oxb2] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb2 - i][oxb2] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb2 - i][oxb2] == 2||jlbsCode[oyb2 - i][oxb2]==1) {
                                break;

                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb2-i>=0) { //往左炸
                            if (jlbsCode[oyb2][oxb2-i] == 0 || jlbsCode[oyb2][oxb2-i] > 2) {
                                if (jlbsCode[oyb2][oxb2-i] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb2][oxb2-i] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb2][oxb2-i] == 2||jlbsCode[oyb2][oxb2-i]==1) {
                                break;

                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb2+i<=7) { //往右炸
                            if (jlbsCode[oyb2][oxb2+i] == 0 || jlbsCode[oyb2][oxb2+i] > 2) {
                                if (jlbsCode[oyb2][oxb2+i] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb2][oxb2+i] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb2][oxb2+i] == 2||jlbsCode[oyb2][oxb2+i]==1) {
                                break;

                            }
                        }
                    }
                    if (jlbsCode[oyb2][oxb2] == 6) { //中
                        oPdie();
                    }
                }
                if(onePbt2>7){
                    //設定編碼 回復草地
                    jlbsCode[oyb2][oxb2]=0;
                    jlbs[oyb2][oxb2].setIcon(imgeGrass);

                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb2+i<=7) { //往下炸
                            if (jlbsCode[oyb2 + i][oxb2] == 0 || jlbsCode[oyb2 + i][oxb2] > 2||jlbsCode[oyb2+i][oxb2]<0) {
                                jlbs[oyb2 + i][oxb2].setIcon(imgeGrass);
                                jlbsCode[oyb2+i][oxb2] = 0;
                            } else if (jlbsCode[oyb2 + i][oxb2] == 2) {

                                oyb2+=i;
                                rTmpx=oxb2;
                                rTmpy=oyb2;
                                rewardOne();
                                oyb2-=i;
                                break;
                            } else if(jlbsCode[oyb2+i][oxb2]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb2-i>=0) { //往上炸
                            if (jlbsCode[oyb2 - i][oxb2] == 0 || jlbsCode[oyb2 - i][oxb2] > 2||jlbsCode[oyb2-i][oxb2]<0) {
                                jlbs[oyb2 - i][oxb2].setIcon(imgeGrass);
                                jlbsCode[oyb2-i][oxb2] = 0;
                            } else if (jlbsCode[oyb2 - i][oxb2] == 2) {
                                oyb2-=i;
                                rTmpx=oxb2;
                                rTmpy=oyb2;
                                rewardOne();
                                oyb2+=i;
                                break;
                            } else if(jlbsCode[oyb2-i][oxb2]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb2-i>=0) { //往左炸
                            if (jlbsCode[oyb2][oxb2-i] == 0 || jlbsCode[oyb2][oxb2-i] > 2||jlbsCode[oyb2][oxb2-i]<0) {
                                jlbs[oyb2][oxb2-i].setIcon(imgeGrass);
                                jlbsCode[oyb2][oxb2-i] = 0;
                            } else if (jlbsCode[oyb2][oxb2-i] == 2) {
                                oxb2-=i;
                                rTmpx=oxb2;
                                rTmpy=oyb2;
                                rewardOne();
                                oxb2+=i;
                                break;
                            } else if(jlbsCode[oyb2][oxb2-i]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb2+i<=7) { //往右炸
                            if (jlbsCode[oyb2][oxb2+i] == 0 || jlbsCode[oyb2][oxb2+i] > 2||jlbsCode[oyb2][oxb2+i]<0) {
                                jlbs[oyb2][oxb2+i].setIcon(imgeGrass);
                                jlbsCode[oyb2][oxb2+i] = 0;
                            } else if (jlbsCode[oyb2][oxb2+i] == 2) {
                                oxb2+=i;
                                rTmpx=oxb2;
                                rTmpy=oyb2;
                                rewardOne();
                                oxb2-=i;
                                break;
                            } else if(jlbsCode[oyb2][oxb2+i]==1){
                                break;
                            }
                        }
                    }

                    b=true;
                    BombTime1PT2.stop();
                    onePbt2=0;
                    countBomb--;

                }
            }
        });
        BombTime1PT3 = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                onePbt3++;
                if(onePbt3>6){
                    jlbs[oyb3][oxb3].setIcon(imgeFire);

                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb3+i<=7) { //往下炸
                            if (jlbsCode[oyb3 + i][oxb3] == 0 || jlbsCode[oyb3 + i][oxb3] > 2||jlbsCode[oyb3+i][oxb3]<0) {
                                jlbs[oyb3 + i][oxb3].setIcon(imgefireWS);
                            } else if (jlbsCode[oyb3 + i][oxb3] == 2) {
                                jlbs[oyb3 + i][oxb3].setIcon(imgefireWS);
                                break;
                            }else if(jlbsCode[oyb3 +i][oxb3]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb3-i>=0) { //往上炸
                            if (jlbsCode[oyb3 - i][oxb3] == 0 || jlbsCode[oyb3 - i][oxb3] > 2||jlbsCode[oyb3-i][oxb3]<0) {
                                jlbs[oyb3 - i][oxb3].setIcon(imgefireWS);
                            } else if (jlbsCode[oyb3 - i][oxb3] == 2) {
                                jlbs[oyb3 - i][oxb3].setIcon(imgefireWS);
                                break;
                            }else if(jlbsCode[oyb3 -i][oxb3]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb3-i>=0) { //往左炸
                            if (jlbsCode[oyb3][oxb3-i] == 0 || jlbsCode[oyb3][oxb3-i] > 2||jlbsCode[oyb3][oxb3-i]<0) {
                                jlbs[oyb3][oxb3-i].setIcon(imgefireAD);
                            } else if (jlbsCode[oyb3][oxb3-i] == 2) {
                                jlbs[oyb3][oxb3-i].setIcon(imgefireAD);
                                break;
                            }else if(jlbsCode[oyb3][oxb3-i]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb3+i<=7) { //往右炸
                            if (jlbsCode[oyb3][oxb3+i] == 0 || jlbsCode[oyb3][oxb3+i] > 2||jlbsCode[oyb3][oxb3+i]<0) {
                                jlbs[oyb3][oxb3+i].setIcon(imgefireAD);
                            } else if (jlbsCode[oyb3][oxb3+i] == 2) {
                                jlbs[oyb3][oxb3+i].setIcon(imgefireAD);
                                break;
                            }else if(jlbsCode[oyb3][oxb3+i]==1){
                                break;
                            }
                        }
                    }

                    //判定腳色死亡
                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb3+i<=7) { //往下炸
                            if (jlbsCode[oyb3 + i][oxb3] == 0 || jlbsCode[oyb3 + i][oxb3] > 2) {
                                if (jlbsCode[oyb3 + i][oxb3] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb3 + i][oxb3] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb3 + i][oxb3] == 2||jlbsCode[oyb3 +i][oxb3]==1) {
                                break;

                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb3-i>=0) { //往上炸
                            if (jlbsCode[oyb3 - i][oxb3] == 0 || jlbsCode[oyb3 - i][oxb3] > 2) {
                                if (jlbsCode[oyb3 - i][oxb3] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb3 - i][oxb3] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb3 - i][oxb3] == 2||jlbsCode[oyb3 - i][oxb3]==1) {
                                break;

                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb3-i>=0) { //往左炸
                            if (jlbsCode[oyb3][oxb3-i] == 0 || jlbsCode[oyb3][oxb3-i] > 2) {
                                if (jlbsCode[oyb3][oxb3-i] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb3][oxb3-i] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb3][oxb3-i] == 2||jlbsCode[oyb3][oxb3-i]==1) {
                                break;

                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb3+i<=7) { //往右炸
                            if (jlbsCode[oyb3][oxb3+i] == 0 || jlbsCode[oyb3][oxb3+i] > 2) {
                                if (jlbsCode[oyb3][oxb3+i] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb3][oxb3+i] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb3][oxb3+i] == 2||jlbsCode[oyb3][oxb3+i]==1) {
                                break;

                            }
                        }
                    }
                    if (jlbsCode[oyb3][oxb3] == 6) { //中
                        oPdie();
                    }
                }
                if(onePbt3>7){
                    //設定編碼 回復草地
                    jlbsCode[oyb3][oxb3]=0;
                    jlbs[oyb3][oxb3].setIcon(imgeGrass);

                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb3+i<=7) { //往下炸
                            if (jlbsCode[oyb3 + i][oxb3] == 0 || jlbsCode[oyb3 + i][oxb3] > 2||jlbsCode[oyb3+i][oxb3]<0) {
                                jlbs[oyb3 + i][oxb3].setIcon(imgeGrass);
                                jlbsCode[oyb3+i][oxb3] = 0;
                            } else if (jlbsCode[oyb3 + i][oxb3] == 2) {

                                oyb3+=i;
                                rTmpx=oxb3;
                                rTmpy=oyb3;
                                rewardOne();
                                oyb3-=i;
                                break;
                            } else if(jlbsCode[oyb3+i][oxb3]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb3-i>=0) { //往上炸
                            if (jlbsCode[oyb3 - i][oxb3] == 0 || jlbsCode[oyb3 - i][oxb3] > 2||jlbsCode[oyb3-i][oxb3]<0) {
                                jlbs[oyb3 - i][oxb3].setIcon(imgeGrass);
                                jlbsCode[oyb3-i][oxb3] = 0;
                            } else if (jlbsCode[oyb3 - i][oxb3] == 2) {
                                oyb3-=i;
                                rTmpx=oxb3;
                                rTmpy=oyb3;
                                rewardOne();
                                oyb3+=i;
                                break;
                            } else if(jlbsCode[oyb3-i][oxb3]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb3-i>=0) { //往左炸
                            if (jlbsCode[oyb3][oxb3-i] == 0 || jlbsCode[oyb3][oxb3-i] > 2||jlbsCode[oyb3][oxb3-i]<0) {
                                jlbs[oyb3][oxb3-i].setIcon(imgeGrass);
                                jlbsCode[oyb3][oxb3-i] = 0;
                            } else if (jlbsCode[oyb3][oxb3-i] == 2) {
                                oxb3-=i;
                                rTmpx=oxb3;
                                rTmpy=oyb3;
                                rewardOne();
                                oxb3+=i;
                                break;
                            } else if(jlbsCode[oyb3][oxb3-i]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb3+i<=7) { //往右炸
                            if (jlbsCode[oyb3][oxb3+i] == 0 || jlbsCode[oyb3][oxb3+i] > 2||jlbsCode[oyb3][oxb3+i]<0) {
                                jlbs[oyb3][oxb3+i].setIcon(imgeGrass);
                                jlbsCode[oyb3][oxb3+i] = 0;
                            } else if (jlbsCode[oyb3][oxb3+i] == 2) {
                                oxb3+=i;
                                rTmpx=oxb3;
                                rTmpy=oyb3;
                                rewardOne();
                                oxb3-=i;
                                break;
                            } else if(jlbsCode[oyb3][oxb3+i]==1){
                                break;
                            }
                        }
                    }

                    BombTime1PT3.stop();
                    onePbt3=0;
                    countBomb--;

                }
            }
        });
        BombTime1PT4 = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                onePbt4++;
                if(onePbt4>6){
                    jlbs[oyb4][oxb4].setIcon(imgeFire);

                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb4+i<=7) { //往下炸
                            if (jlbsCode[oyb4 + i][oxb4] == 0 || jlbsCode[oyb4 + i][oxb4] > 2||jlbsCode[oyb4+i][oxb4]<0) {
                                jlbs[oyb4 + i][oxb4].setIcon(imgefireWS);
                            } else if (jlbsCode[oyb4 + i][oxb4] == 2) {
                                jlbs[oyb4 + i][oxb4].setIcon(imgefireWS);
                                break;
                            }else if(jlbsCode[oyb4 +i][oxb4]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb4-i>=0) { //往上炸
                            if (jlbsCode[oyb4 - i][oxb4] == 0 || jlbsCode[oyb4 - i][oxb4] > 2||jlbsCode[oyb4-i][oxb4]<0) {
                                jlbs[oyb4 - i][oxb4].setIcon(imgefireWS);
                            } else if (jlbsCode[oyb4 - i][oxb4] == 2) {
                                jlbs[oyb4 - i][oxb4].setIcon(imgefireWS);
                                break;
                            }else if(jlbsCode[oyb4 -i][oxb4]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb4-i>=0) { //往左炸
                            if (jlbsCode[oyb4][oxb4-i] == 0 || jlbsCode[oyb4][oxb4-i] > 2||jlbsCode[oyb4][oxb4-i]<0) {
                                jlbs[oyb4][oxb4-i].setIcon(imgefireAD);
                            } else if (jlbsCode[oyb4][oxb4-i] == 2) {
                                jlbs[oyb4][oxb4-i].setIcon(imgefireAD);
                                break;
                            }else if(jlbsCode[oyb4][oxb4-i]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb4+i<=7) { //往右炸
                            if (jlbsCode[oyb4][oxb4+i] == 0 || jlbsCode[oyb4][oxb4+i] > 2||jlbsCode[oyb4][oxb4+i]<0) {
                                jlbs[oyb4][oxb4+i].setIcon(imgefireAD);
                            } else if (jlbsCode[oyb4][oxb4+i] == 2) {
                                jlbs[oyb4][oxb4+i].setIcon(imgefireAD);
                                break;
                            }else if(jlbsCode[oyb4][oxb4+i]==1){
                                break;
                            }
                        }
                    }

                    //判定腳色死亡
                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb4+i<=7) { //往下炸
                            if (jlbsCode[oyb4 + i][oxb4] == 0 || jlbsCode[oyb4 + i][oxb4] > 2) {
                                if (jlbsCode[oyb4 + i][oxb4] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb4 + i][oxb4] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb4 + i][oxb4] == 2||jlbsCode[oyb4 +i][oxb4]==1) {
                                break;

                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb4-i>=0) { //往上炸
                            if (jlbsCode[oyb4 - i][oxb4] == 0 || jlbsCode[oyb4 - i][oxb4] > 2) {
                                if (jlbsCode[oyb4 - i][oxb4] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb4 - i][oxb4] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb4 - i][oxb4] == 2||jlbsCode[oyb4 - i][oxb4]==1) {
                                break;

                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb4-i>=0) { //往左炸
                            if (jlbsCode[oyb4][oxb4-i] == 0 || jlbsCode[oyb4][oxb4-i] > 2) {
                                if (jlbsCode[oyb4][oxb4-i] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb4][oxb4-i] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb4][oxb4-i] == 2||jlbsCode[oyb4][oxb4-i]==1) {
                                break;

                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb4+i<=7) { //往右炸
                            if (jlbsCode[oyb4][oxb4+i] == 0 || jlbsCode[oyb4][oxb4+i] > 2) {
                                if (jlbsCode[oyb4][oxb4+i] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb4][oxb4+i] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb4][oxb4+i] == 2||jlbsCode[oyb4][oxb4+i]==1) {
                                break;

                            }
                        }
                    }
                    if (jlbsCode[oyb4][oxb4] == 6) { //中
                        oPdie();
                    }
                }
                if(onePbt4>7){
                    //設定編碼 回復草地
                    jlbsCode[oyb4][oxb4]=0;
                    jlbs[oyb4][oxb4].setIcon(imgeGrass);

                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb4+i<=7) { //往下炸
                            if (jlbsCode[oyb4 + i][oxb4] == 0 || jlbsCode[oyb4 + i][oxb4] > 2||jlbsCode[oyb4+i][oxb4]<0) {
                                jlbs[oyb4 + i][oxb4].setIcon(imgeGrass);
                                jlbsCode[oyb4+i][oxb4] = 0;
                            } else if (jlbsCode[oyb4 + i][oxb4] == 2) {

                                oyb4+=i;
                                rTmpx=oxb4;
                                rTmpy=oyb4;
                                rewardOne();
                                oyb4-=i;
                                break;
                            } else if(jlbsCode[oyb4+i][oxb4]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb4-i>=0) { //往上炸
                            if (jlbsCode[oyb4 - i][oxb4] == 0 || jlbsCode[oyb4 - i][oxb4] > 2||jlbsCode[oyb4-i][oxb4]<0) {
                                jlbs[oyb4 - i][oxb4].setIcon(imgeGrass);
                                jlbsCode[oyb4-i][oxb4] = 0;
                            } else if (jlbsCode[oyb4 - i][oxb4] == 2) {
                                oyb4-=i;
                                rTmpx=oxb4;
                                rTmpy=oyb4;
                                rewardOne();
                                oyb4+=i;
                                break;
                            } else if(jlbsCode[oyb4-i][oxb4]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb4-i>=0) { //往左炸
                            if (jlbsCode[oyb4][oxb4-i] == 0 || jlbsCode[oyb4][oxb4-i] > 2||jlbsCode[oyb4][oxb4-i]<0) {
                                jlbs[oyb4][oxb4-i].setIcon(imgeGrass);
                                jlbsCode[oyb4][oxb4-i] = 0;
                            } else if (jlbsCode[oyb4][oxb4-i] == 2) {
                                oxb4-=i;
                                rTmpx=oxb4;
                                rTmpy=oyb4;
                                rewardOne();
                                oxb4+=i;
                                break;
                            } else if(jlbsCode[oyb4][oxb4-i]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb4+i<=7) { //往右炸
                            if (jlbsCode[oyb4][oxb4+i] == 0 || jlbsCode[oyb4][oxb4+i] > 2||jlbsCode[oyb4][oxb4+i]<0) {
                                jlbs[oyb4][oxb4+i].setIcon(imgeGrass);
                                jlbsCode[oyb4][oxb4+i] = 0;
                            } else if (jlbsCode[oyb4][oxb4+i] == 2) {
                                oxb4+=i;
                                rTmpx=oxb4;
                                rTmpy=oyb4;
                                rewardOne();
                                oxb4-=i;
                                break;
                            } else if(jlbsCode[oyb4][oxb4+i]==1){
                                break;
                            }
                        }
                    }

                    BombTime1PT4.stop();
                    onePbt4=0;
                    countBomb--;

                }
            }
        });
        BombTime1PT5 = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                onePbt5++;
                if(onePbt5>6){
                    jlbs[oyb5][oxb5].setIcon(imgeFire);

                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb5+i<=7) { //往下炸
                            if (jlbsCode[oyb5 + i][oxb5] == 0 || jlbsCode[oyb5 + i][oxb5] > 2||jlbsCode[oyb5+i][oxb5]<0) {
                                jlbs[oyb5 + i][oxb5].setIcon(imgefireWS);
                            } else if (jlbsCode[oyb5 + i][oxb5] == 2) {
                                jlbs[oyb5 + i][oxb5].setIcon(imgefireWS);
                                break;
                            }else if(jlbsCode[oyb5 +i][oxb5]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb5-i>=0) { //往上炸
                            if (jlbsCode[oyb5 - i][oxb5] == 0 || jlbsCode[oyb5 - i][oxb5] > 2||jlbsCode[oyb5-i][oxb5]<0) {
                                jlbs[oyb5 - i][oxb5].setIcon(imgefireWS);
                            } else if (jlbsCode[oyb5 - i][oxb5] == 2) {
                                jlbs[oyb5 - i][oxb5].setIcon(imgefireWS);
                                break;
                            }else if(jlbsCode[oyb5 -i][oxb5]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb5-i>=0) { //往左炸
                            if (jlbsCode[oyb5][oxb5-i] == 0 || jlbsCode[oyb5][oxb5-i] > 2||jlbsCode[oyb5][oxb5-i]<0) {
                                jlbs[oyb5][oxb5-i].setIcon(imgefireAD);
                            } else if (jlbsCode[oyb5][oxb5-i] == 2) {
                                jlbs[oyb5][oxb5-i].setIcon(imgefireAD);
                                break;
                            }else if(jlbsCode[oyb5][oxb5-i]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb5+i<=7) { //往右炸
                            if (jlbsCode[oyb5][oxb5+i] == 0 || jlbsCode[oyb5][oxb5+i] > 2||jlbsCode[oyb5][oxb5+i]<0) {
                                jlbs[oyb5][oxb5+i].setIcon(imgefireAD);
                            } else if (jlbsCode[oyb5][oxb5+i] == 2) {
                                jlbs[oyb5][oxb5+i].setIcon(imgefireAD);
                                break;
                            }else if(jlbsCode[oyb5][oxb5+i]==1){
                                break;
                            }
                        }
                    }

                    //判定腳色死亡
                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb5+i<=7) { //往下炸
                            if (jlbsCode[oyb5 + i][oxb5] == 0 || jlbsCode[oyb5 + i][oxb5] > 2) {
                                if (jlbsCode[oyb5 + i][oxb5] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb5 + i][oxb5] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb5 + i][oxb5] == 2||jlbsCode[oyb5 +i][oxb5]==1) {
                                break;

                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb5-i>=0) { //往上炸
                            if (jlbsCode[oyb5 - i][oxb5] == 0 || jlbsCode[oyb5 - i][oxb5] > 2) {
                                if (jlbsCode[oyb5 - i][oxb5] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb5 - i][oxb5] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb5 - i][oxb5] == 2||jlbsCode[oyb5 - i][oxb5]==1) {
                                break;

                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb5-i>=0) { //往左炸
                            if (jlbsCode[oyb5][oxb5-i] == 0 || jlbsCode[oyb5][oxb5-i] > 2) {
                                if (jlbsCode[oyb5][oxb5-i] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb5][oxb5-i] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb5][oxb5-i] == 2||jlbsCode[oyb5][oxb5-i]==1) {
                                break;

                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb5+i<=7) { //往右炸
                            if (jlbsCode[oyb5][oxb5+i] == 0 || jlbsCode[oyb5][oxb5+i] > 2) {
                                if (jlbsCode[oyb5][oxb5+i] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[oyb5][oxb5+i] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[oyb5][oxb5+i] == 2||jlbsCode[oyb5][oxb5+i]==1) {
                                break;

                            }
                        }
                    }
                    if (jlbsCode[oyb5][oxb5] == 6) { //中
                        oPdie();
                    }
                }
                if(onePbt5>7){
                    //設定編碼 回復草地
                    jlbsCode[oyb5][oxb5]=0;
                    jlbs[oyb5][oxb5].setIcon(imgeGrass);

                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb5+i<=7) { //往下炸
                            if (jlbsCode[oyb5 + i][oxb5] == 0 || jlbsCode[oyb5 + i][oxb5] > 2||jlbsCode[oyb5+i][oxb5]<0) {
                                jlbs[oyb5 + i][oxb5].setIcon(imgeGrass);
                                jlbsCode[oyb5+i][oxb5] = 0;
                            } else if (jlbsCode[oyb5 + i][oxb5] == 2) {

                                oyb5+=i;
                                rTmpx=oxb5;
                                rTmpy=oyb5;
                                rewardOne();
                                oyb5-=i;
                                break;
                            } else if(jlbsCode[oyb5+i][oxb5]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<onefireQua+1;i++){
                        if(oyb5-i>=0) { //往上炸
                            if (jlbsCode[oyb5 - i][oxb5] == 0 || jlbsCode[oyb5 - i][oxb5] > 2||jlbsCode[oyb5-i][oxb5]<0) {
                                jlbs[oyb5 - i][oxb5].setIcon(imgeGrass);
                                jlbsCode[oyb5-i][oxb5] = 0;
                            } else if (jlbsCode[oyb5 - i][oxb5] == 2) {
                                oyb5-=i;
                                rTmpx=oxb5;
                                rTmpy=oyb5;
                                rewardOne();
                                oyb5+=i;
                                break;
                            } else if(jlbsCode[oyb5-i][oxb5]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb5-i>=0) { //往左炸
                            if (jlbsCode[oyb5][oxb5-i] == 0 || jlbsCode[oyb5][oxb5-i] > 2||jlbsCode[oyb5][oxb5-i]<0) {
                                jlbs[oyb5][oxb5-i].setIcon(imgeGrass);
                                jlbsCode[oyb5][oxb5-i] = 0;
                            } else if (jlbsCode[oyb5][oxb5-i] == 2) {
                                oxb5-=i;
                                rTmpx=oxb5;
                                rTmpy=oyb5;
                                rewardOne();
                                oxb5+=i;
                                break;
                            } else if(jlbsCode[oyb5][oxb5-i]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<onefireQua+1;i++){
                        if(oxb5+i<=7) { //往右炸
                            if (jlbsCode[oyb5][oxb5+i] == 0 || jlbsCode[oyb5][oxb5+i] > 2||jlbsCode[oyb5][oxb5+i]<0) {
                                jlbs[oyb5][oxb5+i].setIcon(imgeGrass);
                                jlbsCode[oyb5][oxb5+i] = 0;
                            } else if (jlbsCode[oyb5][oxb5+i] == 2) {
                                oxb5+=i;
                                rTmpx=oxb5;
                                rTmpy=oyb5;
                                rewardOne();
                                oxb5-=i;
                                break;
                            } else if(jlbsCode[oyb5][oxb5+i]==1){
                                break;
                            }
                        }
                    }

                    BombTime1PT5.stop();
                    onePbt5=0;
                    countBomb--;

                }
            }
        });








        BombTime2PT1 = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                twoPbt1++;

                if(twoPbt1>6){
                    jlbs[tyb1][txb1].setIcon(imgeFire);

                    for(int i=1;i<twofireQua+1;i++){
                        if(tyb1+i<=7) { //往下炸
                            if (jlbsCode[tyb1 + i][txb1] == 0 || jlbsCode[tyb1 + i][txb1] > 2||jlbsCode[tyb1+i][txb1]<0) {
                                jlbs[tyb1 + i][txb1].setIcon(imgefireWS);
                            } else if (jlbsCode[tyb1 + i][txb1] == 2) {
                                jlbs[tyb1 + i][txb1].setIcon(imgefireWS);
                                break;
                            }else if(jlbsCode[tyb1 +i][txb1]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<twofireQua+1;i++){
                        if(tyb1-i>=0) { //往上炸
                            if (jlbsCode[tyb1 - i][txb1] == 0 || jlbsCode[tyb1 - i][txb1] > 2||jlbsCode[tyb1-i][txb1]<0) {
                                jlbs[tyb1 - i][txb1].setIcon(imgefireWS);
                            } else if (jlbsCode[tyb1 - i][txb1] == 2) {
                                jlbs[tyb1 - i][txb1].setIcon(imgefireWS);
                                break;
                            }else if(jlbsCode[tyb1 -i][txb1]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<twofireQua+1;i++){
                        if(txb1-i>=0) { //往左炸
                            if (jlbsCode[tyb1][txb1-i] == 0 || jlbsCode[tyb1][txb1-i] > 2||jlbsCode[tyb1][txb1-i]<0) {
                                jlbs[tyb1][txb1-i].setIcon(imgefireAD);
                            } else if (jlbsCode[tyb1][txb1-i] == 2) {
                                jlbs[tyb1][txb1-i].setIcon(imgefireAD);
                                break;
                            }else if(jlbsCode[tyb1][txb1-i]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<twofireQua+1;i++){
                        if(txb1+i<=7) { //往右炸
                            if (jlbsCode[tyb1][txb1+i] == 0 || jlbsCode[tyb1][txb1+i] > 2||jlbsCode[tyb1][txb1+i]<0) {
                                jlbs[tyb1][txb1+i].setIcon(imgefireAD);
                            } else if (jlbsCode[tyb1][txb1+i] == 2) {
                                jlbs[tyb1][txb1+i].setIcon(imgefireAD);
                                break;
                            }else if(jlbsCode[tyb1][txb1+i]==1){
                                break;
                            }
                        }
                    }

                    //判定腳色死亡
                    for(int i=1;i<twofireQua+1;i++){
                        if(tyb1+i<=7) { //往下炸
                            if (jlbsCode[tyb1 + i][txb1] == 0 || jlbsCode[tyb1 + i][txb1] > 2) {
                                if (jlbsCode[tyb1 + i][txb1] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[tyb1 + i][txb1] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[tyb1 + i][txb1] == 2||jlbsCode[tyb1 +i][txb1]==1) {
                                break;

                            }
                        }
                    }

                    for(int i=1;i<twofireQua+1;i++){
                        if(tyb1-i>=0) { //往上炸
                            if (jlbsCode[tyb1 - i][txb1] == 0 || jlbsCode[tyb1 - i][txb1] > 2) {
                                if (jlbsCode[tyb1 - i][txb1] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[tyb1 - i][txb1] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[tyb1 - i][txb1] == 2||jlbsCode[tyb1 - i][txb1]==1) {
                                break;

                            }
                        }
                    }

                    for(int i=1;i<twofireQua+1;i++){
                        if(txb1-i>=0) { //往左炸
                            if (jlbsCode[tyb1][txb1-i] == 0 || jlbsCode[tyb1][txb1-i] > 2) {
                                if (jlbsCode[tyb1][txb1-i] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[tyb1][txb1-i] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[tyb1][txb1-i] == 2||jlbsCode[tyb1][txb1-i]==1) {
                                break;

                            }
                        }
                    }
                    for(int i=1;i<twofireQua+1;i++){
                        if(txb1+i<=7) { //往右炸
                            if (jlbsCode[tyb1][txb1+i] == 0 || jlbsCode[tyb1][txb1+i] > 2) {
                                if (jlbsCode[tyb1][txb1+i] == 3) { //下
                                    oPdie();
                                } else if (jlbsCode[tyb1][txb1+i] == 4) {
                                    tPdie();
                                }
                            } else if (jlbsCode[tyb1][txb1+i] == 2||jlbsCode[tyb1][txb1+i]==1) {
                                break;

                            }
                        }
                    }
                    if (jlbsCode[tyb1][txb1] == 6) { //中
                        oPdie();
                    }
                }
                if(twoPbt1>7){
                    //設定編碼 回復草地
                    jlbsCode[tyb1][txb1]=0;
                    jlbs[tyb1][txb1].setIcon(imgeGrass);

                    for(int i=1;i<twofireQua+1;i++){
                        if(tyb1+i<=7) { //往下炸
                            if (jlbsCode[tyb1 + i][txb1] == 0 || jlbsCode[tyb1 + i][txb1] > 2||jlbsCode[tyb1+i][txb1]<0) {
                                jlbs[tyb1 + i][txb1].setIcon(imgeGrass);
                                jlbsCode[tyb1+i][txb1] = 0;
                            } else if (jlbsCode[tyb1 + i][txb1] == 2) {
                                tyb1+=i;
                                rewardTwo();
                                tyb1-=i;
                                break;
                            } else if(jlbsCode[tyb1+i][txb1]==1){
                                break;
                            }
                        }
                    }
                    for(int i=1;i<twofireQua+1;i++){
                        if(tyb1-i>=0) { //往上炸
                            if (jlbsCode[tyb1 - i][txb1] == 0 || jlbsCode[tyb1 - i][txb1] > 2||jlbsCode[tyb1-i][txb1]<0) {
                                jlbs[tyb1 - i][txb1].setIcon(imgeGrass);
                                jlbsCode[tyb1-i][txb1] = 0;
                            } else if (jlbsCode[tyb1 - i][txb1] == 2) {
                                tyb1-=i;
                                rewardTwo();
                                tyb1+=i;
                                break;
                            } else if(jlbsCode[tyb1-i][txb1]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<twofireQua+1;i++){
                        if(txb1-i>=0) { //往左炸
                            if (jlbsCode[tyb1][txb1-i] == 0 || jlbsCode[tyb1][txb1-i] > 2||jlbsCode[tyb1][txb1-i]<0) {
                                jlbs[tyb1][txb1-i].setIcon(imgeGrass);
                                jlbsCode[tyb1][txb1-i] = 0;
                            } else if (jlbsCode[tyb1][txb1-i] == 2) {
                                txb1-=i;
                                rewardTwo();
                                txb1+=i;
                                break;
                            } else if(jlbsCode[tyb1][txb1-i]==1){
                                break;
                            }
                        }
                    }

                    for(int i=1;i<twofireQua+1;i++){
                        if(txb1+i<=7) { //往右炸
                            if (jlbsCode[tyb1][txb1+i] == 0 || jlbsCode[tyb1][txb1+i] > 2||jlbsCode[tyb1][txb1+i]<0) {
                                jlbs[tyb1][txb1+i].setIcon(imgeGrass);
                                jlbsCode[tyb1][txb1+i] = 0;
                            } else if (jlbsCode[tyb1][txb1+i] == 2) {
                                txb1+=i;
                                rewardTwo();
                                txb1-=i;
                                break;
                            } else if(jlbsCode[tyb1][txb1+i]==1){
                                break;
                            }
                        }
                    }


                    c=true;
                    BombTime2PT1.stop();
                    twoPbt1=0;

                }
            }
        });







    }
    public void countOne(){
        if(onebombQua<5&&jlbsCode[y1][x1]==-2){
            onebombQua++;
            System.out.println("1P炸彈"+onebombQua);
        }
        if(onefireQua<5&&jlbsCode[y1][x1]==-1){
            onefireQua++;
            System.out.println("1P火力"+onefireQua);
        }
    }
    public void countTwo(){
        if(twobombQua<5&&jlbsCode[y2][x2]==-2){
            twobombQua++;
            //  System.out.println("2P炸彈"+twobombQua);
        }
        if(twofireQua<5&&jlbsCode[y2][x2]==-1){
            twofireQua++;
            // System.out.println("2P火力"+twofireQua);
        }
    }
    public  void rewardOne(){
        Random rnd = new Random();

        reward=rnd.nextInt(10)+1;
        //System.out.println(reward);
        if(reward<4){
            jlbs[rTmpy][rTmpx].setIcon(imgefirePlus);
            jlbsCode[rTmpy][rTmpx]=-1;
        }else if(reward>5) {
            jlbs[rTmpy][rTmpx].setIcon(imgebombPlus);
            jlbsCode[rTmpy][rTmpx] = -2;
        }else if(reward==4||reward==5){
            jlbs[rTmpy][rTmpx].setIcon(imgeGrass);
            jlbsCode[rTmpy][rTmpx]=0;
        }
    }
    public  void rewardTwo(){
        Random rnd = new Random();

        reward=rnd.nextInt(10)+1;
        if(reward<4){
            jlbs[tyb1][txb1].setIcon(imgefirePlus);
            jlbsCode[tyb1][txb1]=-1;
        }else if(reward>5) {
            jlbs[tyb1][txb1].setIcon(imgebombPlus);
            jlbsCode[tyb1][txb1] =-2;
        }else if(reward==4||reward==5){
            jlbs[tyb1][txb1].setIcon(imgeGrass);
            jlbsCode[tyb1][txb1]=0;
        }
    }
    public void oPdie(){
        BombTime1PT1.stop();
        BombTime1PT2.stop();
        BombTime1PT3.stop();
        BombTime1PT4.stop();
        BombTime1PT5.stop();
        BombTime2PT1.stop();
        JOptionPane.showMessageDialog(null, "1P死亡", "遊戲結束", JOptionPane.ERROR_MESSAGE);
        restart();
    }
    public void tPdie(){
        BombTime1PT1.stop();
        BombTime1PT2.stop();
        BombTime1PT3.stop();
        BombTime1PT4.stop();
        BombTime1PT5.stop();
        BombTime2PT1.stop();
        JOptionPane.showMessageDialog(null, "2P死亡", "遊戲結束", JOptionPane.ERROR_MESSAGE);
        restart();
    }
    public void restart(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                jlbs[i][j].setIcon(imgeBrick);
                jlbsCode[i][j]=2;
            }
        }
        jlbs[0][1].setIcon(imgeGrass); jlbsCode[0][1]=0;
        jlbs[1][0].setIcon(imgeGrass); jlbsCode[1][0]=0;
        jlbs[7][6].setIcon(imgeGrass); jlbsCode[7][6]=0;
        jlbs[6][7].setIcon(imgeGrass); jlbsCode[6][7]=0;
        jlbs[1][1].setIcon(imgeStone2); jlbsCode[1][1]=1;
        jlbs[1][2].setIcon(imgeStone2); jlbsCode[1][2]=1;
        jlbs[2][1].setIcon(imgeStone2); jlbsCode[2][1]=1;
        jlbs[2][2].setIcon(imgeStone2); jlbsCode[2][2]=1;
        jlbs[5][5].setIcon(imgeStone2); jlbsCode[5][5]=1;
        jlbs[5][6].setIcon(imgeStone2); jlbsCode[5][6]=1;
        jlbs[6][5].setIcon(imgeStone2); jlbsCode[6][5]=1;
        jlbs[6][6].setIcon(imgeStone2); jlbsCode[6][6]=1;
        jlbs[1][4].setIcon(imgeStone2); jlbsCode[1][4]=1;
        jlbs[1][6].setIcon(imgeStone2); jlbsCode[1][6]=1;
        jlbs[3][4].setIcon(imgeStone2); jlbsCode[3][4]=1;
        jlbs[3][6].setIcon(imgeStone2); jlbsCode[3][6]=1;
        jlbs[4][1].setIcon(imgeStone2); jlbsCode[4][1]=1;
        jlbs[4][3].setIcon(imgeStone2); jlbsCode[4][3]=1;
        jlbs[6][1].setIcon(imgeStone2); jlbsCode[6][1]=1;
        jlbs[6][3].setIcon(imgeStone2); jlbsCode[6][3]=1;
        jlbsCode[0][0]=3;
        jlbsCode[7][7]=4;
        jlbs[0][0].setIcon(imge1pS);
        jlbs[7][7].setIcon(imge2pS);
        onePbt1 =0;
        twoPbt1 =0;
        oxb1 =0;
        oyb1 =0;
        txb1 =0;
        tyb1 =0;

        c = true;
        x1 = 0;
        y1 = 0;
        x2 = 7;
        y2 = 7;
        onebombQua = 1;
        onefireQua = 1;
        twobombQua = 1;
        twofireQua = 1;
        countBomb =0;
        queueBomb =1;
    }


}