package Tetris;
import java.awt.*;
import java.util.Random;
import javax.swing.*;

//22300649 전경호. 영어 미숙으로 한국어 주석이 섞여 있습니다.

// 기타 컨트롤 해결 (Rules 담당)

public class Control {
    //한 박스 크기
    public static final int BOX_SIZE = 25;
    public int score = 0;
    //블럭이 떨어지는 시간(난이도)
    public int dropTime = 500;

    //LeftPanel에 기록될 레벨. 1000점 당 1단계가 올라간다.
    public int level = (int)(score / 1000) + 1;

    //레벨 당 블럭이 떨어지는 시간. 레벨 1이 올라갈 때마다 10%씩 빨라지도록 설계.
    public void getLevel() {
        dropTime = (int)(500 * (1 - 0.1 * (score / 1000)));
        level = (int)(score / 1000) + 1;
    }

    //판 개수

    public static final int BoardWidth = 10;
    public static final int BoardHeight = 21;

    //점수표. 한번에 없애는 줄의 개수에 따라 추가 점수 표기.
    public static final int[] SCORELine = {100, 300, 500, 1000};

    //그냥 떨어졌을 떄. 10점
    public static final int SCORE_PIECE = 10;
    // 스페이스바로 바로 떨어뜨렸을 때. 30점 추가.
    public static final int SCORE_SPACE = 30;

    // 좌표를 통해 박스를 표기해서 모양 만들기. Point 사용.
    /* Point[shape][rotate][point]
        I, O, T, J, L, S, Z 순
        0, 90. 180, 270
        왼쪽 위 기준 좌표 측정

     */
    public static final Point[][][] TETROMINO = {

            {   //I
                    {new Point(0,1), new Point(1,1), new Point(2,1), new Point(3,1) },
                    {new Point(2,0), new Point(2,1), new Point(2,2), new Point(2,3) },
                    {new Point(0,1), new Point(1,1), new Point(2,1), new Point(3,1) },
                    {new Point(1,0), new Point(1,1), new Point(1,2), new Point(1,3) }
            },
            {   //O
                    {new Point(0,0), new Point(0,1), new Point(1,0), new Point(1,1) },
                    {new Point(0,0), new Point(0,1), new Point(1,0), new Point(1,1) },
                    {new Point(0,0), new Point(0,1), new Point(1,0), new Point(1,1) },
                    {new Point(0,0), new Point(0,1), new Point(1,0), new Point(1,1) }
            },
            {   //T
                    {new Point(1,0), new Point(0,1), new Point(1,1), new Point(2,1) },
                    {new Point(1,0), new Point(1,1), new Point(2,1), new Point(1,2) },
                    {new Point(0,1), new Point(1,1), new Point(2,1), new Point(1,2) },
                    {new Point(1,0), new Point(1,1), new Point(0,1), new Point(1,2) }
            },
            {   //J
                    {new Point(0,0), new Point(0,1), new Point(1,1), new Point(2,1) },
                    {new Point(0,0), new Point(1,0), new Point(0,1), new Point(0,2) },
                    {new Point(0,0), new Point(1,0), new Point(2,0), new Point(2,1) },
                    {new Point(1,0), new Point(1,1), new Point(0,2), new Point(1,2) }

            },
            {
                //L
                    {new Point(2,0), new Point(0,1), new Point(1,1), new Point(2,1) },
                    {new Point(0,0), new Point(0,1), new Point(0,2), new Point(1,2) },
                    {new Point(0,0), new Point(1,0), new Point(2,0), new Point(0,1) },
                    {new Point(0,0), new Point(1,0), new Point(1,1), new Point(1,2) }
            },
            {
                //S
                    {new Point(1,0), new Point(2,0), new Point(0,1), new Point(1,1) },
                    {new Point(0,0), new Point(0,1), new Point(1,1), new Point(1,2) },
                    {new Point(1,0), new Point(2,0), new Point(0,1), new Point(1,1) },
                    {new Point(0,0), new Point(0,1), new Point(1,1), new Point(1,2) },
            },
            {
                //Z
                    {new Point(0,0), new Point(1,0), new Point(1,1), new Point(2,1) },
                    {new Point(1,0), new Point(0,1), new Point(1,1), new Point(0,2) },
                    {new Point(0,0), new Point(1,0), new Point(1,1), new Point(2,1) },
                    {new Point(1,0), new Point(0,1), new Point(1,1), new Point(0,2) },

            }
    };

    /*
    Color of Tetromino I, O, T, J, L, S, Z
    Referred from "Tetr.io" design.
     */
    public static Color[] TminoColor = {
            new Color(102,255,255),
            new Color(255,255,102),
            new Color(255,102,255),
            new Color(102,102,255),
            new Color(255,153,51),
            new Color(153,255,102),
            new Color(255,102,102)
    };

    //Screens (left, Game, Right) Three part
    protected LeftPanel leftPanel;
    public LeftPanel getLeftPanel() {
        return leftPanel;
    }
    public void setLeftPanel(LeftPanel leftPanel) {
        this.leftPanel = leftPanel;
    }
    public void updateLeftPanel() {
    }


    public RightPanel rightPanel;
    public RightPanel getRightPanel() {
        return rightPanel;
    }
    public void setRightPanel(RightPanel rightPanel) {
        this.rightPanel = rightPanel;
    }


    protected GamePanel gamePanel;
    public GamePanel getGamePanel() {
        return gamePanel;
    }
    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }



    public Color[][] board;
    //About Tetromino

    //Tetromino의 모양 index.
    protected int TminoShape;

    //0, 90, 180, 270의 index
    protected int TminoRotation;

    //좌표.
    protected Point TminoPoint;
    public Point getTminoPoint() {
        return TminoPoint;
    }
    public void setTminoPoint(Point x) {
        this.TminoPoint = x;
    }

    /*The rule "7-bag" is used to set the next block in Tetris.
    7bag is a method of grouping seven Tetrominos into a random arrangement.

    설명 : 테트리스에서 블록은 완전한 랜덤으로 나타나는 것이 아닌, 7종류의 Tetromino(블럭)이 하나의 bag를 이루어 다음 블럭에 영향을 준다. bag 안에서의
        Tetromino는 무작위로 배열된다.
     */
    protected int[] bag7 = new int[7];
    protected int[] nextBag7 = new int[7];

    //초기 Hold를 위한 임시 결측값.
    protected int holdBox = 999;

    //bag List 생성. (겹치지 않는 7개의 숫자 배열)
    public void setBag7(int[] list7) {
        Random rand = new Random();
        int idx = 0;
        boolean flag = true;
        while(idx < 7) {
            flag = true;
            int r = rand.nextInt(7);

            if (idx != 0) {
                for (int i = 0; i < idx; i++) {
                    if (r == list7[i]) {
                        flag = false;
                    }
                }
            }
            if (flag) {
                list7[idx] = r;
                idx++;
            }
        }
    }
    public int bagIdx = 0;

    //Hold는 블럭을 놓기 전에 한번만 사용 가능. 체크하기 위한 flag
    boolean useHold;

    //Hold를 위해 Hold값과 현재 블럭을 교환. LeftPanel에 해당 블럭 전달. Hold 세부 규칙도 구현.
    public void Holding() {
        if (!useHold) {
            if (holdBox == 999) {
                holdBox = bag7[bagIdx - 1];
                TminoShape = getTmino();
                useHold = true;
                rightPanel.paintNextTmino();
            }
            else {
                setTminoPoint(new Point(5,1));
                int temp = holdBox;
                if (bagIdx == 0) {
                    holdBox = bag7[0];
                }
                else {
                    holdBox = bag7[bagIdx - 1];
                }
                TminoShape = temp;
                useHold = true;
            }

            leftPanel.blank = false;
            leftPanel.paintHoldTmino(this);
        }
    }

    // +) RightPanel에서의 다음 블럭을 출력하기 위해서, nextBag 리스트가 필요.
    public int getTmino() {
        setTminoPoint(new Point(5,1));
        rightPanel.paintNextTmino();
        bagIdx++;
        TminoRotation = 0;
        useHold = false;
        if (bagIdx > bag7.length) {
            bagIdx = 0;
            for (int i = 0; i < bag7.length; i++) {
                bag7[i] = nextBag7[i];
            }
            TminoShape = bag7[0];
            setBag7(nextBag7);
            return bag7[bagIdx++];
        }
        else {
            TminoShape = bag7[bagIdx-1];
            return bag7[bagIdx - 1];
        }
    }

    //게임 실행중 체크.
    boolean isPlaying = false;
    public boolean getIsPlaying() {
       return isPlaying;
    }
    public void setIsPlaying(boolean i) {
        isPlaying = i;
    }

    public int getScore() {
        return score;
    }
    public void addScore(int score) {
        this.score += score;
    }

    //지워진 Line 개수 체크.
    protected int clearLine = 0;
    public void addClearLine(int line) {
        clearLine += line;
    }
    public int getClearLine() {
        return clearLine;
    }


    //진행 시간.
    public int time = 0;

    public int getTime() {
        return time;
    }
    public void setTime(int time) {
        this.time = time;
    }


    //게임이 끝났을 때, Game Over!문구 다음 시간, 점수 출력.
    public void finish() {
        setIsPlaying(false);
        System.out.println(time);
        JOptionPane.showMessageDialog(null, "Game Over!");
        JOptionPane.showMessageDialog(null, "SCORE : " + score + "\nTIME : " + time + "s");
        System.exit(0);
    }

    //RightPanel Update.
    public void updateRightPanel() {
        rightPanel.setSidePanel();
        rightPanel.paintNextTmino();
        leftPanel.setLevelLabel();
    }

    //시작 setting.
    public void startGame() {
        setBag7(bag7);
        setBag7(nextBag7);
        gamePanel.startPanel();
        rightPanel.setSidePanel();
        new Thread(gamePanel).start();
        setIsPlaying(true);
        new Thread(rightPanel).start();

    }
}


