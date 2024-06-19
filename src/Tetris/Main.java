package Tetris;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
public class Main extends JFrame {


    public Main(LeftPanel leftPanel, RightPanel rightPanel, GamePanel gamePanel) {
        //main 창 띄우기.
        super("TETRIS");
        setSize(660, 665);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 0));
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);
        add(gamePanel, BorderLayout.CENTER);

        //키입력. @Tetr.io에 기반한 Key.
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        gamePanel.rotation(+1);
                        break;
                    case KeyEvent.VK_DOWN:
                        gamePanel.dropping();
                        break;
                    case KeyEvent.VK_LEFT:
                        gamePanel.moving(-1);
                        break;
                    case KeyEvent.VK_RIGHT:
                        gamePanel.moving(+1);
                        break;
                    case KeyEvent.VK_Z:
                        gamePanel.rotation(3);
                        break;
                    case KeyEvent.VK_A:
                        gamePanel.rotation(2);
                        break;
                    case KeyEvent.VK_C:
                        gamePanel.Holding();
                        break;

                    case KeyEvent.VK_SPACE:
                        while (!gamePanel.isTouched(gamePanel.getControl().getTminoPoint().x,
                        gamePanel.getControl().getTminoPoint().y,
                        gamePanel.getControl().TminoRotation)) {
                            gamePanel.dropping();
                        }
                        gamePanel.isFloor();
                        gamePanel.getControl().addScore(Control.SCORE_SPACE);
                        break;

                }
            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    //Game 시작.
    public static void startGame() {
        Control control = new Control();
        //LeftPanel leftPanel = new LeftPanel(control);
        LeftPanel leftPanel = new LeftPanel(control);
        //RightPanel rightPanel = new RightPanel(control);
        RightPanel rightPanel = new RightPanel(control);
        GamePanel gamePanel = new GamePanel(control);
        control.setLeftPanel(leftPanel);
        control.setRightPanel(rightPanel);
        control.setGamePanel(gamePanel);

        Main main = new Main(leftPanel, rightPanel, gamePanel);
        control.startGame();
        main.setVisible(true);

    }
    public static void main(String[] args) {
        startGame();
    }
}

