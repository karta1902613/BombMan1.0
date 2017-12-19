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
    //∥       6 = 有炸彈&&有人                          ∥
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

    private Timer BombTime1P;
    private Timer BombTime2P;
    private int onePbt =0;
    private int twoPbt =0;
    private int oxb =0;
    private int oyb =0;
    private int txb =0;
    private int tyb =0;
    private boolean b = true;
    private boolean c = true;


    int x1 = 0, y1 = 0 ,x2 = 7, y2 = 7;

    public MainFrame() {
        init();
    }

    private void init() {

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        cp = this.getContentPane();
        cp.setLayout(new GridLayout(8, 8, 0, 0));
        setBounds(0, 0, 1000, 1010);

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
                        if(x2==0||jlbsCode[y2][x2-1]!=0) {
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
                            jlbsCode[y2][x2]=4;

                            break;
                        }else {
                            x2--;
                            jlbsCode[y2][x2+1]=0;
                            jlbs[y2][x2+1].setIcon(imgeGrass);
                            jlbs[y2][x2].setIcon(imge2pA);
                            jlbsCode[y2][x2]=4;
                            break;
                        }


                    case 38: //往上
                        if(y2==0||jlbsCode[y2-1][x2]!=0){
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
                            jlbsCode[y2][x2]=4;

                            break;
                        }else { //人物移動設地原本位置圖片以及移動之後位置圖片
                            y2--;
                            jlbsCode[y2+1][x2]=0;
                            jlbs[y2+1][x2].setIcon(imgeGrass);
                            jlbs[y2][x2].setIcon(imge2pW);
                            jlbsCode[y2][x2]=4;
                            break;
                        }

                    case 39: //往右
                        if(x2==7||jlbsCode[y2][x2+1]!=0){
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
                            jlbsCode[y2][x2]=4;

                            break;
                        }else{
                            x2++;
                            jlbsCode[y2][x2-1]=0;
                            jlbs[y2][x2-1].setIcon(imgeGrass);
                            jlbs[y2][x2].setIcon(imge2pD);
                            jlbsCode[y2][x2]=4;
                            break;
                        }


                    case 40: //往下
                        if(y2==7||jlbsCode[y2+1][x2]!=0){
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
                            jlbsCode[y2][x2]=4;

                            break;
                        }else {
                            y2++;
                            jlbsCode[y2-1][x2]=0;
                            jlbs[y2-1][x2].setIcon(imgeGrass);
                            jlbs[y2][x2].setIcon(imge2pS);
                            jlbsCode[y2][x2]=4;
                            break;
                        }
                    case 87: //W
                        if(y1==0||jlbsCode[y1-1][x1]!=0){
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
                            jlbsCode[y1][x1]=3;

                            break;
                        }else { //人物移動設地原本位置圖片以及移動之後位置圖片
                            y1--;
                            jlbsCode[y1+1][x1]=0;
                            jlbs[y1+1][x1].setIcon(imgeGrass);
                            jlbs[y1][x1].setIcon(imge1pW);
                            jlbsCode[y1][x1]=3;
                            break;
                        }
                    case 83: //S
                        if(y1==7||jlbsCode[y1+1][x1]!=0){
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
                            jlbsCode[y1][x1]=3;

                            break;
                        }else {
                            y1++;
                            jlbsCode[y1-1][x1]=0;
                            jlbs[y1-1][x1].setIcon(imgeGrass);
                            jlbs[y1][x1].setIcon(imge1pS);
                            jlbsCode[y1][x1]=3;
                            break;
                        }
                    case 65: //A
                        if(x1==0||jlbsCode[y1][x1-1]!=0) {
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
                            jlbsCode[y1][x1]=3;

                            break;
                        }else {
                            x1--;
                            jlbsCode[y1][x1+1]=0;
                            jlbs[y1][x1+1].setIcon(imgeGrass);
                            jlbs[y1][x1].setIcon(imge1pA);
                            jlbsCode[y1][x1]=3;
                            break;
                        }
                    case 68: //D
                        if(x1==7||jlbsCode[y1][x1+1]!=0){
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

                            break;
                        }else{
                            x1++;
                            jlbsCode[y1][x1-1]=0;
                            jlbs[y1][x1-1].setIcon(imgeGrass);
                            jlbs[y1][x1].setIcon(imge1pD);
                            jlbsCode[y1][x1]=3;
                            break;
                        }
                    case 32: //SPACE
                        if(b == true){
                            jlbs[y1][x1].setIcon(imge1pSBomb);
                            jlbsCode[y1][x1]=6;
                            oyb=y1;
                            oxb=x1;
                            b=false;
                            BombTime1P.start();
                            break;
                        }else{
                            break;
                        }
                    case 96: //0
                        if(c == true){
                            jlbs[y2][x2].setIcon(imge2pSBomb);
                            jlbsCode[y2][x2]=6;
                            tyb=y2;
                            txb=x2;
                            c=false;
                            BombTime2P.start();
                        }


                }
            }
        }) ;

        BombTime1P = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onePbt++;

                if(onePbt>6){
                    jlbs[oyb][oxb].setIcon(imgeFire);
                    if(oyb<7){ //往下炸
                        if(jlbsCode[oyb+1][oxb]==0||jlbsCode[oyb+1][oxb]>1) {
                            jlbs[oyb + 1][oxb].setIcon(imgeFireS);
                            }

                    }
                    if(oyb>0){ //往上炸
                        if(jlbsCode[oyb-1][oxb]==0||jlbsCode[oyb-1][oxb]>1) {
                            jlbs[oyb - 1][oxb].setIcon(imgeFireW);
                        }
                    }
                    if(oxb>0){ //往左炸
                        if(jlbsCode[oyb][oxb-1]==0||jlbsCode[oyb][oxb-1]>1) {
                            jlbs[oyb][oxb-1].setIcon(imgeFireA);
                        }
                    }
                    if(oxb<7){ //往右炸
                        if(jlbsCode[oyb][oxb+1]==0||jlbsCode[oyb][oxb+1]>1) {
                            jlbs[oyb][oxb+1].setIcon(imgeFireD);
                        }
                    }
                    if(oyb<7) {
                        if (jlbsCode[oyb + 1][oxb] == 3) { //下
                            oPdie();
                        } else if (jlbsCode[oyb + 1][oxb] == 4) {
                            tPdie();
                        }
                    }
                    if(oyb>0) {
                        if (jlbsCode[oyb - 1][oxb] == 3) { //上
                            oPdie();
                        } else if (jlbsCode[oyb - 1][oxb] == 4) {
                            tPdie();
                        }
                    }
                    if(oxb>0) {
                        if (jlbsCode[oyb][oxb - 1] == 3) { //左
                            oPdie();
                        } else if (jlbsCode[oyb][oxb - 1] == 4) {
                            tPdie();
                        }
                    }
                    if(oxb<7) {
                        if (jlbsCode[oyb][oxb + 1] == 3) { //右
                            oPdie();
                        } else if (jlbsCode[oyb][oxb + 1] == 4) {
                            tPdie();
                        }
                    }
                    if (jlbsCode[oyb][oxb] == 6) { //中
                        oPdie();
                    }
                }

                if(onePbt>7){
                    //設定編碼 回復草地
                    jlbsCode[oyb][oxb]=0;
                    jlbs[oyb][oxb].setIcon(imgeGrass);
                    if(oyb<7){//往下炸

                        if(jlbsCode[oyb+1][oxb]==0||jlbsCode[oyb+1][oxb]>1) {
                            jlbs[oyb + 1][oxb].setIcon(imgeGrass);
                            jlbsCode[oyb + 1][oxb] = 0;

                        }
                    }
                    if(oyb>0){//往上炸
                        if(jlbsCode[oyb-1][oxb]==0||jlbsCode[oyb-1][oxb]>1) {
                            jlbs[oyb - 1][oxb].setIcon(imgeGrass);
                            jlbsCode[oyb-1][oxb] = 0;
                        }
                    }
                    if(oxb>0){//往左炸
                        if(jlbsCode[oyb][oxb-1]==0||jlbsCode[oyb][oxb-1]>1) {
                            jlbs[oyb][oxb-1].setIcon(imgeGrass);
                            jlbsCode[oyb][oxb-1] = 0;
                        }
                    }
                    if(oxb<7){//往右炸
                        if(jlbsCode[oyb][oxb+1]==0||jlbsCode[oyb][oxb+1]>1) {
                            jlbs[oyb][oxb+1].setIcon(imgeGrass);
                            jlbsCode[oyb][oxb+1] = 0;
                        }
                    }
                    b=true;
                    BombTime1P.stop();
                    onePbt=0;

                }
            }
        });

        BombTime2P = new Timer(150, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                twoPbt++;

                if(twoPbt>6){
                    jlbs[tyb][txb].setIcon(imgeFire);
                    if(tyb<7){ //往下炸
                        if(jlbsCode[tyb+1][txb]==0||jlbsCode[tyb+1][txb]>1) {
                            jlbs[tyb + 1][txb].setIcon(imgeFireS);

                        }
                    }
                    if(tyb>0){ //往上炸
                        if(jlbsCode[tyb-1][txb]==0||jlbsCode[tyb-1][txb]>1) {
                            jlbs[tyb - 1][txb].setIcon(imgeFireW);
                        }
                    }
                    if(txb>0){ //往左炸
                        if(jlbsCode[tyb][txb-1]==0||jlbsCode[tyb][txb-1]>1) {
                            jlbs[tyb][txb-1].setIcon(imgeFireA);
                        }
                    }
                    if(txb<7){ //往右炸
                        if(jlbsCode[tyb][txb+1]==0||jlbsCode[tyb][txb+1]>1) {
                            jlbs[tyb][txb+1].setIcon(imgeFireD);
                        }
                    }
                    if(tyb<7) {
                        if (jlbsCode[tyb + 1][txb] == 3) { //下
                            oPdie();
                        } else if (jlbsCode[tyb + 1][txb] == 4) {
                            tPdie();
                        }
                    }
                    if(tyb>0) {
                        if (jlbsCode[tyb - 1][txb] == 3) { //上
                            oPdie();
                        } else if (jlbsCode[tyb - 1][txb] == 4) {
                            tPdie();
                        }
                    }
                    if(txb>0) {
                        if (jlbsCode[tyb][txb - 1] == 3) { //左
                            oPdie();
                        } else if (jlbsCode[tyb][txb - 1] == 4) {
                            tPdie();
                        }
                    }
                    if(txb<7) {
                        if (jlbsCode[tyb][txb + 1] == 3) { //右
                            oPdie();
                        } else if (jlbsCode[tyb][txb + 1] == 4) {
                            tPdie();
                        }
                    }
                        if (jlbsCode[tyb][txb] == 6) { //中
                            tPdie();
                        }
                }


                if(twoPbt>7){
                    //設定編碼 回復草地
                    jlbsCode[tyb][txb]=0;
                    jlbs[tyb][txb].setIcon(imgeGrass);
                    if(tyb<7){//往下炸
                        if(jlbsCode[tyb+1][txb]==0||jlbsCode[tyb+1][txb]>1) {
                            jlbs[tyb + 1][txb].setIcon(imgeGrass);
                            jlbsCode[tyb + 1][txb] = 0;
                        }
                    }
                    if(tyb>0){//往上炸
                        if(jlbsCode[tyb-1][txb]==0||jlbsCode[tyb-1][txb]>1) {
                            jlbs[tyb - 1][txb].setIcon(imgeGrass);
                            jlbsCode[tyb-1][txb] = 0;
                        }
                    }
                    if(txb>0){//往左炸
                        if(jlbsCode[tyb][txb-1]==0||jlbsCode[tyb][txb-1]>1) {
                            jlbs[tyb][txb-1].setIcon(imgeGrass);
                            jlbsCode[tyb][txb-1] = 0;
                        }
                    }
                    if(txb<7){//往右炸
                        if(jlbsCode[tyb][txb+1]==0||jlbsCode[tyb][txb+1]>1) {
                            jlbs[tyb][txb+1].setIcon(imgeGrass);
                            jlbsCode[tyb][txb+1] = 0;
                        }
                    }
                    c=true;
                    BombTime2P.stop();
                    twoPbt=0;

                }
            }
        });




    }
    public void oPdie(){
        BombTime1P.stop();
        BombTime2P.stop();
        JOptionPane.showMessageDialog(null, "1P死亡", "遊戲結束", JOptionPane.ERROR_MESSAGE);
        restart();
    }
    public void tPdie(){
        BombTime1P.stop();
        BombTime2P.stop();
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
        onePbt =0;
        twoPbt =0;
         oxb =0;
         oyb =0;
        txb =0;
        tyb =0;
        b = true;
        c = true;
        x1 = 0;
        y1 = 0;
        x2 = 7;
        y2 = 7;
    }


}
