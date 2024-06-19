package Tetris;
import java.awt.*;
import javax.swing.*;


//Tetris의 Main 화면을 출력하는 class. Swing 사용.

public class GamePanel extends JPanel implements Runnable{
    protected Control control;

    public Control getControl() {
        return control;
    }

    public GamePanel(Control control) {
        super();
        this.control = control;
    }

    //메인 Board making.
    /*
        원래 "Tetr.io"의 Design을 위해 검은 배경사진에 흰색 테두리, 검은 board를 표현하고 싶었으나, 디자인적으로 예쁜 구현이 어려워
            흰색 배경에 검은 board로 대체.
     */
    public void startPanel() {

        control.board = new Color[Control.BoardWidth+2][Control.BoardHeight+3];

        for (int i = 0; i < Control.BoardWidth + 2; ++i) {
            for (int j = 0; j < Control.BoardHeight+ 3; ++j) {
                if (i == 0 || i == Control.BoardWidth + 1 || j == 0 || j == Control.BoardHeight+1) {
                    control.board[i][j] = new Color(204,204,204);
                }
                else {
                    control.board[i][j] = Color.BLACK;
                }
            }
        }

        control.TminoShape = control.getTmino();
    }

    //Tetromino 구현. Graphics 문법 사용.
    public void drawTmino(Graphics g) {
        g.setColor(Control.TminoColor[control.TminoShape]);
        for (Point p : Control.TETROMINO[control.TminoShape][control.TminoRotation]) {
            g.fillRect((p.x + control.getTminoPoint().x) * (Control.BOX_SIZE + 1),
                    (p.y + control.getTminoPoint().y) * (Control.BOX_SIZE + 1),
                    Control.BOX_SIZE,
                    Control.BOX_SIZE);

        }


    }

    //Board표현.
    public void paintComponent(Graphics g) {

        g.fillRect(0,0, (Control.BOX_SIZE + 1) * 12, (Control.BOX_SIZE + 1) * 23);
        for (int i = 0; i < Control.BoardWidth+2; i++) {
            for (int j = 0; j < Control.BoardHeight+2; j++) {
                g.setColor(control.board[i][j]);
                g.fillRect((Control.BOX_SIZE + 1) * i, (Control.BOX_SIZE + 1) * j,
                        Control.BOX_SIZE, Control.BOX_SIZE);
            }
        }
        drawTmino(g);

    }

    //블럭이 땅바닥에 닿았을 경우. (패배 or next)
    public boolean isTouched(int x, int y, int rot) {
        boolean l = false;
        for (Point p : Control.TETROMINO[control.TminoShape][rot]) {
            if (control.board[p.x + x][p.y + y + 1] != Color.BLACK) {
                l = true;
            }
        }
        return l;
    }

    //holding 시, 블럭 repaint를 위한 코드.
    public void Holding() {
        control.Holding();

        getParent().repaint();
    }

    /*
    회전 상태 변경 후, Board를 새로 출력
     */
    public void rotation(int i) {
        int newRot = (control.TminoRotation + i) % 4;
        if (newRot < 0) {
            newRot += 3;
        }
        if (!isTouched(control.getTminoPoint().x, control.getTminoPoint().y, newRot)) {
            control.TminoRotation = newRot;
        }
        getParent().repaint();
    }

    //블럭 moving.
    public void moving(int i) {
        if (!isTouched(control.getTminoPoint().x + i, control.getTminoPoint().y, control.TminoRotation)) {
            control.getTminoPoint().x += i;
        }
        getParent().repaint();
    }

    //space. 한방에 떨구기
    public void dropping() {
        if (!isTouched(control.getTminoPoint().x, control.getTminoPoint().y, control.TminoRotation)) {
            control.getTminoPoint().y += 1;
        }
        else {
            isFloor();
        }
        getParent().repaint();
    }

    //꽉 찰 시에 라인 지우기. clearRows(), removeLine().
    public void removeLine(int line) {
        for (int j = line - 1; j > 0; j--) {
            for (int i =1; i < Control.BoardWidth+1; i++) {
                control.board[i][j+1] = control.board[i][j];
            }
        }
    }
    public void clearRows() {
        boolean blank;
        int clearNum = 0;
        for (int j = 21; j > 0; j--) {
            blank = false;
            for (int i = 1; i < 11; i++) {
                if (control.board[i][j] == Color.BLACK) {
                    blank = true;
                    break;
                }
            }
            if (!blank) {
                removeLine(j);
                j += 1;
                clearNum += 1;
            }
        }
        control.addClearLine(clearNum);
        switch (clearNum) {
            case 1:
                control.addScore(Control.SCORELine[0]);
                break;
            case 2:
                control.addScore(Control.SCORELine[1]);
                break;
            case 3:
                control.addScore(Control.SCORELine[2]);
                break;
            case 4:
                control.addScore(Control.SCORELine[3]);
                break;
        }
        clearNum = 0;
    }

    //바닥에 닿을 시. finish이거나 계속 하거나.
    public void isFloor() {
        for (Point p : Control.TETROMINO[control.TminoShape][control.TminoRotation]) {
            control.board[control.getTminoPoint().x + p.x][control.getTminoPoint().y + p.y] = Control.TminoColor[control.TminoShape];
        }

        //Lose, Full
        if (control.getTminoPoint().y < 2) {
            control.setIsPlaying(false);
            control.finish();
        }
        control.addScore(Control.SCORE_PIECE);
        clearRows();
        control.TminoShape = control.getTmino();
        control.getLevel();
        control.updateLeftPanel();
        control.updateRightPanel();

        //control.updateLeftPanel();
    }


    //running.
    public void run() {
        while (control.isPlaying) {
            try {
                Thread.sleep(control.dropTime);
                dropping();
            }
            catch (InterruptedException e) {
            }
        }
    }
}
