package Tetris;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/*
게임 진행시간, 다음 피스, 점수, 제거 줄 수
 */
public class RightPanel extends JPanel implements Runnable {
    public Control control;

    // 시간
    protected JPanel timePanel = new JPanel();
    protected JPanel nextTminoPanels = new JPanel();


    //다음 블록 Panel
    protected JPanel nextTmino1 = new JPanel();
    protected JPanel nextTmino2 = new JPanel();
    //Score
    protected JPanel scoreInfo = new JPanel();

    protected JLabel TimeLabel = new JLabel("0");
    protected JLabel ScoreLabel = new JLabel("0");
    //Line
    protected JLabel lineLabel = new JLabel("0");


    //Score와 ClearLine Update를 위한 함수.
    public void setSidePanel() {
        ScoreLabel.setText(Integer.toString(control.getScore()));
        lineLabel.setText(Integer.toString(control.getClearLine()));
        paintNextTmino();
    }

    public void setLabelFont(JLabel l) {
        l.setFont(new Font("Serif", Font.BOLD, 20));
        l.setHorizontalAlignment(JLabel.CENTER);
    }

    /*
        기존 계획 : GamePanel에서와 같이 Graphics를 사용하여 RightPanel의 다음 블록과 LeftPanel의 Hold 블록을 표현하려고 했음.

        -> 사진으로 저장하는 것이 코딩에 있어 효율적이라고 판단함. 계획을 수정.
        - tetromino라는 폴더에 각각 블록을 캡처한 사진을 저장. 각각의 사진을 control의 bag index에 맞춰 불러오기.
     */
    public void paintNextTmino() {
        int idx = control.bagIdx;
        nextTmino1.removeAll();
        ImageIcon imageIcon1;
        if (idx < 7) {
            imageIcon1 = new ImageIcon("tetromino/" + control.bag7[idx] + ".png");

        }
        else {
            imageIcon1 = new ImageIcon("tetromino/" + control.nextBag7[idx-7] + ".png");
        }
        Image image1 = imageIcon1.getImage();
        Image modifiedImage1 = image1.getScaledInstance(100,100, Image.SCALE_SMOOTH);
        imageIcon1 = new ImageIcon(modifiedImage1);
        JLabel nextPiece1 =new JLabel(imageIcon1);
        nextTmino1.add(nextPiece1);
        nextTmino1.revalidate();



        nextTmino2.removeAll();
        idx++;
        ImageIcon imageIcon2;
        if (idx < 7) {
            imageIcon2 = new ImageIcon("tetromino/" + control.bag7[idx] + ".png");

        }
        else {
            imageIcon2 = new ImageIcon("tetromino/" + control.nextBag7[idx-7] + ".png");
        }
        Image image2 = imageIcon2.getImage();
        Image modifiedImage2 = image2.getScaledInstance(100,100, Image.SCALE_SMOOTH);
        imageIcon2 = new ImageIcon(modifiedImage2);
        JLabel nextPiece2 =new JLabel(imageIcon2);
        nextTmino2.add(nextPiece2);
        nextTmino2.revalidate();
        // 이미지 아이콘 설정 코드 필요
    }

    public RightPanel(Control control) {
        this.control = control;
        this.setLayout(new BorderLayout(0, 10));
        setBorder(new EmptyBorder(26, 0, 26, 10));

        timePanel.setLayout(new GridLayout(2, 1));
        timePanel.setBorder(new LineBorder(Color.BLACK));

        nextTminoPanels.setLayout(new GridLayout(3,1));
        nextTminoPanels.setBorder(new LineBorder(Color.BLACK));

        scoreInfo.setLayout(new GridLayout(4, 1));
        scoreInfo.setBorder(new LineBorder(Color.BLACK));

        this.add(timePanel, BorderLayout.NORTH);
        this.add(nextTminoPanels, BorderLayout.CENTER);
        this.add(scoreInfo, BorderLayout.SOUTH);

        timePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        timePanel.add(new JLabel("TIME"));
        timePanel.add(TimeLabel);

        nextTminoPanels.add(new JLabel("Next"), BorderLayout.NORTH);
        nextTminoPanels.setBorder(new EmptyBorder(10, 10, 10, 10));
        nextTminoPanels.add(nextTmino1, BorderLayout.SOUTH);
        nextTminoPanels.add(nextTmino2, BorderLayout.SOUTH);

        scoreInfo.setBorder(new EmptyBorder(10, 10, 10, 10));
        scoreInfo.add(new JLabel("Score"));
        scoreInfo.add(ScoreLabel);
        scoreInfo.add(new JLabel("Lines"));
        scoreInfo.add(lineLabel);

        setLabelFont(TimeLabel);
        setLabelFont(ScoreLabel);
        setLabelFont(lineLabel);

        setPreferredSize(new Dimension(156, 0));

    }
    //running. (시간 업데이트)
    @Override
    public void run() {
        while (control.getIsPlaying()) {
            try {
                TimeLabel.setText(Integer.toString(control.getTime()));
                Thread.sleep(1000);
                control.setTime(control.getTime() + 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
