import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    //∥       5 = 炸彈                                 ∥
    // ==================================================
    //物件圖片
    private ImageIcon imgeGrass = new ImageIcon("grass.jpg");
    private ImageIcon imgeStone = new ImageIcon("stone.jpg");
    private ImageIcon imgeStone2 = new ImageIcon("stone2.jpg");
    private ImageIcon imgeBrick = new ImageIcon("brick.jpg");
    private ImageIcon imgeBomb = new ImageIcon("bomb.jpg");
    private ImageIcon imgeFire = new ImageIcon("fire.jpg");
    private ImageIcon imgeFireW = new ImageIcon("firew.jpg");
    private ImageIcon imgeFireA = new ImageIcon("firea.jpg");
    private ImageIcon imgeFireS = new ImageIcon("fires.jpg");
    private ImageIcon imgeFireD = new ImageIcon("fired.jpg");

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

    private Timer BombTime;
    private int bt =0;
    private int xb =0;
    private int yb =0;
    private boolean b = true;






    int x1 = 7, y1 = 7 ,x2 =0, y2 =0;

    public MainFrame() {
        init();
    }

    private void init() {

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        cp = this.getContentPane();
        cp.setLayout(new GridLayout(8, 8, 0, 0));
        setBounds(0, 0, 1000, 900);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                jlbs[i][j]= new JLabel();
                jlbs[i][j].setIcon(imgeBrick);
                jlbsCode[i][j]=2;
                cp.add(jlbs[i][j]);
            }
        }
        this.setJMenuBar(jmb);
        jmb.add(jmGame);
        jmb.add(jmAbout);

        jmGame.add(jmiNG);
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

        jmAbout.add(jmiHelp);

        jlbsCode[0][0]=3;
        jlbsCode[7][7]=4;

        jlbs[x2][y2].setIcon(imge1pS);
        jlbs[x1][y1].setIcon(imge2pS);



        this.addKeyListener( new KeyAdapter(){
            public void keyPressed(KeyEvent e)
            {
                int i = e.getKeyCode() ;
                // System.out.println(x2);
                switch( i )
                {
                    case 37: //往左
                        if(x1==0||jlbsCode[y1][x1-1]!=0){
                            break;
                        }else {
                            jlbsCode[y1][x1]=0;                  //設定人物原始位置編號為0
                            jlbs[y1][x1].setIcon(imgeGrass);     //設定人物原始位置圖片
                            x1--;                                //改變人物位置值
                            jlbs[y1][x1].setIcon(imge2pS);       //設定人物現在位置圖片
                            jlbsCode[y1][x1]=3;                  //設定人物現在位置編號
                            break;
                        }
                    case 38: //往上
                        if(y1==0||jlbsCode[y1-1][x1]!=0){
                            break;
                        }else {
                            jlbsCode[y1][x1]=0;
                            jlbs[y1][x1].setIcon(imgeGrass);
                            y1--;
                            jlbs[y1][x1].setIcon(imge2pS);
                            jlbsCode[y1][x1]=3;
                            break;
                        }
                    case 39: //往右
                        if(x1==7||jlbsCode[y1][x1+1]!=0){
                            break;
                        }else {
                            jlbsCode[y1][x1]=0;
                            jlbs[y1][x1].setIcon(imgeGrass);
                            x1++;
                            jlbs[y1][x1].setIcon(imge2pS);
                            jlbsCode[y1][x1]=3;
                            break;
                        }
                    case 40: //往下
                        if(y1==7||jlbsCode[y1+1][x1]!=0){
                            break;
                        }else {
                            jlbsCode[y1][x1]=0;
                            jlbs[y1][x1].setIcon(imgeGrass);
                            y1++;
                            jlbs[y1][x1].setIcon(imge2pS);
                            jlbsCode[y1][x1]=3;
                            break;
                        }
                    case 87: //W
                        if(y2==0||jlbsCode[y2-1][x2]!=0){
                            if(jlbsCode[y2][x2]!=5) {
                                jlbs[y2][x2].setIcon(imge1pW);
                                break;
                            }
                        }
                        else if(jlbsCode[y2][x2]==5){
                            y2--;
                            jlbs[y2][x2].setIcon(imge1pW);
                            jlbsCode[y2][x2]=4;
                            break;
                        }else {
                            jlbsCode[y2][x2]=0;
                            jlbs[y2][x2].setIcon(imgeGrass);
                            y2--;
                            jlbs[y2][x2].setIcon(imge1pW);
                            jlbsCode[y2][x2]=4;
                            break;
                        }
                    case 83: //S
                        if(y2==7||jlbsCode[y2+1][x2]!=0){
                            if(jlbsCode[y2][x2]!=5) {
                                jlbs[y2][x2].setIcon(imge1pS);
                                break;
                            }
                        }
                        else if(jlbsCode[y2][x2]==5){
                            y2++;
                            jlbs[y2][x2].setIcon(imge1pS);
                            jlbsCode[y2][x2]=4;
                            break;
                        }else {
                            jlbsCode[y2][x2]=0;
                            jlbs[y2][x2].setIcon(imgeGrass);
                            y2++;
                            jlbs[y2][x2].setIcon(imge1pS);
                            jlbsCode[y2][x2]=4;
                            break;
                        }
                    case 65: //A
                        if(x2==0||jlbsCode[y2][x2-1]!=0) {
                            if(jlbsCode[y2][x2]!=5) {
                                jlbs[y2][x2].setIcon(imge1pA);
                                break;
                            }
                        }
                        else if(jlbsCode[y2][x2]==5){
                            x2--;
                            jlbs[y2][x2].setIcon(imge1pA);
                            jlbsCode[y2][x2]=4;
                            break;
                        }else {
                            jlbsCode[y2][x2]=0;
                            jlbs[y2][x2].setIcon(imgeGrass);
                            x2--;
                            jlbs[y2][x2].setIcon(imge1pA);
                            jlbsCode[y2][x2]=4;
                            break;
                        }
                    case 68: //D
                        if(x2==7||jlbsCode[y2][x2+1]!=0){
                            if(jlbsCode[y2][x2]!=5) {
                                jlbs[y2][x2].setIcon(imge1pD);
                                break;
                            }
                        }
                        else if(jlbsCode[y2][x2]==5){
                            x2++;
                            jlbs[y2][x2].setIcon(imge1pD);
                            jlbsCode[y2][x2]=4;
                            break;
                        }else{
                            jlbsCode[y2][x2]=0;
                            jlbs[y2][x2].setIcon(imgeGrass);
                            x2++;
                            jlbs[y2][x2].setIcon(imge1pD);
                            jlbsCode[y2][x2]=4;
                            break;
                        }
                    case 32: //SPACE
                        if(b == true){
                            jlbs[y2][x2].setIcon(imgeBomb);
                            jlbsCode[y2][x2]=5;
                            yb=y2;
                            xb=x2;
                            b=false;
                            BombTime.start();
                            break;
                        }else{
                            break;
                        }

                }
            }
        }) ;

        BombTime = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bt++;
                System.out.println(bt);
                if(bt>2){
                    jlbs[yb][xb].setIcon(imgeFire);
                    if(yb<7){ //往下炸
                        if(jlbsCode[yb+1][xb]==0||jlbsCode[yb+1][xb]>1) {
                            jlbs[yb + 1][xb].setIcon(imgeFireS);
                        }
                    }
                    if(yb>0){ //往上炸
                        if(jlbsCode[yb-1][xb]==0||jlbsCode[yb-1][xb]>1) {
                            jlbs[yb - 1][xb].setIcon(imgeFireW);
                        }
                    }
                    if(xb>0){ //往左炸
                        if(jlbsCode[yb][xb-1]==0||jlbsCode[yb][xb-1]>1) {
                            jlbs[yb][xb-1].setIcon(imgeFireA);
                        }
                    }
                    if(xb<7){ //往右炸
                        if(jlbsCode[yb][xb+1]==0||jlbsCode[yb][xb+1]>1) {
                            jlbs[yb][xb+1].setIcon(imgeFireD);
                        }
                    }
                }
                if(bt>3){
                    //設定編碼 回復草地
                    jlbsCode[yb][xb]=0;
                    jlbs[yb][xb].setIcon(imgeGrass);
                    if(yb<7){//往下炸
                        if(jlbsCode[yb+1][xb]==0||jlbsCode[yb+1][xb]>1) {
                            jlbs[yb + 1][xb].setIcon(imgeGrass);
                            jlbsCode[yb + 1][xb] = 0;
                        }
                    }
                    if(yb>0){//往上炸
                        if(jlbsCode[yb-1][xb]==0||jlbsCode[yb-1][xb]>1) {
                            jlbs[yb - 1][xb].setIcon(imgeGrass);
                            jlbsCode[yb-1][xb] = 0;
                        }
                    }
                    if(xb>0){//往左炸
                        if(jlbsCode[yb][xb-1]==0||jlbsCode[yb][xb-1]>1) {
                            jlbs[yb][xb-1].setIcon(imgeGrass);
                            jlbsCode[yb][xb-1] = 0;
                        }
                    }
                    if(xb<7){//往右炸
                        if(jlbsCode[yb][xb+1]==0||jlbsCode[yb][xb+1]>1) {
                            jlbs[yb][xb+1].setIcon(imgeGrass);
                            jlbsCode[yb][xb+1] = 0;
                        }
                    }
                    b=true;
                    BombTime.stop();
                    bt=0;

                }
            }
        });

    }
}